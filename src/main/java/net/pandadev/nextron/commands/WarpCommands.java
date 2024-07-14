package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.arguments.objects.Warp;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.SettingsAPI;
import net.pandadev.nextron.utils.Utils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Objects;

@RootCommand
public class WarpCommands extends HelpBase {

    public WarpCommands() {
        super(
                "warp, Teleports you to a warp, /warp <warp>\n/w <home>",
                "setwarp, Sets a new warp, /setwarp <name>",
                "delwarp, Deletes a warp, /delwarp <warp>",
                "renamewarp, Renames a warp, /renamewarp <warp>");
    }

    @Execute(name = "warp", aliases = {"w"})
    @Permission("nextron.warp")
    public void warpCommand(@Context Player player, @Arg Warp warp) {
        if (Configs.warp.get("Warps." + warp.getName().toLowerCase()) == null) {
            player.sendMessage(Main.getPrefix() + Text.get("warp.error").replace("%w", warp.getName().toLowerCase()));
            return;
        }
        player.teleport((Location) Objects.requireNonNull(Configs.warp.get("Warps." + warp.getName().toLowerCase())));
        if (SettingsAPI.allowsFeedback(player))
            player.sendMessage(Main.getPrefix() + Text.get("warp.success").replace("%w", warp.getName().toLowerCase()));
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
    }

    @Execute(name = "setwarp")
    @Permission("nextron.setwarp")
    public void setWarpCommand(@Context Player player, @Arg String name) {
        if (Configs.warp.get("Warps." + name.toLowerCase()) != null) {
            player.sendMessage(Main.getPrefix() + Text.get("setwarp.error").replace("%w", name.toLowerCase()));
            return;
        }
        Configs.warp.set("Warps." + name.toLowerCase(), player.getLocation());
        Configs.saveWarpConfig();
        player.sendMessage(Main.getPrefix() + Text.get("setwarp.success").replace("%w", name.toLowerCase()));
    }

    @Execute(name = "delwarp")
    @Permission("nextron.delwarp")
    public void deleteWarpCommand(@Context CommandSender sender, @Arg Warp warp) {
        if (Configs.warp.get("Warps." + warp.getName().toLowerCase()) == null) {
            sender.sendMessage(Main.getPrefix() + Text.get("delwarp.error").replace("%w", warp.getName().toLowerCase()));
            return;
        }
        Configs.warp.set("Warps." + warp.getName().toLowerCase(), null);
        Configs.saveWarpConfig();
        sender.sendMessage(Main.getPrefix() + Text.get("delwarp.success").replace("%w", warp.getName().toLowerCase()));
    }

    @Execute(name = "renamewarp")
    @Permission("nextron.renamewarp")
    public void renameWarpCommand(@Context Player player, @Arg Warp warp) {
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
