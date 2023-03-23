package tk.pandadev.nextron.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.Configs;
import tk.pandadev.nextron.utils.LanguageLoader;

import java.util.ArrayList;
import java.util.List;

public class TpaCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return false;
        }

        Player player = (Player) (sender);

        if (args.length == 1){
            try {
                Player target = Bukkit.getPlayer(args[0]);
                if (!target.equals(player)){
                    if (!Configs.settings.getBoolean(target.getUniqueId() + ".allowtpas")) {player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tpa_allow")); return false;}
                    Main.tpa.put(target, player);
                    target.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tpa_target_success_1").replace("%p", player.getName()));
                    target.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tpa_target_success_2"));
                    target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tpa_sender_success").replace("%t", target.getName()));
                } else {
                    player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tpa_error"));
                }
            }
            catch (Exception e){
                player.sendMessage(Main.getInvalidPlayer());
            }
        } else {
            player.sendMessage(Main.getPrefix() + "§c/tpa <player>");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        if (args.length == 1){
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