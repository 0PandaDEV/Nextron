package tk.pandadev.nextron.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class ReloadCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0){
            if (sender instanceof Player){
                if (sender.hasPermission("essentialsp.rl")) {
                    Bukkit.broadcastMessage(Main.getPrefix() + "§cReloading Server");
                    getServer().reload();
                    Bukkit.broadcastMessage(Main.getPrefix() + "§aReload Complete!");
                } else {
                    sender.sendMessage(Main.getNoPerm());
                }
            } else {
                Bukkit.broadcastMessage(Main.getPrefix() + "§cReloading Server");
                getServer().reload();
                Bukkit.broadcastMessage(Main.getPrefix() + "§aReload Complete!");
            }
        } else {
            sender.sendMessage(Main.getPrefix() + "§c/rl");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player playert = (Player) (sender);
        ArrayList<String> list = new ArrayList<String>();



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