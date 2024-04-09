package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.Utils;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import net.pandadev.nextron.utils.commandapi.processors.Warp;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Objects;

public class WarpCommands extends HelpBase {

    public WarpCommands() {
        super("warp", "Teleports you to public available positions", "/warp <warp>\n/w <warp>\n/setwarp <name>\n/delwarp <warp>\n/renamewarp <warp> <name>");
    }

    @Command(names = {"warp", "w"}, permission = "nextron.warp", playerOnly = true)
    public void warpCommand(Player player, @Param(name = "warp") Warp warp) {
        if (Configs.warp.get("Warps." + warp.getName().toLowerCase()) == null) {
            player.sendMessage(Main.getPrefix() + Text.get("warp.error").replace("%w", warp.getName().toLowerCase()));
            return;
        }
        player.teleport((Location) Objects.requireNonNull(Configs.warp.get("Warps." + warp.getName().toLowerCase())));
        if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback"))
            player.sendMessage(Main.getPrefix() + Text.get("warp.success").replace("%w", warp.getName().toLowerCase()));
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
    }

    @Command(names = {"setwarp"}, permission = "nextron.warp.set", playerOnly = true)
    public void setWarpCommand(Player player, @Param(name = "name") String name) {
        if (Configs.warp.get("Warps." + name.toLowerCase()) != null) {
            player.sendMessage(Main.getPrefix() + Text.get("setwarp.error").replace("%w", name.toLowerCase()));
            return;
        }
        Configs.warp.set("Warps." + name.toLowerCase(), player.getLocation());
        Configs.saveWarpConfig();
        player.sendMessage(Main.getPrefix() + Text.get("setwarp.success").replace("%w", name.toLowerCase()));
    }

    @Command(names = {"delwarp"}, permission = "nextron.warp.delete")
    public void deleteWarpCommand(CommandSender sender, @Param(name = "warp") Warp warp) {
        if (Configs.warp.get("Warps." + warp.getName().toLowerCase()) == null) {
            sender.sendMessage(Main.getPrefix() + Text.get("delwarp.error").replace("%w", warp.getName().toLowerCase()));
            return;
        }
        Configs.warp.set("Warps." + warp.getName().toLowerCase(), null);
        Configs.saveWarpConfig();
        sender.sendMessage(Main.getPrefix() + Text.get("delwarp.success").replace("%w", warp.getName().toLowerCase()));
    }

    @Command(names = {"renamewarp"}, permission = "nextron.warp.rename", playerOnly = true)
    public void renameWarpCommand(Player player, @Param(name = "warp") Warp warp) {
        if (Configs.warp.get("Warps." + warp.getName().toLowerCase()) == null) {
            player.sendMessage(Main.getPrefix() + Text.get("warp.error").replace("%w", warp.getName().toLowerCase()));
            return;
        }

        new AnvilGUI.Builder()
                .onClick((state, text) -> {
                    if (Utils.countWords(text.getText()) > 1) {
                        player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                        return Collections.singletonList(
                                AnvilGUI.ResponseAction.replaceInputText(Text.get("anvil_gui_one_word")));
                    }
                    Configs.warp.set("Warps." + text.getText(), Configs.warp.get("Warps." + warp.getName()));
                    Configs.warp.set("Warps." + warp.getName(), null);
                    Configs.saveHomeConfig();
                    player.sendMessage(Main.getPrefix() + Text.get("warp.rename.success").replace("%w", warp.getName())
                            .replace("%n", text.getText()));
                    return Collections.singletonList(AnvilGUI.ResponseAction.close());
                })
                .text(warp.getName())
                .preventClose()
                .itemLeft(new ItemStack(Material.NAME_TAG))
                .title("Enter a name")
                .plugin(Main.getInstance())
                .open(player);
    }
}
