package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.VanishAPI;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand extends HelpBase {

    public VanishCommand() {
        super("vanish, Hides you form the tab list and other players can't see you, /vanish [player]\n/v [player]");
    }

    @Command(names = {"vanish", "v"}, permission = "nextron.vanish")
    public void vanishCommand(CommandSender sender, @Param(name = "target", required = false) Player target) {
        if (target != null) {
            if (VanishAPI.isVanish(target)) {
                Main.getInstance().getVanishAPI().setVanish(target, false);
                sender.sendMessage(
                        Main.getPrefix() + Text.get("unvanish.other").replace("%t", target.getName()));
                if (Configs.settings.getBoolean(target.getUniqueId() + ".vanish." + "message")) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance()
                            .getConfig().getString("join_message").replace("%p", target.getName())));
                }
            } else {
                Main.getInstance().getVanishAPI().setVanish(target, true);
                sender.sendMessage(Main.getPrefix() + Text.get("vanish.other").replace("%t", target.getName()));
                if (Configs.settings.getBoolean(target.getUniqueId() + ".vanish." + "message")) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance()
                            .getConfig().getString("leave_message").replace("%p", target.getName())));
                }
            }
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        Player player = (Player) (sender);

        if (VanishAPI.isVanish(player)) {
            Main.getInstance().getVanishAPI().setVanish(player, false);
            player.sendMessage(Main.getPrefix() + Text.get("unvanish"));
            if (Configs.settings.getBoolean(player.getUniqueId() + ".vanish." + "message")) {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance()
                        .getConfig().getString("join_message").replace("%p", player.getName())));
            }
        } else {
            Main.getInstance().getVanishAPI().setVanish(player, true);
            player.sendMessage(Main.getPrefix() + Text.get("vanish"));
            if (Configs.settings.getBoolean(player.getUniqueId() + ".vanish." + "message")) {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance()
                        .getConfig().getString("leave_message").replace("%p", player.getName())));
            }
        }
    }
}