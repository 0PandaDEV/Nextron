package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

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
            Configs.settings.set(player.getUniqueId() + ".lastback", player.getLocation());
            Configs.settings.set(player.getUniqueId() + ".isback", true);
            Configs.saveSettingsConfig();
            player.teleport((Location) Objects.requireNonNull(Configs.settings.get(player.getUniqueId() + ".lastpos")));
            return;
        }


        Configs.settings.set(target.getUniqueId() + ".lastback", player.getLocation());
        Configs.settings.set(target.getUniqueId() + ".isback", true);
        Configs.saveSettingsConfig();
        target.teleport((Location) Objects.requireNonNull(Configs.settings.get(target.getUniqueId() + ".lastpos")));
        player.sendMessage(Main.getPrefix() + Text.get("back.other.success").replace("%p", target.getName()));
        return;
    }

}