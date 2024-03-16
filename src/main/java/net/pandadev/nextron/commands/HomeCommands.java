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
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HomeCommands extends CommandBase implements CommandExecutor, TabCompleter {

    public HomeCommands() {
        super("home", "Teleports you instant to a player set position", "/home <home>\n/h <home>", "nextron.home");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        Player player = (Player) (sender);

        if (label.equalsIgnoreCase("sethome") && args.length == 1) {

            if (Configs.home.getString("Homes." + player.getUniqueId() + "." + args[0].toLowerCase()) == null) {
                Configs.home.set("Homes." + player.getUniqueId() + "." + args[0].toLowerCase(), player.getLocation());
                Configs.saveHomeConfig();
                player.sendMessage(Main.getPrefix() + Text.get("sethome.success").replace("%h", args[0].toLowerCase()));
            } else {
                TextComponent yes = new TextComponent("§2[§aYes§2]");
                yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/aisdvja4f89dfjvwe4p9r8jdfvjw34r8q0dvj34-" + args[0].toLowerCase()));
                yes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to reset the position for §a" + args[0].toLowerCase()).create()));
                player.sendMessage(Main.getPrefix() + Text.get("home.reset.confirm"));
                player.spigot().sendMessage(ChatMessageType.SYSTEM, yes);
            }

        } else if (label.equalsIgnoreCase("sethome") && args.length == 0) {

            Configs.home.set("Homes." + player.getUniqueId() + ".default", player.getLocation());
            Configs.saveHomeConfig();
            player.sendMessage(Main.getPrefix() + Text.get("sethome.default.success"));

        } else if (label.equalsIgnoreCase("delhome") && args.length == 1) {

            if (Configs.home.getString("Homes." + player.getUniqueId() + "." + args[0].toLowerCase()) != null) {
                Configs.home.set("Homes." + player.getUniqueId() + "." + args[0].toLowerCase(), null);
                Configs.saveHomeConfig();
                player.sendMessage(Main.getPrefix() + Text.get("delhome.success").replace("%h", args[0].toLowerCase()));
            } else {
                player.sendMessage(Main.getPrefix() + Text.get("home.notfound").replace("%h", args[0].toLowerCase()));
            }

        } else if (label.equalsIgnoreCase("home") && args.length == 0) {

            if (Configs.home.getString("Homes." + player.getUniqueId() + ".default") != null) {
                player.teleport((Location) Configs.home.get("Homes." + player.getUniqueId() + ".default"));
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
                player.sendMessage(Main.getPrefix() + Text.get("home.default.success"));
            } else {
                player.sendMessage(Main.getPrefix() + Text.get("home.default.error"));
            }
        } else if (label.equalsIgnoreCase("home") || label.equalsIgnoreCase("h") && args.length == 1) {

            if (Configs.home.getString("Homes." + player.getUniqueId() + "." + args[0].toLowerCase()) != null) {
                player.teleport(
                        (Location) Configs.home.get("Homes." + player.getUniqueId() + "." + args[0].toLowerCase()));
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
                player.sendMessage(Main.getPrefix() + Text.get("home.success").replace("%h", args[0].toLowerCase()));
            } else {
                player.sendMessage(Main.getPrefix() + Text.get("home.notfound").replace("%h", args[0].toLowerCase()));
            }

        } else if (label.equalsIgnoreCase("renamehome") && args.length == 1) {

            if (Configs.home.getString("Homes." + player.getUniqueId() + "." + args[0].toLowerCase()) == null) {
                player.sendMessage(Main.getPrefix() + Text.get("home.notfound").replace("%h", args[0].toLowerCase()));
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
                                Configs.home.get("Homes." + player.getUniqueId() + "." + args[0]));
                        Configs.home.set("Homes." + player.getUniqueId() + "." + args[0], null);
                        Configs.saveHomeConfig();
                        player.sendMessage(Main.getPrefix() + Text.get("home.rename.success").replace("%h", args[0])
                                .replace("%n", text.getText()));
                        return Collections.singletonList(AnvilGUI.ResponseAction.close());
                    })
                    .text(args[0])
                    .itemLeft(new ItemStack(Material.NAME_TAG))
                    .title("Enter the new name")
                    .plugin(Main.getInstance())
                    .open(player);

        } else {
            player.sendMessage(Main.getPrefix() + "§c/home|sethome|delhome <name>");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player) (sender);

        if (Configs.home.getConfigurationSection("Homes") == null
                || Configs.home.getConfigurationSection("Homes").getKeys(false).isEmpty()) {
            return null;
        } else if (args.length == 1 && label.equalsIgnoreCase("home") || label.equalsIgnoreCase("delhome")
                || label.equalsIgnoreCase("h") || label.equalsIgnoreCase("renamehome")) {
            list.addAll(Objects.requireNonNull(Configs.home.getConfigurationSection("Homes." + playert.getUniqueId()))
                    .getKeys(false));
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
