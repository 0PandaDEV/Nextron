package tk.pandadev.essentialsp.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.ChatInput;
import tk.pandadev.essentialsp.utils.ItemBuilder;
import tk.pandadev.essentialsp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeGui {

    private static int count;
    private AsyncPlayerChatEvent asyncPlayerChatEvent;
    public static Inventory getInventory(Player player) throws IllegalAccessException, NoSuchFieldException {
        HashMap<Integer, ItemStack> integerItemStackHashMap = new HashMap<>();
        Inventory inventory = Bukkit.createInventory(null, 5*9, "Homes");

        count = 0;
        for (String home : Main.getInstance().getConfig().getConfigurationSection("Homes." + player.getUniqueId()).getKeys(false)){
            ItemStack homeitem = new ItemStack(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNkMDJjZGMwNzViYjFjYzVmNmZlM2M3NzExYWU0OTc3ZTM4YjkxMGQ1MGVkNjAyM2RmNzM5MTNlNWU3ZmNmZiJ9fX0="));
            ItemMeta homeitemmeta = homeitem.getItemMeta();
            homeitemmeta.setDisplayName("§f" + home);
            homeitemmeta.setLocalizedName(home);
            ArrayList<String> homeitemlore = new ArrayList<String>();
            homeitemlore.add("");
            homeitemlore.add("§aLinksklick §c>> §7Öffnet Home Einstellungen");
            homeitemlore.add("§aRechtsklick §c>> §7Teleportiert zum Home");
            homeitemmeta.setLore(homeitemlore);
            homeitem.setItemMeta(homeitemmeta);
            inventory.setItem(count, homeitem);
            count++;
        }

        ItemStack arrow_left = new ItemStack(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ=="));
        ItemMeta arrow_meta = arrow_left.getItemMeta();
        arrow_meta.setDisplayName("§fBack");
        arrow_meta.setLocalizedName("back_to_main");
        arrow_left.setItemMeta(arrow_meta);
        inventory.setItem(36, arrow_left);

        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
            inventory.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
        }
        return inventory;
    }

    public static Inventory getHomeInventory(Player player, String home) throws IllegalAccessException, NoSuchFieldException {
        HashMap<Integer, ItemStack> integerItemStackHashMap = new HashMap<>();
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, home);

        /////////////////////////////////////////////////////////////////////////////////////////////

        inventory.setItem(12, new ItemBuilder(Material.ENDER_PEARL).setDisplayname("§x§0§1§5§9§5§6Teleportiert zum Home").setLocalizedName("enderpearl").build());
        inventory.setItem(14, new ItemBuilder(Material.RED_DYE).setDisplayname("§cDelete").setLocalizedName("delete").build());
        inventory.setItem(13, new ItemBuilder(Material.YELLOW_DYE).setDisplayname("§eRename").setLocalizedName("rename").build());

        ItemStack info = new ItemStack(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjNkMDJjZGMwNzViYjFjYzVmNmZlM2M3NzExYWU0OTc3ZTM4YjkxMGQ1MGVkNjAyM2RmNzM5MTNlNWU3ZmNmZiJ9fX0="));
        ItemMeta infometa = info.getItemMeta();
        infometa.setDisplayName("§f" + home);
        ArrayList<String> infolore = new ArrayList<String>();
        infolore.add("");
        infolore.add("§8World: " + Main.getInstance().getConfig().get("Homes." + player.getUniqueId() + "." + home + ".world"));
        infolore.add("§8X: " + Main.getInstance().getConfig().get("Homes." + player.getUniqueId() + "." + home + ".x"));
        infolore.add("§8Y: " + Main.getInstance().getConfig().get("Homes." + player.getUniqueId() + "." + home + ".y"));
        infolore.add("§8Z: " + Main.getInstance().getConfig().get("Homes." + player.getUniqueId() + "." + home + ".z"));
        infometa.setLore(infolore);
        info.setItemMeta(infometa);
        inventory.setItem(16, info);

        /////////////////////////////////////////////////////////////////////////////////////////////

        ItemStack arrow_left = new ItemStack(Utils.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ=="));
        ItemMeta arrow_meta = arrow_left.getItemMeta();
        arrow_meta.setDisplayName("§fBack");
        arrow_meta.setLocalizedName("back_to_home");
        arrow_left.setItemMeta(arrow_meta);
        inventory.setItem(18, arrow_left);

        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : integerItemStackHashMap.entrySet()) {
            inventory.setItem(integerItemStackEntry.getKey(), integerItemStackEntry.getValue());
        }
        return inventory;
    }

}
