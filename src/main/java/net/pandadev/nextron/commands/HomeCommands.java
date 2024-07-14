package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.arguments.objects.Home;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Objects;

@RootCommand
public class HomeCommands extends HelpBase {

    public HomeCommands() {
        super(
                "home, Teleports you to a home, /home [home]\n/h [home]",
                "sethome, Sets a new home, /sethome <name>",
                "delhome, Deletes a home, /delhome <home>",
                "renamehome, Renames a home, /renamehome <home>");
    }

    @Execute(name = "home", aliases = {"h"})
    @Permission("nextron.home")
    public void home(@Context Player player, @OptionalArg Home home) {
        if (home == null) {
            if (Configs.home.getString("Homes." + player.getUniqueId() + ".default") != null) {
                player.teleport((Location) Objects
                        .requireNonNull(Configs.home.get("Homes." + player.getUniqueId() + ".default")));
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
                player.sendMessage(Main.getPrefix() + Text.get("home.default.success"));
            } else {
                player.sendMessage(Main.getPrefix() + Text.get("home.default.error"));
            }
            return;
        }

        if (Configs.home.getString("Homes." + player.getUniqueId() + "." + home.getName().toLowerCase()) != null) {
            player.teleport(
                    (Location) Objects.requireNonNull(
                            Configs.home.get("Homes." + player.getUniqueId() + "." + home.getName().toLowerCase())));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
            player.sendMessage(Main.getPrefix() + Text.get("home.success").replace("%h", home.getName().toLowerCase()));
        } else {
            player.sendMessage(
                    Main.getPrefix() + Text.get("home.notfound").replace("%h", home.getName().toLowerCase()));
        }
    }

    @Execute(name = "sethome")
    @Permission("nextron.sethome")
    public void setHome(@Context Player player, @OptionalArg String name) {
        if (name == null) {
            Configs.home.set("Homes." + player.getUniqueId() + ".default", player.getLocation());
            Configs.saveHomeConfig();
            player.sendMessage(Main.getPrefix() + Text.get("sethome.default.success"));
            return;
        }

        if (Configs.home.getString("Homes." + player.getUniqueId() + "." + name.toLowerCase()) == null) {
            Configs.home.set("Homes." + player.getUniqueId() + "." + name.toLowerCase(), player.getLocation());
            Configs.saveHomeConfig();
            player.sendMessage(Main.getPrefix() + Text.get("sethome.success").replace("%h", name.toLowerCase()));
        } else {
            TextComponent yes = new TextComponent("§2[§aYes§2]");
            yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                    "/aisdvja4f89dfjvwe4p9r8jdfvjw34r8q0dvj34-" + name.toLowerCase()));
            yes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder("§7Click to reset the position for §a" + name.toLowerCase()).create()));
            player.sendMessage(Main.getPrefix() + Text.get("home.reset.confirm"));
            player.spigot().sendMessage(ChatMessageType.SYSTEM, yes);
        }
    }

    @Execute(name = "delhome")
    @Permission("nextron.delhome")
    public void delHome(@Context Player player, @Arg Home home) {
        if (Configs.home.getString("Homes." + player.getUniqueId() + "." + home.getName().toLowerCase()) != null) {
            Configs.home.set("Homes." + player.getUniqueId() + "." + home.getName().toLowerCase(), null);
            Configs.saveHomeConfig();
            player.sendMessage(
                    Main.getPrefix() + Text.get("delhome.success").replace("%h", home.getName().toLowerCase()));
        } else {
            player.sendMessage(
                    Main.getPrefix() + Text.get("home.notfound").replace("%h", home.getName().toLowerCase()));
        }
    }

    @Execute(name = "renamehome")
    @Permission("nextron.renamehome")
    public void renameHome(@Context Player player, @Arg Home home) {
        if (Configs.home.getString("Homes." + player.getUniqueId() + "." + home.getName().toLowerCase()) == null) {
            player.sendMessage(
                    Main.getPrefix() + Text.get("home.notfound").replace("%h", home.getName().toLowerCase()));
            return;
        }

        new AnvilGUI.Builder()
                .onClick((state, text) -> {
                    if (Utils.countWords(text.getText()) > 1) {
                        player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                        return Collections.singletonList(
                                AnvilGUI.ResponseAction.replaceInputText(Text.get("anvil_gui_one_word")));
                    }
                    Configs.home.set("Homes." + player.getUniqueId() + "." + text.getText(),
                            Configs.home.get("Homes." + player.getUniqueId() + "." + home.getName()));
                    Configs.home.set("Homes." + player.getUniqueId() + "." + home.getName(), null);
                    Configs.saveHomeConfig();
                    player.sendMessage(Main.getPrefix() + Text.get("home.rename.success").replace("%h", home.getName())
                            .replace("%n", text.getText()));
                    return Collections.singletonList(AnvilGUI.ResponseAction.close());
                })
                .text(home.getName())
                .itemLeft(new ItemStack(Material.NAME_TAG))
                .title("Enter the new name")
                .plugin(Main.getInstance())
                .open(player);
    }
}
