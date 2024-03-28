package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SpawnCommand extends HelpBase {

    public SpawnCommand() {
        super("spawn", "Allows you to set a spawn point accessible with /spawn", "/spawn [player]\n/sp");
    }

    @Command(names = {"spawn"}, permission = "nextron.spawn", playerOnly = true)
    public void spawnCommand(Player player) {
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