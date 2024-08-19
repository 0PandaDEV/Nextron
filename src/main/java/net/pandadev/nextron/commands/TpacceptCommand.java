package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.SettingsAPI;
import net.pandadev.nextron.languages.TextAPI;
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
        boolean isTpaHere = false;

        if (target == null) {
            target = Main.tpahere.get(player);
            isTpaHere = true;
        }

        if (target != null) {
            target.teleport(player.getLocation());

            String successMessage = Main.getPrefix() + TextAPI.get("tpaccept.player.success").replace("%p", player.getName());
            if (SettingsAPI.allowsFeedback(target)) {
                target.sendMessage(successMessage);
            }
            if (SettingsAPI.allowsFeedback(player)) {
                player.sendMessage(Main.getPrefix() + TextAPI.get("tpaccept.target.success").replace("%t", target.getName()));
            }

            target.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

            if (isTpaHere) {
                Main.tpahere.remove(player);
                Main.tpahere.remove(target);
            } else {
                Main.tpa.remove(player);
                Main.tpa.remove(target);
            }
        } else {
            player.sendMessage(Main.getPrefix() + TextAPI.get("tpaccept.error"));
        }
    }

}
