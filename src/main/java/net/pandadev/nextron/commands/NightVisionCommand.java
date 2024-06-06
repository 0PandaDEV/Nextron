package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class NightVisionCommand extends CommandBase implements TabCompleter {

    public NightVisionCommand() {
        super("nightvision", "Toggle the night vision effect for a player", "/nightvision [player]\n/nv [player]", "nextron.nightvision");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§6This command can only be run by a player!");
            return;
        }
        Player player = (Player) (sender);

        if (args.length == 0) {

            if (!player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 255, false, false, false));
            } else {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }

        } else if (args.length == 1) {

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                player.sendMessage(Main.getInvalidPlayer());
                return;
            }

            if (!target.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                target.addPotionEffect(
                        new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 255, false, false, false));
                player.sendMessage(Main.getPrefix() + Text.get("night.vision.add").replace("%p", target.getName()));
            } else {
                target.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.sendMessage(Main.getPrefix() + Text.get("night.vision.remove").replace("%p", target.getName()));
            }

        } else {
            player.sendMessage(Main.getPrefix() + "§c/nightvision [player]");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        if (args.length == 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        }

        ArrayList<String> completerList = new ArrayList<String>();
        String currentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (!s1.startsWith(currentarg))
                continue;
            completerList.add(s);
        }

        return completerList;
    }

}