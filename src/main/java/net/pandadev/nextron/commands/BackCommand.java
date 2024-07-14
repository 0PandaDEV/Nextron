package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.SettingsAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "back")
@Permission("nextron.back")
public class BackCommand extends HelpBase {

    public BackCommand() {
        super("back, Teleports the player back to the last (death, tpa, home, warp) position., /back [player]");
    }

    @Execute
    void backCommand(@Context CommandSender sender, @OptionalArg Player target) {
        Player player = (Player) (sender);

        if (target == null) {
            SettingsAPI.setLastPosition(player, player.getLocation());
            player.teleport(SettingsAPI.getLastPosition(player));
            return;
        }

        SettingsAPI.setLastPosition(target, target.getLocation());
        target.teleport(SettingsAPI.getLastPosition(target));
        player.sendMessage(Main.getPrefix() + Text.get("back.other.success").replace("%p", target.getName()));
        return;
    }

}