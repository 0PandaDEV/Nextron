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
import java.util.Objects;

public class TphereCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return false;
        }

        Player player = (Player) (sender);

        if (args.length == 1){
            if (player.hasPermission("essentialsp.tphere")){
                Player target = Bukkit.getPlayer(args[0]);

                if (target != null){

                    if (!Objects.equals(target.getName(), player.getName())) {
                        target.teleport(player.getLocation());

                        if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")){
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tphere_success").replace("%t", target.getName()));
                        }

                    } else {
                        player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tphere_error"));
                    }
                } else {
                    player.sendMessage(Main.getInvalidPlayer());
                }
            }
        } else {
            player.sendMessage(Main.getPrefix() + "§c/tphere <player>");
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
