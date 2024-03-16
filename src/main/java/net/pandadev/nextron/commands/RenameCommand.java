package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class RenameCommand extends CommandBase implements CommandExecutor {

    public RenameCommand() {
        super("rename", "Renames the item you are currently holding", "/rename <name>", "nextron.rename");
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


}