package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TphereCommand extends HelpBase {

    public TphereCommand() {
        super("tphere, Teleports a player to you, /tphere <player>\n/tph <player>");
    }

    @Command(names = {"tphere", "tph"}, permission = "nextron.tphere")
    public void tphereCommand(Player player, @Param(name = "target") Player target) {
        if (!Objects.equals(target.getName(), player.getName())) {
            target.teleport(player.getLocation());

            if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) {
                player.sendMessage(
                        Main.getPrefix() + Text.get("tphere.success").replace("%t", target.getName()));
            }

        } else {
            player.sendMessage(Main.getPrefix() + Text.get("tphere.error"));
        }
    }
}
