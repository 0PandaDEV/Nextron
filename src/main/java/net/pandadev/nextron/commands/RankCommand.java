package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.RankAPI;
import net.pandadev.nextron.arguments.objects.Rank;
import net.pandadev.nextron.guis.features.RankGUIs;
import net.pandadev.nextron.utils.Utils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

@RootCommand
public class RankCommand extends HelpBase {

    public RankCommand() {
        super("rank, Allows you to create ranks with prefixes to group players, /rank create\n/rank delete <rank>\n/rank modify <rank> <name | prefix>\n/rank remove <player>\n/rank set <player> <rank>");
    }

    @Execute(name = "rank")
    @Permission("nextron.rank")
    public void rankCommand(@Context Player player) {
        if (Main.getInstance().getConfig().getConfigurationSection("Ranks") == null || Main
                .getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false).isEmpty()) {
            player.sendMessage(Main.getPrefix() + Text.get("maingui.no.ranks"));
            player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
            return;
        }

        RankGUIs.manager(player);
    }

    @Execute(name = "rank set")
    @Permission("nextron.rank.set")
    public void setRankCommand(@Context CommandSender sender, @Arg Player target, @Arg Rank rank) {
        RankAPI.setRank(sender, target, rank.getName());
        RankAPI.checkRank(target);
    }

    @Execute(name = "rank remove")
    @Permission("nextron.rank.remove")
    public void removeRankCommand(@Context CommandSender sender, @Arg Player target) {
        RankAPI.removeRanks(target);
        RankAPI.checkRank(target);

        sender.sendMessage(Main.getPrefix()
                + Text.get("rank.remove.success").replace("%t", target.getName()));
    }

    @Execute(name = "rank delete")
    @Permission("nextron.rank.delete")
    public void removeRankCommand(@Context CommandSender sender, @Arg Rank rank) {
        RankAPI.deleteRank((Player) sender, rank.getName().toLowerCase());
    }

    @Execute(name = "rank create")
    @Permission("nextron.rank.create")
    public void createRankCommand(@Context Player player) {
        RankGUIs.templateRanks(player);
    }

    @Execute(name = "rank prefix")
    @Permission("nextron.rank.prefix")
    public void prefixRankCommand(@Context Player player, @Arg Rank rank) {
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

    @Execute(name = "rank name")
    @Permission("nextron.rank.name")
    public void nameRankCommand(@Context Player player, @Arg Rank rank) {
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