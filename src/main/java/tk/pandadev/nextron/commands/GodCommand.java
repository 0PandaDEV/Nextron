package tk.pandadev.nextron.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.LanguageLoader;

import java.util.ArrayList;
import java.util.List;

public class GodCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (args.length == 0){
            if (!(sender instanceof Player)) { sender.sendMessage(Main.getCommandInstance()); return false; }
            Player player = (Player) (sender);

            if (player.hasPermission("nextron.god")) {
                player.setInvulnerable(!player.isInvulnerable());
                if (player.isInvulnerable()) player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("god_on"));
                else player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("god_off"));
            }
        } else if (args.length == 1) {
            if (sender.hasPermission("nextron.god.other")) {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null){ sender.sendMessage(Main.getInvalidPlayer()); return false;}

                target.setInvulnerable(!target.isInvulnerable());
                if (target.isInvulnerable()) sender.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("god_on_other"));
                else sender.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("god_off_other"));
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player) (sender);

        if (args.length == 1){
            for (Player player : Bukkit.getOnlinePlayers()){
                list.add(String.valueOf(player));
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