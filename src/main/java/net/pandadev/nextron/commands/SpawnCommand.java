package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@RootCommand
public class SpawnCommand extends HelpBase {

    public SpawnCommand() {
        super("spawn, Teleports to the spawn (to set use /setspawn), /spawn [player]\n/sp [player]",
                "setspawn, Sets the spawn (teleport to it /spawn), /setspawn");
    }

    @Execute(name = "spawn", aliases = {"sp"})
    @Permission("nextron.spawn")
    public void spawnCommand(@Context Player player, @OptionalArg Player target) {
        if (target != null) {
            if (!player.hasPermission("nextron.spawn.other")) {
                player.sendMessage(Main.getNoPerm());
                return;
            }

            if (Main.getInstance().getConfig().get("spawn") == null) {
                player.sendMessage(Main.getPrefix() + Text.get("setspawn.error"));
                return;
            }

            target.teleport((Location) Main.getInstance().getConfig().get("spawn"));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
            target.sendMessage(Main.getPrefix() + Text.get("spawn.teleport"));
            player.sendMessage(Main.getPrefix() + Text.get("spawn.teleport.other").replace("%p", target.getName()));
            return;
        }

        if (Main.getInstance().getConfig().get("spawn") == null) {
            player.sendMessage(Main.getPrefix() + Text.get("setspawn.error"));
            return;
        }
        player.teleport((Location) Main.getInstance().getConfig().get("spawn"));
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
        player.sendMessage(Main.getPrefix() + Text.get("spawn.teleport"));
    }

    @Execute(name = "setspawn")
    @Permission("nextron.setspawn")
    public void setSpawnCommand(@Context Player player) {
        Main.getInstance().getConfig().set("spawn", player.getLocation());
        Main.getInstance().saveConfig();
        player.sendMessage(Main.getPrefix() + Text.get("setspawn.success"));
    }


}