package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand extends HelpBase {

    public GodCommand() {
        super("god, Makes a player invulnerable, /god [player]");
    }

    @Command(names = {"god"}, permission = "nextron.god")
    public void godCommand(CommandSender sender, @Param(name = "target", required = false) Player target) {
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            Player player = (Player) (sender);

            player.setInvulnerable(!player.isInvulnerable());
            if (player.isInvulnerable())
                player.sendMessage(Main.getPrefix() + Text.get("god.on"));
            else
                player.sendMessage(Main.getPrefix() + Text.get("god.off"));
            return;
        }

        if (!sender.hasPermission("nextron.god.other")) {
            sender.sendMessage(Main.getNoPerm());
            return;
        }

        target.setInvulnerable(!target.isInvulnerable());
        if (target.isInvulnerable())
            sender.sendMessage(Main.getPrefix() + Text.get("god.on.other").replace("%p", target.getName()));
        else
            sender.sendMessage(Main.getPrefix() + Text.get("god.off.other").replace("%p", target.getName()));
    }

}