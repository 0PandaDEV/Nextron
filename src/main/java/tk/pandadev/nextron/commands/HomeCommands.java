package tk.pandadev.nextron.commands;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.Configs;
import tk.pandadev.nextron.utils.LanguageLoader;
import tk.pandadev.nextron.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HomeCommands extends CommandBase implements CommandExecutor, TabCompleter {

    public HomeCommands(){
        super("home", "Teleports you instant to a player set position", "/home <home>", "/h <home>", "nextron.home");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        Player player = (Player) (sender);

        if (label.equalsIgnoreCase("sethome") && args.length == 1) {

            if (Configs.home.getString("Homes." + player.getUniqueId() + "." + args[0].toLowerCase()) == null) {
                Configs.home.set("Homes." + player.getUniqueId() + "." + args[0].toLowerCase(), player.getLocation());
                Configs.saveHomeConfig();
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("sethome_success").replace("%h", args[0].toLowerCase()));
            } else {
                TextComponent yes = new TextComponent("§aYes");
                yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/aisdvja4f89dfjvwe4p9r8jdfvjw34r8q0dvj34-" + args[0].toLowerCase()));
                yes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to reset the position for §a" + args[0].toLowerCase()).create()));
                TextComponent message = new TextComponent(Main.getPrefix() + LanguageLoader.translationMap.get("home_reset_confirm").replace("%r", "§8["));
                message.addExtra(yes);
                message.addExtra("§8]");
                player.spigot().sendMessage(ChatMessageType.SYSTEM, message);
            }

        } else if (label.equalsIgnoreCase("sethome") && args.length == 0) {

            Configs.home.set("Homes." + player.getUniqueId() + ".default", player.getLocation());
            Configs.saveHomeConfig();
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("sethome_default_success"));

        } else if (label.equalsIgnoreCase("delhome") && args.length == 1) {

            if (Configs.home.getString("Homes." + player.getUniqueId() + "." + args[0].toLowerCase()) != null) {
                Configs.home.set("Homes." + player.getUniqueId() + "." + args[0].toLowerCase(), null);
                Configs.saveHomeConfig();
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("delhome_success").replace("%h", args[0].toLowerCase()));
            } else {
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("delhome_error").replace("%h", args[0].toLowerCase()));
            }

        } else if (label.equalsIgnoreCase("home") && args.length == 0) {

            if (Configs.home.getString("Homes." + player.getUniqueId() + ".default") != null) {
                player.teleport((Location) Configs.home.get("Homes." + player.getUniqueId() + ".default"));
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("home_default_success"));
            } else {
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("home_default_error"));
            }
        } else if (label.equalsIgnoreCase("home") || label.equalsIgnoreCase("h") && args.length == 1) {

            if (Configs.home.getString("Homes." + player.getUniqueId() + "." + args[0].toLowerCase()) != null) {
                player.teleport((Location) Configs.home.get("Homes." + player.getUniqueId() + "." + args[0].toLowerCase()));
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("home_success").replace("%h", args[0].toLowerCase()));
            } else {
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("home_error").replace("%h", args[0].toLowerCase()));
            }

        } else if (label.equalsIgnoreCase("renamehome") && args.length == 1) {

            if (Configs.home.getString("Homes." + player.getUniqueId() + "." + args[0].toLowerCase()) == null) {
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("home_error").replace("%h", args[0].toLowerCase()));
                return;
            }

            new AnvilGUI.Builder()
                    .onComplete((completion) -> {
                        if(Utils.countWords(completion.getText()) > 1) {
                            player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                            return Collections.singletonList(AnvilGUI.ResponseAction.replaceInputText(LanguageLoader.translationMap.get("anvil_gui_one_word")));
                        }
                        Configs.home.set("Homes." + player.getUniqueId() + "." + completion.getText(), Configs.home.get("Homes." + player.getUniqueId() + "." + args[0]));
                        Configs.home.set("Homes." + player.getUniqueId() + "." + args[0], null);
                        Configs.saveHomeConfig();
                        player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("home_rename_success").replace("%h", args[0]).replace("%n", completion.getText()));
                        return Collections.singletonList(AnvilGUI.ResponseAction.close());
                    })
                    .text(args[0])
                    .itemLeft(new ItemStack(Material.NAME_TAG))
                    .title("Enter the new name")
                    .plugin(Main.getInstance())
                    .open(player);

        } else {
            player.sendMessage(Main.getPrefix() + "§c/home|sethome|delhome <name>");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player) (sender);

        if (Configs.home.getConfigurationSection("Homes") == null || Configs.home.getConfigurationSection("Homes").getKeys(false).isEmpty()) {
            return null;
        } else if (args.length == 1 && label.equalsIgnoreCase("home") || label.equalsIgnoreCase("delhome") || label.equalsIgnoreCase("h")) {
            list.addAll(Objects.requireNonNull(Configs.home.getConfigurationSection("Homes." + playert.getUniqueId())).getKeys(false));
        }

        ArrayList<String> completerList = new ArrayList<String>();
        String currentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (!s1.startsWith(currentarg)) continue;
            completerList.add(s);
        }

        return completerList;
    }
}
