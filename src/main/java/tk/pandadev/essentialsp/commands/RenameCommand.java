package tk.pandadev.essentialsp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.LanguageLoader;

import java.util.ArrayList;
import java.util.List;

public class RenameCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) { sender.sendMessage(Main.getCommandInstance()); return false; }
        Player player = (Player) (sender);

        if (args.length == 1){
            ItemMeta itemMeta = player.getItemInHand().getItemMeta();
            if (itemMeta == null){player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rename_error")); return false;}
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', args[0]));
            player.getItemInHand().setItemMeta(itemMeta);
        } else {
            player.sendMessage(Main.getPrefix() + "§c/rename <name>");
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