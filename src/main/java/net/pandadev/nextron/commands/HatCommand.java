package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Command(name = "hat", aliases = {"wear"})
@Permission("nextron.hat")
public class HatCommand extends HelpBase {

    public HatCommand() {
        super("hat, Wear the current item in your hand, /hat\n/wear");
    }

    @Execute
    public void hatCommand(@Context Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        player.getInventory().setHelmet(itemStack);

        player.sendMessage(Main.getPrefix() + TextAPI.get("hat.success"));
    }

}