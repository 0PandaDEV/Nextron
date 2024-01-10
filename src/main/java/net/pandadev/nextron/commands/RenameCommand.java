package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import net.pandadev.nextron.Main;

import java.util.ArrayList;
import java.util.List;

public class RenameCommand extends CommandBase implements CommandExecutor, TabCompleter {

    public RenameCommand() {
        super("rename", "Renames the item you are currently holding", "/rename <name>", "", "nextron.rename");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }
        Player player = (Player) (sender);

        if (args.length >= 1) {
            ItemMeta itemMeta = player.getItemInHand().getItemMeta();
            if (itemMeta == null) {
                player.sendMessage(Main.getPrefix() + Text.get("rename.error"));
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                if (i > 0)
                    sb.append(" ");
                sb.append(args[i]);
            }

            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', String.valueOf(sb)));
            player.getItemInHand().setItemMeta(itemMeta);
        } else {
            player.sendMessage(Main.getPrefix() + "§c/rename <name>");
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
            if (!s1.startsWith(currentarg))
                continue;
            completerList.add(s);
        }

        return completerList;
    }

}