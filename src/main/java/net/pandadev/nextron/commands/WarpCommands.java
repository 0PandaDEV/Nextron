package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.listeners.InputListener;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WarpCommands extends CommandBase implements CommandExecutor, TabCompleter {

    public WarpCommands() {
        super("warp", "Teleports you to public available positions", "/warp <warp>\n/w <warp>", "nextron.warp");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (label.equalsIgnoreCase("setwarp") && args.length == 1) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            Player player = (Player) (sender);

            if (!player.hasPermission("nextron.setwarp")) {
                player.sendMessage(Main.getNoPerm());
                return;
            }
            if (Configs.warp.get("Warps." + args[0].toLowerCase()) != null) {
                player.sendMessage(Main.getPrefix() + Text.get("setwarp.error").replace("%w", args[0].toLowerCase()));
                return;
            }
            Configs.warp.set("Warps." + args[0].toLowerCase(), player.getLocation());
            Configs.saveWarpConfig();
            player.sendMessage(Main.getPrefix() + Text.get("setwarp.success").replace("%w", args[0].toLowerCase()));

        } else if (label.equalsIgnoreCase("delwarp") && args.length == 1) {

            if (!sender.hasPermission("nextron.delwarp")) {
                sender.sendMessage(Main.getNoPerm());
                return;
            }
            if (Configs.warp.get("Warps." + args[0].toLowerCase()) == null) {
                sender.sendMessage(Main.getPrefix() + Text.get("delwarp.error").replace("%w", args[0].toLowerCase()));
                return;
            }
            Configs.warp.set("Warps." + args[0].toLowerCase(), null);
            Configs.saveWarpConfig();
            sender.sendMessage(Main.getPrefix() + Text.get("delwarp.success").replace("%w", args[0].toLowerCase()));

        } else if (label.equalsIgnoreCase("warp") || label.equalsIgnoreCase("w") && args.length == 1) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            Player player = (Player) (sender);

            if (Configs.warp.get("Warps." + args[0].toLowerCase()) == null) {
                player.sendMessage(Main.getPrefix() + Text.get("warp.error").replace("%w", args[0].toLowerCase()));
                return;
            }
            player.teleport((Location) Configs.warp.get("Warps." + args[0].toLowerCase()));
            if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback"))
                player.sendMessage(Main.getPrefix() + Text.get("warp.success").replace("%w", args[0].toLowerCase()));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

        } else if (label.equalsIgnoreCase("renamewarp") && args.length == 1) {

            if (!sender.hasPermission("nextron.renamewarp")) {
                sender.sendMessage(Main.getNoPerm());
                return;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            Player player = (Player) (sender);

            if (Configs.warp.get("Warps." + args[0].toLowerCase()) == null) {
                sender.sendMessage(Main.getPrefix() + Text.get("warp.error").replace("%w", args[0].toLowerCase()));
                return;
            }

            player.sendMessage(Main.getPrefix() + "§7Type the new name for the warp in to the chat");
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1f);

            InputListener.listen(player.getUniqueId()).thenAccept(response -> {
                if (Utils.countWords(response) > 1) {
                    player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                    player.sendMessage(Text.get("anvil.gui.one.word"));
                    return;
                }
                Configs.warp.set("Warps." + response, Configs.warp.get("Warps." + args[0]));
                Configs.warp.set("Warps." + args[0], null);
                Configs.saveHomeConfig();
                sender.sendMessage(Main.getPrefix() + Text.get("warp.rename.success").replace("%h", args[0])
                        .replace("%n", response));
            });

        } else {
            sender.sendMessage(Main.getPrefix() + "§c/warp | setwarp | delwarp <NAME>");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        if (Configs.warp.getConfigurationSection("Warps") == null
                || Configs.warp.getConfigurationSection("Warps").getKeys(false).isEmpty()) {
            return null;
        } else if (args.length == 1 && label.equalsIgnoreCase("warp") || label.equalsIgnoreCase("delwarp")
                || label.equalsIgnoreCase("renamewarp")) {
            list.addAll(Objects.requireNonNull(Configs.warp.getConfigurationSection("Warps")).getKeys(false));
        }

        ArrayList<String> completerList = new ArrayList<String>();
        String currentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (!s1.startsWith(currentarg))
                continue;
            completerList.add(s);
        }

        return completerList;
    }
}
