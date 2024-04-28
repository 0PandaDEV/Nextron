package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HatCommand extends HelpBase {

    public HatCommand() {
        super("hat, Wear the current item in your hand, /hat\n/wear");
    }

    @Command(names = {"hat", "wear"}, permission = "nextron.hat", playerOnly = true)
    public void hatCommand(Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        player.getInventory().setHelmet(itemStack);

        player.sendMessage(Main.getPrefix() + Text.get("hat.success"));
    }

}