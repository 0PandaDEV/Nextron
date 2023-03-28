package tk.pandadev.nextron.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;

import java.util.ArrayList;
import java.util.List;

public class EnderchestCommand extends CommandBase implements TabCompleter {
    public EnderchestCommand() {
        super("enderchest", "Opens a GUI where the player can access his enderchest.", "/enderchest [player]\n/ec [player]", "nextron.enderchest");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {sender.sendMessage(Main.getCommandInstance()); return;}
        Player player = (Player) (sender);

        if (args.length == 0) {

            player.openInventory(player.getEnderChest());

        } else if (args.length == 1) {
            if (!player.hasPermission("nextron.enderchest.other")){ player.sendMessage(Main.getNoPerm()); return;}
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) { player.sendMessage(Main.getInvalidPlayer()); return;}

            player.openInventory(target.getEnderChest());
        } else {
            player.sendMessage(Main.getPrefix() + "§c/enderchest <player>");
        }
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

        return list;
    }
}
