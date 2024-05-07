package net.pandadev.nextron.utils.commandapi.node;

import lombok.Getter;
import lombok.SneakyThrows;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.commands.HelpBase;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.CommandHandler;
import net.pandadev.nextron.utils.commandapi.bukkit.BukkitCommand;
import net.pandadev.nextron.utils.commandapi.help.HelpNode;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import net.pandadev.nextron.utils.commandapi.paramter.ParamProcessor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class CommandNode {
    @Getter
    private static final List<CommandNode> nodes = new ArrayList<>();
    @Getter
    private static final HashMap<Class<?>, Object> instances = new HashMap<>();


    private final ArrayList<String> names = new ArrayList<>();
    private final String permission;
    private final String description;
    private final boolean async;


    private final boolean playerOnly;
    private final boolean consoleOnly;


    private final Object parentClass;
    private final Method method;


    private final List<ArgumentNode> parameters = new ArrayList<>();


    private final List<HelpNode> helpNodes = new ArrayList<>();

    public CommandNode(Object parentClass, Method method, Command command) {

        Arrays.stream(command.names()).forEach(name -> names.add(name.toLowerCase()));


        this.permission = command.permission();
        this.description = command.description();
        this.async = command.async();
        this.playerOnly = command.playerOnly();
        this.consoleOnly = command.consoleOnly();


        this.parentClass = parentClass;
        this.method = method;


        Arrays.stream(method.getParameters()).forEach(parameter -> {
            Param param = parameter.getAnnotation(Param.class);
            if (param == null) return;

            parameters.add(new ArgumentNode(param.name(), param.concated(), param.required(), param.defaultValue(), parameter));
        });


        names.forEach(name -> {
            if (!BukkitCommand.getCommands().containsKey(name.split(" ")[0].toLowerCase()))
                new BukkitCommand(name.split(" ")[0].toLowerCase());
        });


        List<String> toAdd = new ArrayList<>();
        names.forEach(name -> toAdd.add(CommandHandler.getPlugin().getName() + ":" + name.toLowerCase()));
        names.addAll(toAdd);


        nodes.add(this);
    }


    public int getMatchProbability(CommandSender sender, String label, String[] args, boolean tabbed) {
        AtomicInteger probability = new AtomicInteger(0);

        this.names.forEach(name -> {
            StringBuilder nameLabel = new StringBuilder(label).append(" ");
            String[] splitName = name.split(" ");
            int nameLength = splitName.length;

            for (int i = 1; i < nameLength; i++)
                if (args.length >= i) nameLabel.append(args[i - 1]).append(" ");

            if (name.equalsIgnoreCase(nameLabel.toString().trim())) {
                int requiredParameters = (int) this.parameters.stream().filter(ArgumentNode::isRequired).count();

                int actualLength = args.length - (nameLength - 1);

                if (requiredParameters == actualLength || parameters.size() == actualLength) {
                    probability.addAndGet(125);
                    return;
                }

                if (!this.parameters.isEmpty()) {
                    ArgumentNode lastArgument = this.parameters.get(this.parameters.size() - 1);
                    if (lastArgument.isConcated() && actualLength > requiredParameters) {
                        probability.addAndGet(125);
                        return;
                    }
                }

                if (!tabbed || splitName.length > 1 || !parameters.isEmpty()) probability.addAndGet(50);

                if (actualLength > requiredParameters) probability.addAndGet(15);

                if (sender instanceof Player && consoleOnly) probability.addAndGet(-15);

                if (!(sender instanceof Player) && playerOnly) probability.addAndGet(-15);

                if (!permission.isEmpty() && !sender.hasPermission(permission)) probability.addAndGet(-15);

                return;
            }

            String[] labelSplit = nameLabel.toString().split(" ");
            for (int i = 0; i < nameLength && i < labelSplit.length; i++)
                if (splitName[i].equalsIgnoreCase(labelSplit[i])) probability.addAndGet(5);
        });

        return probability.get();
    }


    public void sendUsageMessage(CommandSender sender, String executedCommand) {
        if (consoleOnly && sender instanceof Player) {
            sender.sendMessage(Main.getPrefix() + "§cThis command can only be executed by console.");
            return;
        }

        if (playerOnly && sender instanceof ConsoleCommandSender) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        if (!permission.isEmpty() && !sender.hasPermission(permission)) {
            sender.sendMessage(Main.getPrefix() + "§cI'm sorry, although you do not have permission to execute this command.");
            return;
        }

        HashMap<String, ArrayList<String>> commandsList = new HashMap<>(HelpBase.commands);
        assert sender != null;
        sender.sendMessage(Main.getPrefix() + "§7Usage: \n§a" + commandsList.get(executedCommand).get(0));
    }


    public int requiredArgumentsLength() {
        int requiredArgumentsLength = names.get(0).split(" ").length - 1;
        for (ArgumentNode node : parameters) if (node.isRequired()) requiredArgumentsLength++;
        return requiredArgumentsLength;
    }


    @SneakyThrows
    public void execute(CommandSender sender, String[] args) {

        if (!permission.isEmpty() && !sender.hasPermission(permission)) {
            sender.sendMessage(Main.getPrefix() + "§cI'm sorry, although you do not have permission to execute this command.");
            return;
        }

        if (sender instanceof ConsoleCommandSender && playerOnly) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        if (sender instanceof Player && consoleOnly) {
            sender.sendMessage(Main.getPrefix() + "§cThis command can only be executed by console.");
            return;
        }

        String executedCommand = Arrays.stream(names.toArray(new String[0]))
                .filter(name -> args.length > 0 && args[0].equalsIgnoreCase(name.split(" ")[0]))
                .findFirst()
                .orElse(names.get(0));

        int nameArgs = executedCommand.split(" ").length - 1;

        List<Object> objects = new ArrayList<>(Collections.singletonList(sender));
        for (int i = 0; i < parameters.size(); i++) {
            ArgumentNode node = parameters.get(i);
            String suppliedArgument;

            if (node.isConcated()) {
                suppliedArgument = String.join(" ", Arrays.copyOfRange(args, i + nameArgs, args.length));
                Object object = new ParamProcessor(node, suppliedArgument, sender).get();
                if (object == null) return;
                objects.add(object);
                break;
            } else {
                suppliedArgument = i < args.length - nameArgs ? args[i + nameArgs] : null;
                if (suppliedArgument == null && node.isRequired()) {
                    sendUsageMessage(sender, executedCommand);
                    return;
                }
                if (suppliedArgument == null) {
                    if (!node.getDefaultValue().isEmpty()) {
                        suppliedArgument = node.getDefaultValue();
                    } else {
                        continue;
                    }
                }
                Object object = new ParamProcessor(node, suppliedArgument, sender).get();
                if (object == null) return;
                objects.add(object);
            }
        }

        Object[] methodArgs = convertParameters(method, objects);
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(CommandHandler.getPlugin(), () -> {
                try {
                    method.invoke(parentClass, methodArgs);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
        } else {
            method.invoke(parentClass, methodArgs);
        }
    }

    private Object[] convertParameters(Method method, List<Object> originalParams) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] convertedParams = new Object[parameterTypes.length];
        AtomicInteger index = new AtomicInteger(0);


        for (int i = 0; i < parameterTypes.length; i++) {

            if (i < originalParams.size() && originalParams.get(i) != null) {
                convertedParams[i] = originalParams.get(i);
            } else {


                convertedParams[i] = null;
            }
        }
        return convertedParams;
    }

}
