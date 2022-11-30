package tk.pandadev.essentialsp.commands;

import org.bukkit.Bukkit;
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

public class FlyCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return false;
        }

        Player player = (Player) (sender);

        if (args.length == 1) {
            if (!player.hasPermission("essentialsp.fly.other")) { player.sendMessage(Main.getNoPerm()); return false; }

            Player target = Bukkit.getPlayer(args[0]);
            if (target != null){ player.sendMessage(Main.getInvalidPlayer()); return false; }

            if (target.getAllowFlight()){
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("fly_other_off").replace("%t", target.getName()));
            } else {
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("fly_other_on").replace("%t", target.getName()));
            }

            target.setAllowFlight(!target.getAllowFlight());
            target.setFallDistance(0.0f);

        } else if (args.length == 0){
            if (!player.hasPermission("essentialsp.fly")) { player.sendMessage(Main.getNoPerm()); return false; }

            if (player.getAllowFlight()){
                if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")){
                    player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("fly_off"));
                }
            } else {
                if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")){
                    player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("fly_on"));
                }
            }
            player.setAllowFlight(!player.getAllowFlight());
            player.setFallDistance(0.0f);
        }else {
            player.sendMessage(Main.getPrefix() + "§c/fly [player]");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        Player playert = (Player) (sender);
        ArrayList<String> list = new ArrayList<String>();
        if (args.length == 1 && playert.hasPermission("essentialsp.fly.other")){
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        }

        return list;
    }
}
