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

        if (!(sender instanceof Player)) { sender.sendMessage(Main.getCommandInstance()); return false; }

        Player player = (Player) (sender);

        if (args.length == 1) {
            if (!player.hasPermission("essentialsp.gamemode")) player.sendMessage(Main.getNoPerm());

            GameMode gamemode = null;
            if (args[0].equalsIgnoreCase("0")) gamemode = GameMode.SURVIVAL;
            if (args[0].equalsIgnoreCase("1")) gamemode = GameMode.CREATIVE;
            if (args[0].equalsIgnoreCase("2")) gamemode = GameMode.ADVENTURE;
            if (args[0].equalsIgnoreCase("3")) gamemode = GameMode.SPECTATOR;
            if (args[0].equalsIgnoreCase("survival")) gamemode = GameMode.SURVIVAL;
            if (args[0].equalsIgnoreCase("creative")) gamemode = GameMode.CREATIVE;
            if (args[0].equalsIgnoreCase("adventure")) gamemode = GameMode.ADVENTURE;
            if (args[0].equalsIgnoreCase("spectator")) gamemode = GameMode.SPECTATOR;
            if (gamemode == null) player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_invalid"));

            if (player.getGameMode().equals(gamemode)){ player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_error").replace("%g", gamemode.toString().toLowerCase())); return false; }

            player.setGameMode(gamemode);
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_success").replace("%g", gamemode.toString().toLowerCase()));
        } else if (args.length == 2){
            if (!player.hasPermission("essentialsp.gamemode.other")) {player.sendMessage(Main.getNoPerm()); return false;}

            Player target = Bukkit.getPlayer(args[1]);
            GameMode gamemode = null;
            if (args[0].equalsIgnoreCase("0")) gamemode = GameMode.SURVIVAL;
            if (args[0].equalsIgnoreCase("1")) gamemode = GameMode.CREATIVE;
            if (args[0].equalsIgnoreCase("2")) gamemode = GameMode.ADVENTURE;
            if (args[0].equalsIgnoreCase("3")) gamemode = GameMode.SPECTATOR;
            if (args[0].equalsIgnoreCase("survival")) gamemode = GameMode.SURVIVAL;
            if (args[0].equalsIgnoreCase("creative")) gamemode = GameMode.CREATIVE;
            if (args[0].equalsIgnoreCase("adventure")) gamemode = GameMode.ADVENTURE;
            if (args[0].equalsIgnoreCase("spectator")) gamemode = GameMode.SPECTATOR;
            if (gamemode == null) player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_invalid"));

            if (target.getGameMode().equals(gamemode)){player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_other_error").replace("%t", target.getName()).replace("%g", gamemode.toString().toLowerCase()));return false;}

            target.setGameMode(gamemode);
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("gamemode_other_success").replace("%t", target.getName()).replace("%g", gamemode.toString().toLowerCase()));
        } else {
            player.sendMessage(Main.getPrefix() + "§c/gamemode <mode> [player]");
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

        return list;
    }
}
