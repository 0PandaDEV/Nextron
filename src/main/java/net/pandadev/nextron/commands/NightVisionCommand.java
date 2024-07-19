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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Command(name = "nightvision", aliases = {"nv"})
@Permission("nextron.nightvision")
public class NightVisionCommand extends HelpBase {

    public NightVisionCommand() {
        super("nightvision, Allows you to toggle nightivision, /nightvision [player]\n/nv [player]");
    }

    @Execute
    public void nightVisionCommand(@Context CommandSender sender, @OptionalArg Player target) {
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§6This command can only be run by a player!");
                return;
            }

            Player player = (Player) (sender);

            if (!player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 255, false, false, false));
            } else {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }

            return;
        }

        if (!target.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            target.addPotionEffect(
                    new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 255, false, false, false));
            sender.sendMessage(Main.getPrefix() + TextAPI.get("night.vision.add").replace("%p", target.getName()));
        } else {
            target.removePotionEffect(PotionEffectType.NIGHT_VISION);
            sender.sendMessage(Main.getPrefix() + TextAPI.get("night.vision.remove").replace("%p", target.getName()));
        }
    }

}