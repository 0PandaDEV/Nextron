package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.SettingsAPI;
import org.bukkit.entity.Player;

import java.util.Objects;

@Command(name = "tphere", aliases = {"tph"})
@Permission("nextron.tphere")
public class TphereCommand extends HelpBase {

    public TphereCommand() {
        super("tphere, Teleports a player to you, /tphere <player>\n/tph <player>");
    }

    @Execute
    public void tphereCommand(@Context Player player, @Arg Player target) {
        if (!Objects.equals(target.getName(), player.getName())) {
            target.teleport(player.getLocation());

            if (SettingsAPI.allowsFeedback(player)) {
                player.sendMessage(
                        Main.getPrefix() + Text.get("tphere.success").replace("%t", target.getName()));
            }

        } else {
            player.sendMessage(Main.getPrefix() + Text.get("tphere.error"));
        }
    }
}
