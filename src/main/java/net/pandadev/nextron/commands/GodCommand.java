package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

@Command(name = "god")
@Permission("nextron.god")
public class GodCommand extends HelpBase {

    public GodCommand() {
        super("god, Makes a player invulnerable, /god [player]");
    }

    @Execute
    public void godCommand(@Context CommandSender sender, @Arg Optional<Player> target) {
        if (target.isEmpty()) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            player.setInvulnerable(!player.isInvulnerable());
            if (player.isInvulnerable())
                player.sendMessage(Main.getPrefix() + TextAPI.get("god.on"));
            else
                player.sendMessage(Main.getPrefix() + TextAPI.get("god.off"));
        } else {
            if (!sender.hasPermission("nextron.god.other")) {
                sender.sendMessage(Main.getNoPerm());
                return;
            }
            Player targetPlayer = target.get();
            targetPlayer.setInvulnerable(!targetPlayer.isInvulnerable());
            if (targetPlayer.isInvulnerable())
                sender.sendMessage(Main.getPrefix() + TextAPI.get("god.on.other").replace("%p", targetPlayer.getName()));
            else
                sender.sendMessage(Main.getPrefix() + TextAPI.get("god.off.other").replace("%p", targetPlayer.getName()));
        }
    }

}