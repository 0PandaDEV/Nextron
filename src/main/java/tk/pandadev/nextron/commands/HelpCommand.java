package tk.pandadev.nextron.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return false;
        }
        Player player = (Player) (sender);

        if (args.length == 0){
            player.sendMessage(
                    "§7Help menu for §x§b§1§8§0§f§f§lNextron §7with all commands",
                    "",
                    "/back",
                    "/enderchest",
                    "/features",
                    "/fly",
                    "/gamemode",
                    "/head",
                    "/heal",
                    "/home",
                    "/invsee",
                    "/menu",
                    "/rank",
                    "/rename",
                    "/rl",
                    "/speed",
                    "/sudo",
                    "/tpa",
                    "/tpaccept",
                    "/tphere",
                    "/vanish",
                    "/warp",
                    "",
                    "§7For detailed help/explanation type §a/help [command]"
            );
        } else {
            player.sendMessage(Main.getPrefix() + "§c/help [command]");
        }

        return false;
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