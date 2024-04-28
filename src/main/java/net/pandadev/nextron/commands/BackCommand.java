package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class BackCommand extends HelpBase {

    public BackCommand() {
        super("back, Teleports the player back to the last (death, tpa, home, warp) position., /back [player]");
    }

    @Command(names = {"back"}, permission = "nextron.back")
    public void backCommand(CommandSender sender, @Param(name = "target", required = false) Player target) {


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