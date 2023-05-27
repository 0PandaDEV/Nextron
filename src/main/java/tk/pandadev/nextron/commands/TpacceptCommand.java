package tk.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.Configs;

public class TpacceptCommand extends CommandBase implements CommandExecutor {

    public TpacceptCommand() {
        super("tpaccept", "Accepts an incoming tpa request", "/tpaccept", "", "nextron.tpaccept");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        Player player = (Player) (sender);

        if (args.length == 0) {

            Player target = Main.tpa.get(player);

            if (target != null) {

                target.teleport(player.getLocation());

                if (Configs.settings.getBoolean(target.getUniqueId() + ".feedback")) {
                    target.sendMessage(
                            Main.getPrefix() + Text.get("tpaccept.player.success").replace("%p", player.getName()));
                }
                if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) {
                    player.sendMessage(
                            Main.getPrefix() + Text.get("tpaccept.target.success").replace("%t", target.getName()));
                }

                target.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

                Main.tpa.remove(player);
                Main.tpa.remove(target);

            } else {
                player.sendMessage(Main.getPrefix() + Text.get("tpaccept.error"));
            }

        } else {
            player.sendMessage(Main.getPrefix() + "§c/tpaccept");
        }
    }
}
