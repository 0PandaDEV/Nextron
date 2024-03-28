package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadCommand extends HelpBase {

    public HeadCommand() {
        super("head", "Gives you the head of any player", "/head <player>");
    }

    @Command(names = {"head"}, permission = "nextron.head", playerOnly = true)
    public void headCommand(Player player, @Param(name = "target") Player target) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setDisplayName(target.getName());
        meta.setOwner(target.getName());
        item.setItemMeta(meta);
        Inventory inventory = player.getInventory();
        inventory.addItem(item);
        if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback"))
            player.sendMessage(Main.getPrefix() + Text.get("head.success").replace("%t", target.getName()));
    }
}
