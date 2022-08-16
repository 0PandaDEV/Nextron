package tk.pandadev.essentialsp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;

public class SpeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getPrefix() + "§6Du musst diesen Command als Spieler ausführen!");
            return false;
        }

        Player player = (Player) (sender);

        if (args.length == 1){
            if (player.hasPermission("essentialsp.speed")){
                if (player.isFlying()){
                    float speed = Float.parseFloat(args[0]) / 10f;
                    if (speed > 1){
                        player.sendMessage(Main.getPrefix() + "§cDer wert darf nicht höher als §610§c sein");
                    } else {
                        player.setAllowFlight(true);
                        player.setFlying(true);
                        player.setFlySpeed(speed);
                        if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".feedback")){
                            player.sendMessage(Main.getPrefix() + "§7Die FlySpeed wurde auf §a" + args[0] + "§7 gesetzt");
                        }
                    }
                } else if (player.isOnGround()){
                    float speed = Float.parseFloat(args[0]) / 10f;
                    if (speed > 1){
                        player.sendMessage(Main.getPrefix() + "§cDer wert darf nicht höher als §610§c sein");
                    } else {
                        player.setWalkSpeed(speed);
                        if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".feedback")){
                            player.sendMessage(Main.getPrefix() + "§7Die WalkSpeed wurde auf §a" + args[0] + "§7 gesetzt");
                        }
                    }
                }
            } else{
                player.sendMessage(Main.getNoPerm());
            }
        } else {
            player.sendMessage(Main.getPrefix() + "§c/speed <amount>");
        }

        return false;
    }
}
