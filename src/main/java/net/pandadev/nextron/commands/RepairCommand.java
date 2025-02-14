package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Optional;

@Command(name = "repair")
@Permission("nextron.repair")
public class RepairCommand extends HelpBase {

    public RepairCommand() {
        super("repair, Repairs the current item in a player hand, /repair [player]");
    }

    @Execute
    public void repairCommand(@Context CommandSender sender, @Arg Optional<Player> target) {
        if (target.isEmpty()) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if (itemInHand.getItemMeta() == null) {
                player.sendMessage(Main.getPrefix() + TextAPI.get("repair.error"));
                return;
            }
            if (!(itemInHand.getItemMeta() instanceof Damageable damageable)) {
                player.sendMessage(Main.getPrefix() + TextAPI.get("repair.nodamage"));
                return;
            }
            damageable.setDamage(0);
            itemInHand.setItemMeta((org.bukkit.inventory.meta.ItemMeta) damageable);
            player.sendMessage(Main.getPrefix() + TextAPI.get("repair.success"));
        } else {
            Player targetPlayer = target.get();
            ItemStack itemInHand = targetPlayer.getInventory().getItemInMainHand();
            if (itemInHand.getItemMeta() == null) {
                sender.sendMessage(Main.getPrefix() + TextAPI.get("repair.error.other").replace("%t", targetPlayer.getName()));
                return;
            }
            if (!(itemInHand.getItemMeta() instanceof Damageable damageable)) {
                sender.sendMessage(Main.getPrefix() + TextAPI.get("repair.nodamage"));
                return;
            }
            damageable.setDamage(0);
            itemInHand.setItemMeta((org.bukkit.inventory.meta.ItemMeta) damageable);
            sender.sendMessage(Main.getPrefix() + TextAPI.get("repair.success").replace("%t", targetPlayer.getName()));
        }
    }
}