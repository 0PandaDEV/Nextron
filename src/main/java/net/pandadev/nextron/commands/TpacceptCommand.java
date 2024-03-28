package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.commandapi.Command;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class TpacceptCommand extends HelpBase {

    public TpacceptCommand() {
        super("tpaccept", "Accepts an incoming tpa request", "/tpaccept");
    }

    @Command(names = {"tpaccept"}, permission = "nextron.tpaccept")
    public void tpacceptCommand(Player player) {
        Player target = Main.tpa.get(player);

        if (target != null) {

            target.teleport(player.getLocation());

            if (Configs.settings.getBoolean(target.getUniqueId() + ".feedback")) {
                target.sendMessage(
                        Main.getPrefix() + Text.get("tpaccept.player.success").replace("%p", player.getName()));
            }
            if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) {
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
