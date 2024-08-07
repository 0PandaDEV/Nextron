package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.SettingsAPI;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "fly")
@Permission("nextron.fly")
public class FlyCommand extends HelpBase {

    public FlyCommand() {
        super("fly, Enables/disables fly for you or another player, /fly [player]");
    }

    @Execute
    void flyCommand(@Context CommandSender sender, @OptionalArg Player target) {
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            Player player = (Player) (sender);

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

            return;
        }

        if (target.getAllowFlight()) {
            target.setAllowFlight(false);
            sender.sendMessage(Main.getPrefix() + TextAPI.get("fly.other.off").replace("%t", target.getName()));
        } else {
            target.setAllowFlight(true);
            sender.sendMessage(Main.getPrefix() + TextAPI.get("fly.other.on").replace("%t", target.getName()));
        }

        target.setFallDistance(0.0f);
    }
}
