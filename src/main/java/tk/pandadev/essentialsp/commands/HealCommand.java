package tk.pandadev.essentialsp.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;
import java.util.ArrayList;
import java.util.List;

public class HealCommand implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getPrefix() + "§6Du musst diesen Command als Spieler ausführen!");
            return false;
        }

        Player player = (Player)(sender);

        if (args.length == 0) {

            if (player.hasPermission("essentialsp.heal")) {

                if (player.getHealth() != player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                    player.setFoodLevel(20);
                    if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".feedback")){
                        player.sendMessage(Main.getPrefix() + "§7Du hast dich geheilt");
                    }
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    return true;
                }
                player.sendMessage(Main.getPrefix() + "§cDu bist bereits geheilt");

            } else {
                player.sendMessage(Main.getNoPerm());
            }


        } else if (args.length == 1) {

            if (player.hasPermission("essentialsp.heal.other")) {

                Player target = Bukkit.getPlayer(args[0]);

                if (target != null){
                    if (target.getHealth() != 20.0) {
                        target.setHealth(20.0);
                        target.setFoodLevel(20);
                        target.sendMessage(Main.getPrefix() + "§7Du wurdest von §a" + player.getName() + "§7 geheilt");
                        target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        return true;
                    }
                    player.sendMessage(Main.getPrefix() + "§cDer Spieler §6" + target.getName() + "§c ist bereits geheilt");
                } else {
                    player.sendMessage(Main.getInvalidPlayer());
                }

            } else {
                player.sendMessage(Main.getNoPerm());
            }


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