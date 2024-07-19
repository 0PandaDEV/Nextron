package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "god")
@Permission("nextron.god")
public class GodCommand extends HelpBase {

    public GodCommand() {
        super("god, Makes a player invulnerable, /god [player]");
    }

    @Execute
    public void godCommand(@Context CommandSender sender, @OptionalArg Player target) {
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            Player player = (Player) (sender);

            player.setInvulnerable(!player.isInvulnerable());
            if (player.isInvulnerable())
                player.sendMessage(Main.getPrefix() + TextAPI.get("god.on"));
            else
                player.sendMessage(Main.getPrefix() + TextAPI.get("god.off"));
            return;
        }

        if (!sender.hasPermission("nextron.god.other")) {
            sender.sendMessage(Main.getNoPerm());
            return;
        }

        target.setInvulnerable(!target.isInvulnerable());
        if (target.isInvulnerable())
            sender.sendMessage(Main.getPrefix() + TextAPI.get("god.on.other").replace("%p", target.getName()));
        else
            sender.sendMessage(Main.getPrefix() + TextAPI.get("god.off.other").replace("%p", target.getName()));
    }

}