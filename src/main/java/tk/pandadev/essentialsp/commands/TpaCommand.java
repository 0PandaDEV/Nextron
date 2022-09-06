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
                Main.tpa.put(target, player);
                target.sendMessage(Main.getPrefix() + "§a" + player.getName() + "§7 hat dir eine Tpa gesendet");
                target.sendMessage(Main.getPrefix() + "§7Mache §a/tpaccept §7um die Anfrage anzunehmen");
                target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

                player.sendMessage(Main.getPrefix() + "§7Die Tpa wurde §a" + target.getName() + "§7 gesendet");
            }
            catch (Exception e){
                player.sendMessage(Main.getPrefix() + "§cDieser Spieler ist nicht Online");
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

        return list;
    }
}
