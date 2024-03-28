package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.guis.features.RankGUIs;
import net.pandadev.nextron.utils.RankAPI;
import net.pandadev.nextron.utils.Utils;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import net.pandadev.nextron.utils.commandapi.processors.Rank;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class RankCommand extends HelpBase {

    public RankCommand() {
        super("rank", "Allows you to create ranks with prefixes to group players", "/rank <player> <rank>");
    }

    @Command(names = {"rank"}, permission = "nextron.rank", playerOnly = true)
    public void rankCommand(Player player) {
        if (Main.getInstance().getConfig().getConfigurationSection("Ranks") == null || Main
                .getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false).isEmpty()) {
            player.sendMessage(Main.getPrefix() + Text.get("maingui.no.ranks"));
            player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
            return;
        }

        RankGUIs.manager(player);
    }

    @Command(names = {"rank set"}, permission = "nextron.rank.set")
    public void setRankCommand(CommandSender sender, @Param(name = "target") Player target, @Param(name = "rank") Rank rank) {
        System.out.println(rank.getName());
        RankAPI.setRank(sender, target, rank.getName());
        RankAPI.checkRank(target);
    }

    @Command(names = {"rank remove"}, permission = "nextron.rank.remove")
    public void removeRankCommand(CommandSender sender, @Param(name = "target") Player target) {
        RankAPI.removeRanks(target);
        RankAPI.checkRank(target);

        sender.sendMessage(Main.getPrefix()
                + Text.get("rank.remove.success").replace("%t", target.getName()));
    }

    @Command(names = {"rank delete"}, permission = "nextron.rank.delete")
    public void removeRankCommand(CommandSender sender, @Param(name = "rank") Rank rank) {
        RankAPI.deleteRank((Player) sender, rank.getName().toLowerCase());
    }

    @Command(names = {"rank create"}, permission = "nextron.rank.create", playerOnly = true)
    public void createRankCommand(Player player) {
        RankGUIs.templateRanks(player);
    }

    @Command(names = {"rank modify prefix"}, permission = "nextron.rank.modify.prefix", playerOnly = true)
    public void prefixRankCommand(Player player, @Param(name = "rank") Rank rank) {
        new AnvilGUI.Builder()
                .onClick((state, text) -> {
                    RankAPI.setPrefix(player, rank.getName().toLowerCase(),
                            ChatColor.translateAlternateColorCodes('&', " " + text.getText()));
                    return Collections.singletonList(AnvilGUI.ResponseAction.close());
                })
                .text(Main.getInstance().getConfig().getString("Ranks." + rank.getName().toLowerCase() + ".prefix").replace("§", "&"))
                .itemLeft(new ItemStack(Material.NAME_TAG))
                .title("Enter the new prefix")
                .plugin(Main.getInstance())
                .open(player);
    }

    @Command(names = {"rank modify name"}, permission = "nextron.rank.modify.name", playerOnly = true)
    public void nameRankCommand(Player player, @Param(name = "rank") Rank rank) {
        new AnvilGUI.Builder()
                .onClick((state, text) -> {
                    if (Utils.countWords(text.getText()) > 1) {
                        player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                        return Collections.singletonList(AnvilGUI.ResponseAction
                                .replaceInputText(Text.get("anvil.gui.one.word")));
                    }
                    RankAPI.rename(player, rank.getName().toLowerCase(),
                            ChatColor.translateAlternateColorCodes('&', " " + text.getText()));
                    return Collections.singletonList(AnvilGUI.ResponseAction.close());
                })
                .text(rank.getName().toLowerCase())
                .itemLeft(new ItemStack(Material.NAME_TAG))
                .title("Enter the new name")
                .plugin(Main.getInstance())
                .open(player);
    }
}