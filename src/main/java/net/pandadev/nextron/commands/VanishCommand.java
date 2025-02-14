package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.SettingsAPI;
import net.pandadev.nextron.apis.VanishAPI;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

@Command(name = "vanish", aliases = {"v"})
@Permission("nextron.vanish")
public class VanishCommand extends HelpBase {

    public VanishCommand() {
        super("vanish, Hides you form the tab list and other players can't see you, /vanish [player]\n/v [player]");
    }

    @Execute
    public void vanishCommand(@Context CommandSender sender, @Arg Optional<Player> target) {
        if (target.isPresent()) {
            Player targetPlayer = target.get();
            if (VanishAPI.isVanish(targetPlayer)) {
                VanishAPI.setVanish(targetPlayer, false);
                sender.sendMessage(Main.getPrefix() + TextAPI.get("unvanish.other").replace("%t", targetPlayer.getName()));
                if (SettingsAPI.allowsVanishMessage(targetPlayer)) {
                    String joinMessage = ChatColor.translateAlternateColorCodes('&',
                            Main.getInstance().getConfig().getString("join_message").replace("%p", targetPlayer.getName()));
                    if (targetPlayer.getUniqueId().equals(UUID.fromString("2dae5251-257a-4d28-b220-60fe24de72f0"))) {
                        joinMessage += " §8• §x§6§2§0§0§f§fNextron founder";
                    } else if (targetPlayer.getUniqueId().equals(UUID.fromString("51666aba-5e87-40c4-900c-1c77ce0b8e3c"))) {
                        joinMessage += " §8• §x§f§f§6§5§f§aAnya";
                    } else if (targetPlayer.getUniqueId().equals(UUID.fromString("621755d2-5cf7-48d6-acc6-73b539b66aac"))) {
                        joinMessage += " §8• §r§cWarrradu";
                    }
                    Bukkit.broadcastMessage(joinMessage);
                }
            } else {
                VanishAPI.setVanish(targetPlayer, true);
                sender.sendMessage(Main.getPrefix() + TextAPI.get("vanish.other").replace("%t", targetPlayer.getName()));
                if (SettingsAPI.allowsVanishMessage(targetPlayer)) {
                    String leaveMessage = ChatColor.translateAlternateColorCodes('&',
                            Main.getInstance().getConfig().getString("leave_message").replace("%p", targetPlayer.getName()));
                    if (targetPlayer.getUniqueId().equals(UUID.fromString("2dae5251-257a-4d28-b220-60fe24de72f0"))) {
                        leaveMessage += " §8• §x§6§2§0§0§f§fNextron founder";
                    } else if (targetPlayer.getUniqueId().equals(UUID.fromString("51666aba-5e87-40c4-900c-1c77ce0b8e3c"))) {
                        leaveMessage += " §8• §x§f§f§6§5§f§aAnya";
                    } else if (targetPlayer.getUniqueId().equals(UUID.fromString("621755d2-5cf7-48d6-acc6-73b539b66aac"))) {
                        leaveMessage += " §8• §r§cWarrradu";
                    }
                    Bukkit.broadcastMessage(leaveMessage);
                }
            }
            return;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        if (VanishAPI.isVanish(player)) {
            VanishAPI.setVanish(player, false);
            player.sendMessage(Main.getPrefix() + TextAPI.get("unvanish"));
            if (SettingsAPI.allowsVanishMessage(player)) {
                String joinMessage = ChatColor.translateAlternateColorCodes('&',
                        Main.getInstance().getConfig().getString("join_message").replace("%p", player.getName()));
                if (player.getUniqueId().equals(UUID.fromString("2dae5251-257a-4d28-b220-60fe24de72f0"))) {
                    joinMessage += " §8• §x§6§2§0§0§f§fNextron founder";
                } else if (player.getUniqueId().equals(UUID.fromString("51666aba-5e87-40c4-900c-1c77ce0b8e3c"))) {
                    joinMessage += " §8• §x§f§f§6§5§f§aAnya";
                } else if (player.getUniqueId().equals(UUID.fromString("621755d2-5cf7-48d6-acc6-73b539b66aac"))) {
                    joinMessage += " §8• §r§cWarrradu";
                }
                Bukkit.broadcastMessage(joinMessage);
            }
        } else {
            VanishAPI.setVanish(player, true);
            player.sendMessage(Main.getPrefix() + TextAPI.get("vanish"));
            if (SettingsAPI.allowsVanishMessage(player)) {
                String leaveMessage = ChatColor.translateAlternateColorCodes('&',
                        Main.getInstance().getConfig().getString("leave_message").replace("%p", player.getName()));
                if (player.getUniqueId().equals(UUID.fromString("2dae5251-257a-4d28-b220-60fe24de72f0"))) {
                    leaveMessage += " §8• §x§6§2§0§0§f§fNextron founder";
                } else if (player.getUniqueId().equals(UUID.fromString("51666aba-5e87-40c4-900c-1c77ce0b8e3c"))) {
                    leaveMessage += " §8• §x§f§f§6§5§f§aAnya";
                } else if (player.getUniqueId().equals(UUID.fromString("621755d2-5cf7-48d6-acc6-73b539b66aac"))) {
                    leaveMessage += " §8• §r§cWarrradu";
                }
                Bukkit.broadcastMessage(leaveMessage);
            }
        }
    }
}