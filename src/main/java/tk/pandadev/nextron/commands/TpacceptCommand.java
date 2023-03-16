package tk.pandadev.nextron.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.Configs;
import tk.pandadev.nextron.utils.LanguageLoader;

public class TpacceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return false;
        }

        Player player = (Player) (sender);

        if (args.length == 0){

            Player target = Main.tpa.get(player);

            if (target != null){

                target.teleport(player.getLocation());

                if (Configs.settings.getBoolean(target.getUniqueId() + ".feedback")){
                    player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tpaccept_player_success").replace("%p", player.getName()));
                }
                if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")){
                    player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tpaccept_target_success").replace("%t", target.getName()));
                }

                target.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

                Main.tpa.remove(player);
                Main.tpa.remove(target);

            } else {
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tpaccept_error"));
            }

        } else {
            player.sendMessage(Main.getPrefix() + "§c/tpaccept");
        }

        return false;
    }
}
