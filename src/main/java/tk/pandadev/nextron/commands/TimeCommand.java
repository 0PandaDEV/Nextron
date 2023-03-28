package tk.pandadev.nextron.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.LanguageLoader;

import java.util.ArrayList;
import java.util.List;

public class TimeCommand extends CommandBase implements CommandExecutor, TabCompleter {

    public TimeCommand(){
        super("day", "Allows you to set the time", "/day | night | midnight | noon", "nextron.time");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }
        Player player = (Player) (sender);

        if (!player.hasPermission("nextron.time")) {player.sendMessage(Main.getNoPerm()); return;}

        if (label.equalsIgnoreCase("day") && args.length == 0) {
            player.getLocation().getWorld().setTime(1000);
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("time_success").replace("%d", "day"));
        } else if (label.equalsIgnoreCase("night") && args.length == 0) {
            player.getLocation().getWorld().setTime(13000);
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("time_success").replace("%d", "night"));
        } else if (label.equalsIgnoreCase("midnight") && args.length == 0) {
            player.getLocation().getWorld().setTime(18000);
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("time_success").replace("%d", "midnight"));
        } else if (label.equalsIgnoreCase("noon") && args.length == 0) {
            player.getLocation().getWorld().setTime(6000);
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("time_success").replace("%d", "noon"));
        } else sender.sendMessage(Main.getPrefix() + "§c/day | night | midnight | noon");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


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