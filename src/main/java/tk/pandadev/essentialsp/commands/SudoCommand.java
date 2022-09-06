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

public class SudoCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return false;
        }

        Player player = (Player) (sender);

        if (args.length >= 2){
            if (player.hasPermission("essentialsp.sudo")){
                Player target = Bukkit.getPlayer(args[0]);

                if (target != null){

                    StringBuilder sb = new StringBuilder();
                    for(int i = 1; i < args.length; i++) {
                        if (i > 1) sb.append(" ");
                        sb.append(args[i]);
                    }

                    target.chat("/" + sb);

                    player.sendMessage(Main.getPrefix() + "§7Der Spieler §a" + target.getName() + "§7 wurde gezwungen den Befehl §a/" + sb + "§7 auszuführen");
                } else {
                    player.sendMessage(Main.getInvalidPlayer());
                }
            }
        }else {
            player.sendMessage(Main.getPrefix() + "§c/sudo <player> <command>");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player playert = (Player) (sender);
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
