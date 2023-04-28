package tk.pandadev.nextron.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.Configs;

public class BackCommand extends CommandBase {

    public BackCommand(){
        super("back", "Teleports the player back to the last (death, tpa, home, warp) position.", "/back", "", "nextron.back");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        
        if (!(sender instanceof Player)) { sender.sendMessage(Main.getCommandInstance()); return; }
        Player player = (Player) (sender);

        if (args.length != 0) {player.sendMessage(Main.getPrefix() + "§c/back"); return;}

        Configs.settings.set(player.getUniqueId() + ".lastback", player.getLocation());
        Configs.settings.set(player.getUniqueId() + ".isback", true);
        Configs.saveSettingsConfig();
        player.teleport((Location) Configs.settings.get(player.getUniqueId() + ".lastpos"));

    }

}