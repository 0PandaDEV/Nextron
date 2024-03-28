package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.Utils;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import net.pandadev.nextron.utils.commandapi.processors.HomeInfo;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Objects;

public class HomeCommands extends HelpBase {

    public HomeCommands() {
        super("home", "Teleports you instant to a player set position", "/home <home>\n/h <home>");
    }

    @Command(names = {"home", "h"}, permission = "nextron.home", playerOnly = true)
    public void home(Player player, @Param(name = "home", required = false) HomeInfo home) {
        if (home == null) {
            if (Configs.home.getString("Homes." + player.getUniqueId() + ".default") != null) {
                player.teleport((Location) Objects.requireNonNull(Configs.home.get("Homes." + player.getUniqueId() + ".default")));
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
                player.sendMessage(Main.getPrefix() + Text.get("home.default.success"));
            } else {
                player.sendMessage(Main.getPrefix() + Text.get("home.default.error"));
            }
            return;
        }

        if (Configs.home.getString("Homes." + player.getUniqueId() + "." + home.getName().toLowerCase()) != null) {
            player.teleport(
                    (Location) Objects.requireNonNull(Configs.home.get("Homes." + player.getUniqueId() + "." + home.getName().toLowerCase())));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
            player.sendMessage(Main.getPrefix() + Text.get("home.success").replace("%h", home.getName().toLowerCase()));
        } else {
            player.sendMessage(Main.getPrefix() + Text.get("home.notfound").replace("%h", home.getName().toLowerCase()));
        }
    }

    @Command(names = {"sethome"}, permission = "nextron.sethome", playerOnly = true)
    public void setHome(Player player, @Param(name = "name", required = false) String name) {
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
            yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/aisdvja4f89dfjvwe4p9r8jdfvjw34r8q0dvj34-" + name.toLowerCase()));
            yes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to reset the position for §a" + name.toLowerCase()).create()));
            player.sendMessage(Main.getPrefix() + Text.get("home.reset.confirm"));
            player.spigot().sendMessage(ChatMessageType.SYSTEM, yes);
        }
    }

    @Command(names = {"delhome"}, permission = "nextron.delhome", playerOnly = true)
    public void delHome(Player player, @Param(name = "home") HomeInfo home) {
        if (Configs.home.getString("Homes." + player.getUniqueId() + "." + home.getName().toLowerCase()) != null) {
            Configs.home.set("Homes." + player.getUniqueId() + "." + home.getName().toLowerCase(), null);
            Configs.saveHomeConfig();
            player.sendMessage(Main.getPrefix() + Text.get("delhome.success").replace("%h", home.getName().toLowerCase()));
        } else {
            player.sendMessage(Main.getPrefix() + Text.get("home.notfound").replace("%h", home.getName().toLowerCase()));
        }
    }

    @Command(names = {"renamehome"}, permission = "nextron.renamehome", playerOnly = true)
    public void renameHome(Player player, @Param(name = "home") HomeInfo home) {
        if (Configs.home.getString("Homes." + player.getUniqueId() + "." + home.getName().toLowerCase()) == null) {
            player.sendMessage(Main.getPrefix() + Text.get("home.notfound").replace("%h", home.getName().toLowerCase()));
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
