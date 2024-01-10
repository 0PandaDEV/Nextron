package net.pandadev.nextron.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.guis.GUIs;

import java.util.ArrayList;
import java.util.List;

public class MenuCommand extends CommandBase implements CommandExecutor, TabCompleter {

    public MenuCommand(){
        super("menu", "Opens the menu where you can simply do everything", "/menu", "/m", "");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        Player player = (Player) (sender);

        if (label.equalsIgnoreCase("menu") || label.equalsIgnoreCase("m") && args.length == 0){
            GUIs.mainGui(player);
            player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 100, 1);
        } else {
            player.sendMessage(Main.getPrefix() + "§c/menu");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player playert = (Player) (sender);
        ArrayList<String> list = new ArrayList<String>();



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
