package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import org.bukkit.Bukkit;

@Command(name = "rl", aliases = {"reload"})
@Permission("nextron.reload")
public class ReloadCommand extends HelpBase {

    public ReloadCommand() {
        super("rl, Reloads the server, /rl\n/reload");
    }

    @Execute
    public void reloadCommand() {
        Bukkit.broadcastMessage(Main.getPrefix() + "§cReloading server");
        
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            try {
                Bukkit.getServer().reload();
                Bukkit.broadcastMessage(Main.getPrefix() + "§aReload complete");
            } catch (Exception e) {
                Bukkit.broadcastMessage(Main.getPrefix() + "§cReload failed. Check console for details.");
                e.printStackTrace();
            }
        });
    }

}