package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class RenameCommand extends HelpBase {

    public RenameCommand() {
        super("rename, Renames the item you are currently holding, /rename <name>");
    }

    @Command(names = {"rename"}, permission = "nextron.rename", playerOnly = true)
    public void renameCommand(Player player, @Param(name = "name", concated = true) String name) {
        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
        if (itemMeta == null) {
            player.sendMessage(Main.getPrefix() + Text.get("rename.error"));
            return;
        }

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', String.valueOf(name)));
        player.getInventory().getItemInMainHand().setItemMeta(itemMeta);
    }

}