package tk.pandadev.essentialsp.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.Configs;
import tk.pandadev.essentialsp.utils.LanguageLoader;

import java.util.ArrayList;
import java.util.List;

public class GamemodeCommand implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return false;
        }

        Player player = (Player) (sender);

        if (args.length == 1) {

            if (player.hasPermission("essentialsp.gamemode")) {

                if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                    if (player.getGameMode() != GameMode.CREATIVE){
                        player.setGameMode(GameMode.CREATIVE);
                        if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")){
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_success").replace("%g", "Creative"));
                        }
                    }else {
                        player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_error").replace("%g", "Creative"));
                    }
                }
                if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                    if (player.getGameMode() != GameMode.SURVIVAL){
                        player.setGameMode(GameMode.SURVIVAL);
                        if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")){
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_success").replace("%g", "Survival"));
                        }
                    }else {
                        player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_error").replace("%g", "Survival"));
                    }
                }
                if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                    if (player.getGameMode() != GameMode.ADVENTURE){
                        player.setGameMode(GameMode.ADVENTURE);
                        if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")){
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_success").replace("%g", "Adventure"));
                        }
                    }else {
                        player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_error").replace("%g", "Adventure"));
                    }
                }
                if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                    if (player.getGameMode() != GameMode.SPECTATOR){
                        player.setGameMode(GameMode.SPECTATOR);
                        if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")){
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_success").replace("%g", "Spectator"));
                        }
                    }else {
                        player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_error").replace("%g", "Spectator"));
                    }
                }

            } else {
                player.sendMessage(Main.getNoPerm());
            }

        } else if (args.length == 2){

            if (player.hasPermission("essentialsp.gamemode.other")) {

                Player target = Bukkit.getPlayer(args[1]);

                if (target != null){
                    if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                        if (target.getGameMode() != GameMode.CREATIVE){
                            target.setGameMode(GameMode.CREATIVE);
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_other_success").replace("%t", target.getName()).replace("%g", "Creative"));
                        }else {
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_other_error").replace("%t", target.getName()).replace("%g", "Creative"));
                        }
                    }
                    if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                        if (target.getGameMode() != GameMode.SURVIVAL){
                            target.setGameMode(GameMode.SURVIVAL);
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_other_success").replace("%t", target.getName()).replace("%g", "Survival"));
                        }else {
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_other_error").replace("%t", target.getName()).replace("%g", "Survival"));
                        }
                    }
                    if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                        if (target.getGameMode() != GameMode.ADVENTURE){
                            target.setGameMode(GameMode.ADVENTURE);
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_other_success").replace("%t", target.getName()).replace("%g", "Adventure"));
                        }else {
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_other_error").replace("%t", target.getName()).replace("%g", "Adventure"));
                        }
                    }
                    if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                        if (target.getGameMode() != GameMode.SPECTATOR){
                            target.setGameMode(GameMode.SPECTATOR);
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_other_success").replace("%t", target.getName()).replace("%g", "Spectator"));
                        }else {
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_other_error").replace("%t", target.getName()).replace("%g", "Spectator"));
                        }
                    }
                } else {
                    player.sendMessage(Main.getInvalidPlayer());
                }
            } else {
                player.sendMessage(Main.getNoPerm());
            }

        } else {
            player.sendMessage(Main.getPrefix() + "§c/gamemode [mode] <player>");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player playert = (Player) (sender);
        ArrayList<String> list = new ArrayList<String>();
        if (args.length == 1 && playert.hasPermission("essentialsp.gamemode")) {
            list.add("0");
            list.add("1");
            list.add("2");
            list.add("3");
            list.add("survival");
            list.add("creative");
            list.add("adventure");
            list.add("spectator");
        } else if (args.length == 2 && playert.hasPermission("essentialsp.gamemode.other")){
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
