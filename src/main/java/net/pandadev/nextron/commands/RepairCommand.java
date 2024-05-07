package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class RepairCommand extends HelpBase {

    public RepairCommand() {
        super("repair, Repairs the current item in a player hand, /repair [player]");
    }

    @Command(names = {"repair"}, permission = "nextron.repair")
    public void repairCommand(CommandSender sender, @Param(name = "target", required = false) Player target) {
        if (target == null) {
            Player player = (Player) sender;
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