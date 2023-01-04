package tk.pandadev.essentialsp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
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

public class HealCommand implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return false;
        }

        Player player = (Player)(sender);

        if (args.length == 0) {

            if (!player.hasPermission("essentialsp.heal")) { player.sendMessage(Main.getNoPerm()); return false; }

            if (player.getHealth() != player.getMaxHealth() || player.getFoodLevel() != 20) {
                player.setHealth(player.getMaxHealth());
                player.setFoodLevel(20);
                if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")){
                    player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("heal_success"));
                }
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                return true;
            }
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("heal_error"));


        } else if (args.length == 1) {

            if (!player.hasPermission("essentialsp.heal.other")) { player.sendMessage(Main.getNoPerm()); return false; }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null){ player.sendMessage(Main.getInvalidPlayer()); return false; }

            if (target.getHealth() != 20.0 || target.getFoodLevel() != 20) {
                target.setHealth(20.0);
                target.setFoodLevel(20);
                target.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("heal_other_success").replace("%p", player.getName()));
                target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                return true;
            }
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("heal_other_error").replace("%t", target.getName()));


        } else {
            player.sendMessage(Main.getPrefix() + "§c/heal [player]");
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player)(sender);

        if (args.length == 1 && playert.hasPermission("essentialsp.heal.other")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        }

        return list;
    }
}