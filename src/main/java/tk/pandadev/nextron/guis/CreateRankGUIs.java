package tk.pandadev.nextron.guis;

import dev.triumphteam.gui.guis.Gui;
import games.negative.framework.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.LanguageLoader;
import tk.pandadev.nextron.utils.RankAPI;
import tk.pandadev.nextron.utils.Utils;

import java.util.Collections;

public class CreateRankGUIs {

    public static boolean ready;

    public static void templateRanks(Player player){
        Gui gui = Gui.gui()
                .disableAllInteractions()
                .rows(5)
                .title(Component.text("Template"))
                .create();

        gui.setItem(3,3, dev.triumphteam.gui.builder.item.ItemBuilder.from(Material.RED_DYE).setName("§cOwner").asGuiItem(event -> {
            manualRankCreation(player, "owner", "§4Owner §8• §f");
        }));

        gui.setItem(3,5, dev.triumphteam.gui.builder.item.ItemBuilder.from(Material.ORANGE_DYE).setName("§x§f§e§a§1§3§1Admin").asGuiItem(event -> {
            manualRankCreation(player, "admin", "§x§f§e§a§1§3§1Admin §8• §f");
        }));

        gui.setItem(3,7, dev.triumphteam.gui.builder.item.ItemBuilder.from(Material.PURPLE_DYE).setName("§x§c§d§7§4§f§bDev").asGuiItem(event -> {
            manualRankCreation(player, "dev", "§x§c§d§7§4§f§bDev §8• §f");
        }));

        gui.setItem(5, 9, dev.triumphteam.gui.builder.item.ItemBuilder.skull(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19")).name(Component.text("§fSkip")).asGuiItem(event -> {
            manualRankCreation(player, "not set", "not set");
        }));

        gui.open(player);
    }

    public static void manualRankCreation(Player player, String name, String prefix){
        Gui gui = Gui.gui()
                .disableAllInteractions()
                .rows(5)
                .title(Component.text("Rank Creation"))
                .create();

        ItemStack create_off = new ItemBuilder(Material.GRAY_DYE).setName(LanguageLoader.translationMap.get("rank_gui_not_ready")).build();

        ItemStack create_on = new ItemBuilder(Material.LIME_DYE).setName(LanguageLoader.translationMap.get("rank_gui_ready")).build();

        ready = !name.equals("not set") && !prefix.equals("not set");

        gui.setItem(3, 5, ready ? dev.triumphteam.gui.builder.item.ItemBuilder.from(create_on).asGuiItem(event -> {
            if (ready){
                RankAPI.createRank(player, name, ChatColor.translateAlternateColorCodes('&', " " + prefix));
                player.closeInventory();
            }
        }) : dev.triumphteam.gui.builder.item.ItemBuilder.from(create_off).asGuiItem(event -> {
            if (ready){
                RankAPI.createRank(player, name, ChatColor.translateAlternateColorCodes('&', " " + prefix));
                player.closeInventory();
            }
        }));

        gui.setItem(3, 3, dev.triumphteam.gui.builder.item.ItemBuilder.from(Material.NAME_TAG).name(Component.text("§7Name: §8" + name)).asGuiItem(event -> {
            new AnvilGUI.Builder()
                    .onComplete((completion) -> {
                        if(Utils.countWords(completion.getText()) > 1) {
                            player.playSound(player.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
                            return Collections.singletonList(AnvilGUI.ResponseAction.replaceInputText(LanguageLoader.translationMap.get("anvil_gui_one_word")));
                        }
                        manualRankCreation(player, completion.getText().replace(" ", ""), prefix);
                        return Collections.singletonList(AnvilGUI.ResponseAction.close());
                    })
                    .itemLeft(new ItemStack(Material.NAME_TAG))
                    .title("Enter the name")
                    .plugin(Main.getInstance())
                    .open(player);
        }));

        gui.setItem(3, 7, dev.triumphteam.gui.builder.item.ItemBuilder.from(Material.NAME_TAG).name(Component.text("§7Prefix: §8" + prefix)).asGuiItem(event -> {
            new AnvilGUI.Builder()
                    .onComplete((completion) -> {
                        manualRankCreation(player, name, completion.getText());
                        return Collections.singletonList(AnvilGUI.ResponseAction.close());
                    })
                    .itemLeft(new ItemStack(Material.NAME_TAG))
                    .title("Enter the prefix")
                    .plugin(Main.getInstance())
                    .open(player);
        }));

        gui.setItem(5, 1, dev.triumphteam.gui.builder.item.ItemBuilder.skull(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==")).name(Component.text("§fBack")).asGuiItem(event -> {
            templateRanks(player);
        }));

        gui.open(player);
    }

}
