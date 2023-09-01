package tk.pandadev.nextron.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import tk.pandadev.nextron.Main;

public class ReloadCommand extends CommandBase {

    public ReloadCommand() {
        super("rl", "Reloads the server", "/rl", "", "nextron.reload");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0){
            Bukkit.broadcastMessage(Main.getPrefix() + "§cReloading server");
        Bukkit.getServer().reload();
            Bukkit.broadcastMessage(Main.getPrefix() + "§aReload complete!");
        } else{
            sender.sendMessage(Main.getPrefix() + "§c/rl");
        }
    }

}
