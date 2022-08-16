package tk.pandadev.essentialsp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;

import java.util.ArrayList;
import java.util.List;

public class FlyCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getPrefix() + "§6Du musst diesen Command als Spieler ausführen!");
            return false;
        }

        Player player = (Player) (sender);

        if (args.length == 1) {
            if (player.hasPermission("essentialsp.fly.other")) {

                Player target = Bukkit.getPlayer(args[0]);

                if (target != null){

                    if (target.getAllowFlight()){
                        player.sendMessage(Main.getPrefix() + "§7Der Spieler §a" + target.getName() + "§7 kann jetzt nicht mehr fliegen");
                    } else {
                        player.sendMessage(Main.getPrefix() + "§7Der Spieler §a" + target.getName() + "§7 kann jetzt fliegen");
                    }

                    target.setAllowFlight(!target.getAllowFlight());
                    target.setFallDistance(0.0f);

                } else{
                    player.sendMessage(Main.getInvalidPlayer());
                }

            } else{
                player.sendMessage(Main.getNoPerm());
            }
        } else if (args.length == 0){
            if (player.hasPermission("essentialsp.fly")) {

                if (player.getAllowFlight()){
                    if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".message")){
                        player.sendMessage(Main.getPrefix() + "§7Du kannst jetzt nicht mehr fliegen");
                    }
                } else {
                    if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".message")){
                        player.sendMessage(Main.getPrefix() + "§7Du kannst jetzt fliegen");
                    }
                }

                player.setAllowFlight(!player.getAllowFlight());
                player.setFallDistance(0.0f);

            } else{
                player.sendMessage(Main.getNoPerm());
            }
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
