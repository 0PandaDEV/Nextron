package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVisionCommand extends HelpBase {

    public NightVisionCommand() {
        super("nightvision, Allows you to toggle nightivision, /nightvision [player]\n/nv [player]");
    }

    @Command(names = {"nightvision", "nv"}, permission = "nextron.nightvision")
    public void nightVisionCommand(CommandSender sender, @Param(name = "target", required = false) Player target) {
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


        System.out.println(target);
        if (!target.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            target.addPotionEffect(
                    new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 255, false, false, false));
            sender.sendMessage(Main.getPrefix() + Text.get("night.vision.add").replace("%p", target.getName()));
        } else {
            target.removePotionEffect(PotionEffectType.NIGHT_VISION);
            sender.sendMessage(Main.getPrefix() + Text.get("night.vision.remove").replace("%p", target.getName()));
        }
    }

}