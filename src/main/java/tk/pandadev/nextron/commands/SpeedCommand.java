package tk.pandadev.nextron.commands;

import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.Configs;
import tk.pandadev.nextron.utils.LanguageLoader;

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
                if (NumberUtils.isNumber(args[0])) {
                    float speed = Integer.parseInt(args[0]) / 10f;
                    if (speed > 1) {
                        player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("speed_error"));
                    } else {
                        if (player.isFlying()) {
                            player.setAllowFlight(true);
                            player.setFlying(true);
                            player.setFlySpeed(speed);
                            if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) {
                                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("speed_fly_success").replace("%s", args[0]));
                            }
                        } else if (player.isOnGround()) {
                            player.setWalkSpeed(speed);
                            if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) {
                                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("speed_walk_success").replace("%s", args[0]));
                            }
                        }
                    }
                } else {
                    player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("speed_error_digit"));
                }
            } else{
                player.sendMessage(Main.getNoPerm());
            }
        } else if (args.length == 0){
            if (player.hasPermission("essentialsp.speed.reset")){
                player.setAllowFlight(true);
                player.setFlying(true);
                player.setFlySpeed((float) 0.1);
                player.setWalkSpeed((float) 0.2);
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("speed_reset"));
            }else{
                player.sendMessage(Main.getNoPerm());
            }
        }

        else {
            player.sendMessage(Main.getPrefix() + "§c/speed <amount>");
        }

        return false;
    }
}
