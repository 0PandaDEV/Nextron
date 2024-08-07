package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.SettingsAPI;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "heal")
@Permission("nextron.heal")
public class HealCommand extends HelpBase {

    public HealCommand() {
        super("heal, Fills up your hunger and hearts, /heal [player]");
    }

    @Execute
    public void healCommand(@Context CommandSender sender, @OptionalArg Player target) {
        if (target == null && sender instanceof Player) {
            Player player = (Player) (sender);

            if (player.getHealth() != player.getMaxHealth() || player.getFoodLevel() != 20) {
                player.setHealth(player.getMaxHealth());
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

        if (target.getHealth() != 20.0 || target.getFoodLevel() != 20) {
            target.setHealth(20.0);
            target.setFoodLevel(20);
            target.sendMessage(Main.getPrefix() + TextAPI.get("heal.other.success").replace("%p",
                    sender instanceof Player ? sender.getName() : "Console"));
            target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            return;
        }
        sender.sendMessage(Main.getPrefix() + TextAPI.get("heal.other.error").replace("%t", target.getName()));
    }

}