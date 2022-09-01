package tk.pandadev.essentialsp.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;

public class TpacceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getPrefix() + "§6Du musst diesen Command als Spieler ausführen!");
            return false;
        }

        Player player = (Player) (sender);

        if (args.length == 0){

            Player target = Main.tpa.get(player);

            if (target != null){

                target.teleport(player.getLocation());

                if (Main.getInstance().getSettingsConfig().getBoolean(target.getUniqueId() + ".feedback")){
                    target.sendMessage(Main.getPrefix() + "§7Du wurdest zu §a" + player.getName() + "§7 Teleportiert");
                }
                if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".feedback")){
                    player.sendMessage(Main.getPrefix() + "§a" + target.getName() + "§7 wurde zu dir Teleportiert");
                }

                target.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

                Main.tpa.remove(player);
                Main.tpa.remove(target);

            } else {
                player.sendMessage(Main.getPrefix() + "§cDu hast keine ausstehende Tpas");
            }

        } else {
            player.sendMessage(Main.getPrefix() + "§c/tpaccept");
        }

        return false;
    }
}
