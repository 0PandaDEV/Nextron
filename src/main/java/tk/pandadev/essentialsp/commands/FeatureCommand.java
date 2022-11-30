package tk.pandadev.essentialsp.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.guis.featuretoggle.FeatureGui;

import java.util.ArrayList;
import java.util.List;

public class FeatureCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return false;
        }

        Player player = (Player) (sender);

        if (!(args.length == 0)){ player.sendMessage(Main.getPrefix() + "§c/features"); return false; }

        if (!player.isOp()) {player.sendMessage(Main.getNoPerm()); return false;}
        new FeatureGui().open(player);
        player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 100, 1);

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player playert = (Player) (sender);
        ArrayList<String> list = new ArrayList<String>();



        return list;
    }

}