package tk.pandadev.nextron.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends CommandBase implements TabCompleter {

    public HelpCommand(){
        super("help", "Gives you a list of all commands with a short description", "/help", "nextron.help");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0){
            sender.sendMessage(
                    "",
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
                    "§7For detailed help/explanation type §a/help [command]",
                    ""
            );
        } else if (args.length == 1){
            String usage = CommandBase.getUsage(args[0]).contains("\n") ? "\n" + CommandBase.getUsage(args[0]) : CommandBase.getUsage(args[0]) + "";

            sender.sendMessage(
                    "§8-----< §x§b§1§8§0§f§f§lHelp menu for " + args[0] + " §8>-----",
                    "",
                    "§7Name: §a" + CommandBase.getName(args[0]),
                    "§7Description: §a" + CommandBase.getDescription(args[0]),
                    "§7Usage: §a" + usage,
                    "",
                    "§8---------------------" + args[0].replaceAll("[a-z]", "-") + "-------"
            );
        } else {
            sender.sendMessage(Main.getPrefix() + "§c/help [command]");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player) (sender);

        if (args.length == 1){
            CommandBase.commands.forEach((name, description) -> {
                list.add(name);
            });
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