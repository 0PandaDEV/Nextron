package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.SettingsAPI;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

@Command(name = "heal")
@Permission("nextron.heal")
public class HealCommand extends HelpBase {

    public HealCommand() {
        super("heal, Fills up your hunger and hearts, /heal [player]");
    }

    @Execute
    public void healCommand(@Context CommandSender sender, @Arg Optional<Player> target) {
        if (target.isEmpty() && sender instanceof Player player) {
            double maxHealth = player.getAttribute(Attribute.MAX_HEALTH).getValue();
            if (player.getHealth() != maxHealth || player.getFoodLevel() != 20) {
                player.setHealth(maxHealth);
                player.setFoodLevel(20);
                if (SettingsAPI.allowsFeedback(player)) {
                    player.sendMessage(Main.getPrefix() + TextAPI.get("heal.success"));
                }
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                return;
            }
            player.sendMessage(Main.getPrefix() + TextAPI.get("heal.error"));
            return;
        }

        Player targetPlayer = target.get();
        double maxHealth = targetPlayer.getAttribute(Attribute.MAX_HEALTH).getValue();
        if (targetPlayer.getHealth() != maxHealth || targetPlayer.getFoodLevel() != 20) {
            targetPlayer.setHealth(maxHealth);
            targetPlayer.setFoodLevel(20);
            targetPlayer.sendMessage(Main.getPrefix() + TextAPI.get("heal.other.success")
                    .replace("%p", sender instanceof Player ? sender.getName() : "Console"));
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            return;
        }
        sender.sendMessage(Main.getPrefix() + TextAPI.get("heal.other.error").replace("%t", targetPlayer.getName()));
    }

}