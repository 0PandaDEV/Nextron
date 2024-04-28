package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand extends HelpBase {

    public FlyCommand() {
        super("fly, Enables/disables fly for you or another player, /fly [player]");
    }

    @Command(names = {"fly"}, permission = "nextron.fly")
    public void flyCommand(CommandSender sender, @Param(name = "target", required = false) Player target) {
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            Player player = (Player) (sender);

            if (player.getAllowFlight()) {
                System.out.println("off");
                player.setAllowFlight(false);
                if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) {
                    player.sendMessage(Main.getPrefix() + Text.get("fly.off"));
                }
            } else {
                System.out.println("on");
                player.setAllowFlight(true);
                if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) {
                    player.sendMessage(Main.getPrefix() + Text.get("fly.on"));
                }
            }

            player.setFallDistance(0.0f);

            return;
        }

        if (target.getAllowFlight()) {
            target.setAllowFlight(false);
            sender.sendMessage(Main.getPrefix() + Text.get("fly.other.off").replace("%t", target.getName()));
        } else {
            target.setAllowFlight(true);
            sender.sendMessage(Main.getPrefix() + Text.get("fly.other.on").replace("%t", target.getName()));
        }

        target.setFallDistance(0.0f);
    }
}
