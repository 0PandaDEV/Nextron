package tk.pandadev.nextron.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import tk.pandadev.nextron.Main;

import java.util.HashMap;

public abstract class CommandBase implements CommandExecutor {
    private String command;
    private String permission;
    private String description;

    private String usage;

    public static HashMap<String, HashMap<String, String>> commands = new HashMap<>();
    protected CommandBase(String command, String description, String usage, String permission) {
        this.command = command;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
        HashMap<String, String> commandInfo = new HashMap<>();
        commandInfo.put(description, usage);
        commands.put(command, commandInfo);
    }
    public String getCommandName() {
         return command;
    }
    public String getPermission() {
         return permission;
    }
    protected abstract void execute(CommandSender sender, String label, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase(command)) {
            if (permission != null && !sender.hasPermission(permission)) {
                sender.sendMessage(Main.getNoPerm());
                return true;
            }
            execute(sender, label, args);
            return true;
        }
        return false;
    }

    public static String getName(String name){
        return name;
    }

    public static String getDescription(String name){
        HashMap<String, String> localCommandInfo = commands.get(name);
        return localCommandInfo.keySet().toArray(new String[0])[0];
    }

    public static String getUsage(String name){
        HashMap<String, String> localCommandInfo = commands.get(name);
        String usage = localCommandInfo.get(localCommandInfo.keySet().toArray(new String[0])[0]);
        usage = usage.replace("\\n", "\n");
        return usage;
    }

}