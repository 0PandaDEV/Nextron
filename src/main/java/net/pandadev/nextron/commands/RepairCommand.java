package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

@Command(name = "repair")
@Permission("nextron.repair")
public class RepairCommand extends HelpBase {

    public RepairCommand() {
        super("repair, Repairs the current item in a player hand, /repair [player]");
    }

    @Execute
    public void repairCommand(@Context CommandSender sender, @OptionalArg Player target) {
        if (target == null) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if (itemInHand.getItemMeta() == null) {
                player.sendMessage(Main.getPrefix() + Text.get("repair.error"));
                return;
            }
            if (!(itemInHand.getItemMeta() instanceof Damageable)) {
                player.sendMessage(Main.getPrefix() + Text.get("repair.nodamage"));
                return;
            }
            Damageable damageable = (Damageable) itemInHand.getItemMeta();
            damageable.setDamage(0);
            itemInHand.setItemMeta(damageable);
            player.sendMessage(Main.getPrefix() + Text.get("repair.success"));
            return;
        }

        ItemStack itemInHand = target.getInventory().getItemInMainHand();
        if (itemInHand.getItemMeta() == null) {
            sender.sendMessage(Main.getPrefix() + Text.get("repair.error.other").replace("%t", target.getName()));
            return;
        }
        if (!(itemInHand.getItemMeta() instanceof Damageable)) {
            sender.sendMessage(Main.getPrefix() + Text.get("repair.nodamage"));
            return;
        }
        Damageable damageable = (Damageable) itemInHand.getItemMeta();
        damageable.setDamage(0);
        itemInHand.setItemMeta(damageable);
        sender.sendMessage(Main.getPrefix() + Text.get("repair.success").replace("%t", target.getName()));
    }
}