package tk.pandadev.essentialsp.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.LanguageLoader;

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
                target.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tpa_target_success_1").replace("%p", player.getName()));
                target.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tpa_target_success_2"));
                target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tpa_sender_success").replace("%t", target.getName()));
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

        return list;
    }
}
