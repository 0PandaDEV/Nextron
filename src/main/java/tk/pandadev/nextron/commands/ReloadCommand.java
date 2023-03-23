package tk.pandadev.nextron.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;

import static org.bukkit.Bukkit.getServer;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0){
            if (sender instanceof Player){
                if (sender.hasPermission("nextron.reload")) {
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
            sender.sendMessage(Main.getPrefix() + "§c/reload");
        }

        return false;
    }

}