package tk.pandadev.essentialsp.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.pandadev.essentialsp.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class FeedbackGui {

    public static Inventory getInventory(Player player) throws IllegalAccessException, NoSuchFieldException {
        HashMap<Integer, ItemStack> integerItemStackHashMap = new HashMap<>();

        Inventory inventory = Bukkit.createInventory(null, 27, "Command Feedback Settings");



        ItemStack arrow_left = new ItemStack(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ=="));
        ItemMeta arrow_meta = arrow_left.getItemMeta();
        arrow_meta.setDisplayName("§fBack");
        arrow_meta.setLocalizedName("back_to_main");
        arrow_left.setItemMeta(arrow_meta);
        inventory.setItem(18, arrow_left);

        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
            inventory.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
        }
        return inventory;
    }

}
