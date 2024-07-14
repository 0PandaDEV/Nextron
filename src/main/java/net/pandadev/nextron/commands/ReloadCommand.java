package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@Command(name = "reload", aliases = {"rl"})
@Permission("nextron.reload")
public class ReloadCommand extends HelpBase {

    public ReloadCommand() {
        super("reload, Reloads the server, /reload\n/rl");
    }


    @Execute
    public void reloadCommand(@Context CommandSender sender) {
        Bukkit.broadcastMessage(Main.getPrefix() + "§cReloading server");
        Bukkit.getServer().reload();
        Bukkit.broadcastMessage(Main.getPrefix() + "§aReload complete");
    }

}
