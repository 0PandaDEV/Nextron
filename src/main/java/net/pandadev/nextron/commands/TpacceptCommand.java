package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.SettingsAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@Command(name = "tpaccept")
@Permission("nextron.tpaccept")
public class TpacceptCommand extends HelpBase {

    public TpacceptCommand() {
        super("tpaccept, Accepts an incoming tpa request, /tpaccept");
    }

    @Execute
    public void tpacceptCommand(@Context Player player) {
        Player target = Main.tpa.get(player);

        if (target != null) {

            target.teleport(player.getLocation());

            if (SettingsAPI.allowsFeedback(target)) {
                target.sendMessage(
                        Main.getPrefix() + Text.get("tpaccept.player.success").replace("%p", player.getName()));
            }
            if (SettingsAPI.allowsFeedback(player)) {
                player.sendMessage(
                        Main.getPrefix() + Text.get("tpaccept.target.success").replace("%t", target.getName()));
            }

            target.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

            Main.tpa.remove(player);
            Main.tpa.remove(target);

        } else {
            player.sendMessage(Main.getPrefix() + Text.get("tpaccept.error"));
        }
    }

}
