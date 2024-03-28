package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.entity.Player;

public class SpeedCommand extends HelpBase {

    public SpeedCommand() {
        super("speed", "Allows you to set your fly/walk speed", "/speed [speed]");
    }

    @Command(names = {"speed"}, permission = "nextron.speed", playerOnly = true)
    public void speedCommand(Player player, @Param(name = "speed", required = false) Integer speed) {
        if (speed == null) {
            if (!player.hasPermission("nextron.speed.reset")) {
                player.sendMessage(Main.getNoPerm());
            }
            player.setAllowFlight(true);
            player.setFlying(true);
            player.setFlySpeed((float) 0.1);
            player.setWalkSpeed((float) 0.2);
            player.sendMessage(Main.getPrefix() + Text.get("speed.reset"));
            return;
        }

        if (NumberUtils.isNumber(String.valueOf(speed))) {
            float parsedSpeed = speed / 10f;
            if (parsedSpeed > 1) {
                player.sendMessage(Main.getPrefix() + Text.get("speed.error"));
            } else {
                if (player.isFlying()) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.setFlySpeed(parsedSpeed);
                    if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) {
                        player.sendMessage(
                                Main.getPrefix() + Text.get("speed.fly.success").replace("%s", speed.toString()));
                    }
                } else if (player.isOnGround()) {
                    player.setWalkSpeed(parsedSpeed);
                    if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) {
                        player.sendMessage(
                                Main.getPrefix() + Text.get("speed.walk.success").replace("%s", speed.toString()));
                    }
                }
            }
        } else {
            player.sendMessage(Main.getPrefix() + Text.get("speed.error.digit"));
        }
    }
}
