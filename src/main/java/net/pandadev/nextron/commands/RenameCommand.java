package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

@Command(name = "rename")
@Permission("nextron.rename")
public class RenameCommand extends HelpBase {

    public RenameCommand() {
        super("rename, Renames the item you are currently holding, /rename <name>");
    }

    @Execute
    public void renameCommand(@Context Player player, @Join String name) {
        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
        if (itemMeta == null) {
            player.sendMessage(Main.getPrefix() + TextAPI.get("rename.error"));
            return;
        }

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', String.valueOf(name)));
        player.getInventory().getItemInMainHand().setItemMeta(itemMeta);
    }

}