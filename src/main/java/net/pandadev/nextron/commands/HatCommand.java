package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HatCommand extends CommandBase {

    public HatCommand() {
        super("hat", "War the current item in your hand", "/hat\n/wear", "nextron.hat");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }
        Player player = (Player) (sender);

        if (args.length == 0) {
            ItemStack itemStack = player.getInventory().getItemInMainHand();

            player.getInventory().setHelmet(itemStack);

            player.sendMessage(Main.getPrefix() + Text.get("hat.success"));
        }

    }

}