package net.pandadev.nextron.commands;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends HelpBase {

    public ReloadCommand() {
        super("rl", "Reloads the server", "/rl");
    }


    @Command(names = {"reload", "rl"}, permission = "nextron.reload")
    public void reloadCommand(CommandSender sender) {
        Bukkit.broadcastMessage(Main.getPrefix() + "§cReloading server");
        Bukkit.getServer().reload();
        Bukkit.broadcastMessage(Main.getPrefix() + "§aReload complete!");
    }

}
