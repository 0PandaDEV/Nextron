package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.SettingsAPI;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

@Command(name = "fly")
@Permission("nextron.fly")
public class FlyCommand extends HelpBase {

    public FlyCommand() {
        super("fly, Enables/disables fly for you or another player, /fly [player]");
    }

    @Execute
    void flyCommand(@Context CommandSender sender, @Arg Optional<Player> target) {
        if (target.isEmpty()) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                if (SettingsAPI.allowsFeedback(player)) {
                    player.sendMessage(Main.getPrefix() + TextAPI.get("fly.off"));
                }
            } else {
                player.setAllowFlight(true);
                if (SettingsAPI.allowsFeedback(player)) {
                    player.sendMessage(Main.getPrefix() + TextAPI.get("fly.on"));
                }
            }
            player.setFallDistance(0.0f);
        } else {
            Player targetPlayer = target.get();
            if (targetPlayer.getAllowFlight()) {
                targetPlayer.setAllowFlight(false);
                sender.sendMessage(Main.getPrefix() + TextAPI.get("fly.other.off").replace("%t", targetPlayer.getName()));
            } else {
                targetPlayer.setAllowFlight(true);
                sender.sendMessage(Main.getPrefix() + TextAPI.get("fly.other.on").replace("%t", targetPlayer.getName()));
            }
            targetPlayer.setFallDistance(0.0f);
        }
    }
}
