package tk.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.Configs;

public class SpeedCommand extends CommandBase implements CommandExecutor {

    public SpeedCommand() {
        super("speed", "Allows you to set your fly/walk speed", "/speed [speed]", "", "nextron.speed");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        Player player = (Player) (sender);

        if (args.length == 1) {
            if (player.hasPermission("nextron.speed")) {
                if (NumberUtils.isNumber(args[0])) {
                    float speed = Integer.parseInt(args[0]) / 10f;
                    if (speed > 1) {
                        player.sendMessage(Main.getPrefix() + Text.get("speed.error"));
                    } else {
                        if (player.isFlying()) {
                            player.setAllowFlight(true);
                            player.setFlying(true);
                            player.setFlySpeed(speed);
                            if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) {
                                player.sendMessage(
                                        Main.getPrefix() + Text.get("speed.fly.success").replace("%s", args[0]));
                            }
                        } else if (player.isOnGround()) {
                            player.setWalkSpeed(speed);
                            if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) {
                                player.sendMessage(
                                        Main.getPrefix() + Text.get("speed.walk.success").replace("%s", args[0]));
                            }
                        }
                    }
                } else {
                    player.sendMessage(Main.getPrefix() + Text.get("speed.error.digit"));
                }
            } else {
                player.sendMessage(Main.getNoPerm());
            }
        } else if (args.length == 0) {
            if (player.hasPermission("nextron.speed.reset")) {
                player.setAllowFlight(true);
                player.setFlying(true);
                player.setFlySpeed((float) 0.1);
                player.setWalkSpeed((float) 0.2);
                player.sendMessage(Main.getPrefix() + Text.get("speed.reset"));
            } else {
                player.sendMessage(Main.getNoPerm());
            }
        } else {
            player.sendMessage(Main.getPrefix() + "§c/speed <amount>");
        }
    }
}
