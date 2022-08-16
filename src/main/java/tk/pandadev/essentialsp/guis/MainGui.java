package tk.pandadev.essentialsp.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.ItemBuilder;
import tk.pandadev.essentialsp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainGui {
    public static Inventory getInventory(Player player) throws IllegalAccessException, NoSuchFieldException {
        HashMap<Integer, ItemStack> integerItemStackHashMap = new HashMap<>();

        //integerItemStackHashMap.put(22, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§c§l" + player.getDisplayName()).setLocalizedName("player").build());

        Inventory inventory = Bukkit.createInventory(null, 5*9, "Settings");

        //ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        //SkullMeta skull = (SkullMeta) item.getItemMeta();
        //skull.setDisplayName(player.getName());
        //ArrayList<String> lore = new ArrayList<String>();
        //lore.add("Player Settings");
        //skull.setLore(lore);
        //skull.setLocalizedName("player");
        //skull.setOwner(player.getName());
        //item.setItemMeta(skull);
        //inventory.setItem(16, item);

        //inventory.setItem(14, new ItemBuilder(Material.RED_DYE).setDisplayname("§c§lVanish Settings").setLocalizedName("vanish settings").build());
        //inventory.setItem(13, new ItemBuilder(Material.NETHER_STAR).setDisplayname("§f§lCommand Feedback").setLocalizedName("command feedback").build());

        //inventory.setItem(22, Utils.getSkull("https://textures.minecraft.net/texture/86971dd881dbaf4fd6bcaa93614493c612f869641ed59d1c9363a3666a5fa6", player.getName(), "player"));

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".feedback")){
            ItemStack feedback = new ItemStack(Material.LIME_DYE);
            ItemMeta feedback_meta = feedback.getItemMeta();
            feedback_meta.setDisplayName("§a✔ §8| §aCommand Feedback ist aktiviert");
            feedback_meta.setLocalizedName("feedback toggler");
            ArrayList<String> feedback_lore = new ArrayList<String>();
            feedback_lore.add("");
            feedback_lore.add("§8Steuert ob eine Nachricht als ergebnis");
            feedback_lore.add("§8nach einem Command angezeigt werden soll");
            feedback_lore.add("§8z.b. 'Du wurdest in den Gamemode Creative gesetzt'");
            feedback_lore.add("§8Fehlermeldungen werden dennoch angezeigt");
            feedback_lore.add("§8sowie wichtige nachrichten");
            feedback_lore.add("");
            feedback_lore.add("§aLinksklick §c>> §7Toggle");
            feedback_meta.setLore(feedback_lore);
            feedback.setItemMeta(feedback_meta);
            inventory.setItem(32, feedback);
        } else {
            ItemStack feedback = new ItemStack(Material.GRAY_DYE);
            ItemMeta feedback_meta = feedback.getItemMeta();
            feedback_meta.setDisplayName("§c❌ §8| §7Command Feedback ist deaktiviert");
            feedback_meta.setLocalizedName("feedback toggler");
            ArrayList<String> feedback_lore = new ArrayList<String>();
            feedback_lore.add("");
            feedback_lore.add("§8Steuert ob eine Nachricht als ergebnis");
            feedback_lore.add("§8nach einem Command angezeigt werden soll");
            feedback_lore.add("§8z.b. 'Du wurdest in den Gamemode Creative gesetzt'");
            feedback_lore.add("§8Fehlermeldungen werden dennoch angezeigt");
            feedback_lore.add("§8sowie wichtige nachrichten");
            feedback_lore.add("");
            feedback_lore.add("§aLinksklick §c>> §7Toggle");
            feedback_meta.setLore(feedback_lore);
            feedback.setItemMeta(feedback_meta);
            inventory.setItem(32, feedback);
        }

        inventory.setItem(14, new ItemBuilder(Material.NETHERITE_INGOT).setDisplayname("§8Command Feedback Settings").setLocalizedName("feedback settings").build());

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".vanish." + "message")){
            ItemStack vanish = new ItemStack(Material.LIME_DYE);
            ItemMeta vanish_meta = vanish.getItemMeta();
            vanish_meta.setDisplayName("§a✔ §8| §aFake Join/Quit Message aktiviert");
            vanish_meta.setLocalizedName("vanish message toggler");
            ArrayList<String> vanish_lore = new ArrayList<String>();
            vanish_lore.add("");
            vanish_lore.add("§8Steuert ob eine Fake Join oder Quit");
            vanish_lore.add("§8Nachricht angezeigt werden soll");
            vanish_lore.add("§8wen du in den Vanish gehst oder ihn verlässt");
            vanish_lore.add("");
            vanish_lore.add("§aLinksklick §c>> §7Toggle");
            vanish_meta.setLore(vanish_lore);
            vanish.setItemMeta(vanish_meta);
            inventory.setItem(30, vanish);
        } else {
            ItemStack vanish = new ItemStack(Material.GRAY_DYE);
            ItemMeta vanish_meta = vanish.getItemMeta();
            vanish_meta.setDisplayName("§c❌ §8| §7Fake Join/Quit Message deaktiviert");
            vanish_meta.setLocalizedName("vanish message toggler");
            ArrayList<String> vanish_lore = new ArrayList<String>();
            vanish_lore.add("");
            vanish_lore.add("§8Steuert ob eine Fake Join oder Quit");
            vanish_lore.add("§8Nachricht angezeigt werden soll");
            vanish_lore.add("§8wen du in den Vanish gehst oder ihn verlässt");
            vanish_lore.add("");
            vanish_lore.add("§aLinksklick §c>> §7Toggle");
            vanish_meta.setLore(vanish_lore);
            vanish.setItemMeta(vanish_meta);
            inventory.setItem(30, vanish);
        }

        inventory.setItem(12, new ItemBuilder(Material.HEART_OF_THE_SEA).setDisplayname("§3Vanish Settings").setLocalizedName("vanish settings").build());

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        inventory.setItem(21, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayname(" ").build());
        inventory.setItem(23, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayname(" ").build());

        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
            inventory.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
        }
        return inventory;
    }
}
