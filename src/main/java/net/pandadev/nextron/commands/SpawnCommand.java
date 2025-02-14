package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Optional;

@RootCommand
public class SpawnCommand extends HelpBase {

    public SpawnCommand() {
        super("spawn, Teleports to the spawn (to set use /setspawn), /spawn [player]\n/sp [player]",
                "setspawn, Sets the spawn (teleport to it /spawn), /setspawn");
    }

    @Execute(name = "spawn", aliases = {"sp"})
    @Permission("nextron.spawn")
    public void spawnCommand(@Context Player player, @Arg Optional<Player> target) {
        if (target.isPresent()) {
            if (!player.hasPermission("nextron.spawn.other")) {
                player.sendMessage(Main.getNoPerm());
                return;
            }

            if (Main.getInstance().getConfig().get("spawn") == null) {
                player.sendMessage(Main.getPrefix() + TextAPI.get("setspawn.error"));
                return;
            }

            target.get().teleport((Location) Main.getInstance().getConfig().get("spawn"));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
            target.get().sendMessage(Main.getPrefix() + TextAPI.get("spawn.teleport"));
            player.sendMessage(Main.getPrefix() + TextAPI.get("spawn.teleport.other").replace("%p", target.get().getName()));
        } else {
            if (Main.getInstance().getConfig().get("spawn") == null) {
                player.sendMessage(Main.getPrefix() + TextAPI.get("setspawn.error"));
                return;
            }
            player.teleport((Location) Main.getInstance().getConfig().get("spawn"));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
            player.sendMessage(Main.getPrefix() + TextAPI.get("spawn.teleport"));
        }
    }

    @Execute(name = "setspawn")
    @Permission("nextron.setspawn")
    public void setSpawnCommand(@Context Player player) {
        Main.getInstance().getConfig().set("spawn", player.getLocation());
        Main.getInstance().saveConfig();
        player.sendMessage(Main.getPrefix() + TextAPI.get("setspawn.success"));
    }
}