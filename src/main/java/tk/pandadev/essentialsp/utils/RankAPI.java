package tk.pandadev.essentialsp.utils;

import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RankAPI {

    public static void setRank(Player player, String rank){
        if (Main.getInstance().getConfig().get("ranks." + rank) != null){
            if (!Main.getInstance().getConfig().getStringList("ranks." + rank.toLowerCase() + ".players").contains(player.getName())){
                removeRanks(player);
                List<String> list = Main.getInstance().getConfig().getStringList("ranks." + rank.toLowerCase() + ".players");
                list.add(String.valueOf(player.getUniqueId()));
                Main.getInstance().getConfig().set("ranks." + rank.toLowerCase() + ".players", list);
                Main.getInstance().saveConfig();
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_set_success").replace("%p", player.getName()).replace("%r", rank.toLowerCase()));
            }else {
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_set_error").replace("%p", player.getName()).replace("%r", rank.toLowerCase()));
            }
        } else {
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_dontexists"));
        }
    }

    public static void removeRanks(Player player){
        for (String rank : Main.getInstance().getConfig().getConfigurationSection("ranks").getKeys(false)){
            List<String> list = Main.getInstance().getConfig().getStringList("ranks." + rank.toLowerCase() + ".players");
            list.remove(String.valueOf(player.getUniqueId()));
            Main.getInstance().getConfig().set("ranks." + rank.toLowerCase() + ".players", list);
            Main.getInstance().saveConfig();
        }
    }

    public static void createRank(Player player, String name, String prefix){
        for (String rank : Main.getInstance().getConfig().getConfigurationSection("ranks").getKeys(false)){
            if (!rank.equalsIgnoreCase(name)){
                List<String> list = new ArrayList<>();
                Main.getInstance().getConfig().set("ranks." + name + ".prefix", prefix.substring(1));
                Main.getInstance().getConfig().set("ranks." + name + ".players", list);
                Main.getInstance().saveConfig();
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_create_success").replace("%r", name));
            }else {
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_exists"));
            }
        }
    }

    public static void deleteRank(Player player, String name){
        if (Main.getInstance().getConfig().get("ranks." + name) != null){
            Main.getInstance().getConfig().set("ranks." + name, null);
            Main.getInstance().saveConfig();
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_delete_success").replace("%r", name));
        } else {
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_dontexists"));
        }
    }

    public static void setPrefix(Player player, String name, String prefix){
        if (Main.getInstance().getConfig().get("ranks." + name) != null){
            Main.getInstance().getConfig().set("ranks." + name + ".prefix", prefix.substring(1));
            Main.getInstance().saveConfig();
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_setprefix_success").replace("%r", name).replace("%p", player.getName()));
        } else {
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_dontexists"));
        }
    }

    public static void setName(Player player, String rankname, String name){

        for (String rank : Main.getInstance().getConfig().getConfigurationSection("ranks").getKeys(false)){
            Main.getInstance().getConfig().set(rank, rankname);
        }

        //if (Main.getInstance().getConfig().get("ranks." + rankname) != null){
        //    Main.getInstance().getConfig().set("ranks." + rankname, name.substring(1));
        //    Main.getInstance().saveConfig();
        //    player.sendMessage(Main.getPrefix() + "§7Der Name des Ranks §a" + rankname + "§7 wurde auf §a" + name.substring(1) + "§7 geändert");
        //} else {
        //    player.sendMessage(Main.getPrefix() + "§cDieser Rank existiert nicht");
        //}
    }

    public static String getRank(Player player){
        for (String rank : Main.getInstance().getConfig().getConfigurationSection("ranks").getKeys(false)){
            if (Main.getInstance().getConfig().getStringList("ranks." + rank + ".players").contains(String.valueOf(player.getUniqueId()))){
                return rank;
            }
        }
        return null;
    }

    public static void checkRank(Player player){
        if (getRank(player) == null){
            List<String> list = Main.getInstance().getConfig().getStringList("ranks.spieler.players");
            list.add(String.valueOf(player.getUniqueId()));
            Main.getInstance().getConfig().set("ranks.spieler.players", list);
            Main.getInstance().saveConfig();
        }
    }

    public static String getFormatedRank(Player player){
        for (String rank : Main.getInstance().getConfig().getConfigurationSection("ranks").getKeys(false)){
            if (Main.getInstance().getConfig().getStringList("ranks." + rank + ".players").contains(String.valueOf(player.getUniqueId()))){
                return Main.getInstance().getConfig().getString("ranks." + rank + ".prefix").substring(0, 7);
            }
        }
        return null;
    }



}
