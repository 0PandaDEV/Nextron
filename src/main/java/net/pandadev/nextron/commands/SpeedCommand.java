package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.SettingsAPI;
import net.pandadev.nextron.languages.TextAPI;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.entity.Player;

import java.util.Optional;

@Command(name = "speed")
@Permission("nextron.speed")
public class SpeedCommand extends HelpBase {

    public SpeedCommand() {
        super("speed, Allows you to set your fly/walk speed, /speed [speed]");
    }

    @Execute
    public void speedCommand(@Context Player player, @Arg Optional<Integer> speed) {
        if (speed.isEmpty()) {
            if (!player.hasPermission("nextron.speed.reset")) {
                player.sendMessage(Main.getNoPerm());
                return;
            }
            player.setAllowFlight(true);
            player.setFlying(true);
            player.setFlySpeed(0.1f);
            player.setWalkSpeed(0.2f);
            player.sendMessage(Main.getPrefix() + TextAPI.get("speed.reset"));
            return;
        }

        if (NumberUtils.isDigits(String.valueOf(speed.get()))) {
            float parsedSpeed = speed.get() / 10f;
            if (parsedSpeed > 1) {
                player.sendMessage(Main.getPrefix() + TextAPI.get("speed.error"));
            } else {
                if (player.isFlying()) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.setFlySpeed(parsedSpeed);
                    if (SettingsAPI.allowsFeedback(player)) {
                        player.sendMessage(Main.getPrefix() + TextAPI.get("speed.fly.success").replace("%s", speed.get().toString()));
                    }
                } else if (!player.isFlying()) {
                    player.setWalkSpeed(parsedSpeed);
                    if (SettingsAPI.allowsFeedback(player)) {
                        player.sendMessage(Main.getPrefix() + TextAPI.get("speed.walk.success").replace("%s", speed.get().toString()));
                    }
                }
            }
        } else {
            player.sendMessage(Main.getPrefix() + TextAPI.get("speed.error.digit"));
        }
    }
}
