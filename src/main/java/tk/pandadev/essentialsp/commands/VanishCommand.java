package tk.pandadev.essentialsp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.Configs;
import tk.pandadev.essentialsp.utils.LanguageLoader;
import tk.pandadev.essentialsp.utils.VanishAPI;
import tk.pandadev.essentialsp.utils.VanishManager;

import java.util.ArrayList;
import java.util.List;

public class VanishCommand implements CommandExecutor, TabCompleter {

    public Plugin plugin;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return false;
        }

        Player player = (Player) (sender);

        if (args.length == 1) {

            if (player.hasPermission("essentialsp.vanish.other")) {

                Player target = Bukkit.getPlayer(args[0]);

                if (target != null){
                    if (VanishAPI.isVanish(target)){
                        Main.getInstance().getVanishAPI().setVanish(target, false);
                        player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("unvanish_other").replace("%t", target.getName()));
                        if (Configs.settings.getBoolean(target.getUniqueId() + ".vanish." + "message")){
                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("join_message").replace("%p", target.getName())));
                        }
                    } else{
                        Main.getInstance().getVanishAPI().setVanish(target, true);
                        player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("vanish_other").replace("%t", target.getName()));
                        if (Configs.settings.getBoolean(target.getUniqueId() + ".vanish." + "message")){
                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("leave_message").replace("%p", target.getName())));
                        }
                    }
                } else {
                    player.sendMessage(Main.getInvalidPlayer());
                }

            } else {
                player.sendMessage(Main.getNoPerm());
            }


        } else if (args.length == 0){

            if (player.hasPermission("essentialsp.vanish")) {

                if (VanishAPI.isVanish(player)){
                    Main.getInstance().getVanishAPI().setVanish(player, false);
                    player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("unvanish"));
                    if (Configs.settings.getBoolean(player.getUniqueId() + ".vanish." + "message")){
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("join_message").replace("%p", player.getName())));
                    }
                } else{
                    Main.getInstance().getVanishAPI().setVanish(player, true);
                    player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("vanish"));
                    if (Configs.settings.getBoolean(player.getUniqueId() + ".vanish." + "message")){
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("leave_message").replace("%p", player.getName())));
                    }
                }

            } else {
                player.sendMessage(Main.getNoPerm());
            }


        } else {
            player.sendMessage(Main.getPrefix() + "§c/vanish <player>");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player)(sender);

        if (args.length == 1 && playert.hasPermission("essentialsp.vanish.other")){
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
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