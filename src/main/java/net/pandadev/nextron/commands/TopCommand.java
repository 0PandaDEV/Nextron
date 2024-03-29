package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TopCommand extends CommandBase implements TabCompleter {

    public TopCommand() {
        super("top", "Teleports you to the highest block above you", "/top", "nextron.top");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }
        Player player = (Player) (sender);

        if (args.length == 0) {
            Location location = player.getLocation();
            int highestY = player.getWorld().getHighestBlockYAt(location);
            if (highestY <= location.getBlockY()) {
                player.sendMessage(Main.getPrefix() + Text.get("top.no_blocks_above"));
                return;
            }
            location.setY(highestY + 1.0);
            player.teleport(location);
            if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback"))
                player.sendMessage(Main.getPrefix() + Text.get("top.success"));
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player) (sender);


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