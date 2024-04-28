package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SpawnCommand extends HelpBase {

    public SpawnCommand() {
        super("spawn, Teleports to the spawn (to set use /setspawn), /spawn [player]\n/sp [player]",
                "setspawn, Sets the spawn (teleport to it /spawn), /setspawn");
    }

    @Command(names = {"spawn", "sp"}, permission = "nextron.spawn")
    public void spawnCommand(Player player, @Param(name = "target") Player target) {
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

    @Command(names = {"setspawn"}, permission = "nextron.setspawn")
    public void setSpawnCommand(Player player) {
        Main.getInstance().getConfig().set("spawn", player.getLocation());
        Main.getInstance().saveConfig();
        player.sendMessage(Main.getPrefix() + Text.get("setspawn.success"));
    }


}