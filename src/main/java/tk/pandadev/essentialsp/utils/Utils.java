package tk.pandadev.essentialsp.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class Utils {

    public static ItemStack getSkull(String url, String displayName, String localizedName) throws IllegalAccessException, NoSuchFieldException {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);

        if (url == null || url.isEmpty())
            return skull;

        ItemMeta skullMeta = skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:" + url + "}}}").getBytes());
        profile.getProperties().put("textures", new Property("textures", String.valueOf(encodedData), url));

        Field profileField = null;

        profileField = skullMeta.getClass().getDeclaredField("profile");

        profileField.setAccessible(true);

        profileField.set(skullMeta, profile);

        skullMeta.setDisplayName(displayName);
        skullMeta.setLocalizedName(localizedName);
        skull.setItemMeta(skullMeta);
        return skull;
    }

    public static ItemStack createSkull(String url) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if (url.isEmpty())
            return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

}
