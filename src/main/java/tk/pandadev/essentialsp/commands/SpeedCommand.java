package tk.pandadev.essentialsp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.LanguageLoader;

public class SpeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return false;
        }

        Player player = (Player) (sender);

        if (args.length == 1){
            if (player.hasPermission("essentialsp.speed")){
                float speed = Float.parseFloat(args[0]) / 10f;
                if (speed > 1) {
                    player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("speed_error"));
                } else {
                    if (player.isFlying()){
                        player.setAllowFlight(true);
                        player.setFlying(true);
                        player.setFlySpeed(speed);
                        if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".feedback")){
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("speed_fly_success").replace("%s", args[0]));
                        }
                    }else if (player.isOnGround()){
                        player.setWalkSpeed(speed);
                        if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".feedback")){
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("speed_walk_success").replace("%s", args[0]));
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
