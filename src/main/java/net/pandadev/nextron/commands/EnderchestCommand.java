package net.pandadev.nextron.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import net.pandadev.nextron.Main;

import java.util.ArrayList;
import java.util.List;

public class EnderchestCommand extends CommandBase implements TabCompleter {
    public EnderchestCommand() {
        super("enderchest", "Opens a GUI where the player can access his enderchest.", "/enderchest [player]\n/ec [player]", "/ec", "nextron.enderchest");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {sender.sendMessage(Main.getCommandInstance()); return;}
        Player player = (Player) (sender);

        if (args.length == 0){player.openInventory(player.getEnderChest()); return;}

        if (args.length == 1) {
            if (!player.hasPermission("nextron.enderchest.other")){ player.sendMessage(Main.getNoPerm()); return;}

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) { player.sendMessage(Main.getInvalidPlayer()); return;}

            player.openInventory(target.getEnderChest());
            return;
        }

        player.sendMessage(Main.getPrefix() + "§c/enderchest <player>");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player)(sender);

        if (args.length == 1 && playert.hasPermission("nextron.enderchest.other")){
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        }

        ArrayList<String> completerList = new ArrayList<String>();
        String currentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (!s1.startsWith(currentarg)) continue;
            completerList.add(s);
        }

        return completerList;
    }
}
