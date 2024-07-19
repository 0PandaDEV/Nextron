package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.SettingsAPI;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

@Command(name = "head")
@Permission("nextron.head")
public class HeadCommand extends HelpBase {

    public HeadCommand() {
        super("head, Gives you the head of any player, /head <player>");
    }

    @Execute
    public void headCommand(@Context Player player, @Arg Player target) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setDisplayName(target.getName());
        meta.setOwner(target.getName());
        item.setItemMeta(meta);
        Inventory inventory = player.getInventory();
        inventory.addItem(item);
        if (SettingsAPI.allowsFeedback(player))
            player.sendMessage(Main.getPrefix() + TextAPI.get("head.success").replace("%t", target.getName()));
    }
}
