package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpdenyCommand extends CommandBase {

    public TpdenyCommand() {
        super("tpdeny", "Denys an incoming tpa request", "/tpdeny", "/tpd", "nextron.tpdeny");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        Player player = (Player) (sender);

        if (args.length == 0) {

            Player target = Main.tpa.get(player);

            if (target != null) {
                Main.tpa.remove(player);
                Main.tpa.remove(target);

                player.sendMessage(Main.getPrefix() + Text.get("tpdeny.player").replace("%p", target.getName()));
                target.sendMessage(Main.getPrefix() + Text.get("tpdeny.target").replace("%p", player.getName()));

                target.playSound(target.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);

            } else {
                player.sendMessage(Main.getPrefix() + Text.get("tpaccept.error"));
            }
        }

    }

}
