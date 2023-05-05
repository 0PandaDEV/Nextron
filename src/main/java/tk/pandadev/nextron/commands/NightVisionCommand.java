package tk.pandadev.nextron.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.LanguageLoader;

import java.util.ArrayList;
import java.util.List;

public class NightVisionCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§6This command can only be run by a player!");
            return false;
        }
        Player player = (Player) (sender);

        if (args.length == 0){

            if (!player.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 255, false, false, false));
            } else {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }

        } else if (args.length == 1){

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null){player.sendMessage(Main.getInvalidPlayer()); return false;}

            if (!target.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
                target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 255, false, false, false));
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("night_vision_add"));
            } else {
                target.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("night_vision_remove"));
            }

        } else {
            player.sendMessage(Main.getPrefix() + "§c/nightvision [player]");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player) (sender);


        return list;
    }

}