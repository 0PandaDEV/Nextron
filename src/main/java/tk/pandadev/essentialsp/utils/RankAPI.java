package tk.pandadev.essentialsp.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import tk.pandadev.essentialsp.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RankAPI {
    private static FileConfiguration mainConfig = Main.getInstance().getConfig();

    //////////////////////////////////////////////

    public static void createPlayerTeam(Player player){
        Scoreboard scoreboard = player.getScoreboard();
        Team finalrank = scoreboard.getTeam("010player");
        if (finalrank == null) finalrank = scoreboard.registerNewTeam("010player");
        finalrank.setPrefix("§6Player §8• §7");
        finalrank.setColor(ChatColor.GRAY);
    }

    //////////////////////////////////////////////

    public static void setRank(Player player, String rank){
        if (mainConfig.get("Ranks." + rank.toLowerCase()) == null) player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_dontexists"));
        if (mainConfig.getStringList("Ranks." + rank.toLowerCase() + ".players").contains(player)) player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_set_error").replace("%p", player.getName()).replace("%r", rank.toLowerCase()));
        removeRanks(player);
        List<String> list = mainConfig.getStringList("Ranks." + rank.toLowerCase() + ".players");
        list.add(String.valueOf(player.getUniqueId()));
        mainConfig.set("Ranks." + rank.toLowerCase() + ".players", list);
        Main.getInstance().saveConfig();
        Main.getInstance().getTablistManager().setAllPlayerTeams();
        player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_set_success").replace("%p", player.getName()).replace("%r", rank.toLowerCase()));
    }

    public static void removeRanks(Player player){
        for (String rank : mainConfig.getConfigurationSection("Ranks").getKeys(false)){
            List<String> list = mainConfig.getStringList("Ranks." + rank.toLowerCase() + ".players");
            list.remove(String.valueOf(player.getUniqueId()));
            mainConfig.set("Ranks." + rank.toLowerCase() + ".players", list);
            Main.getInstance().saveConfig();
        }
        checkRank(player);
        Main.getInstance().getTablistManager().setAllPlayerTeams();
    }

    public static void createRank(Player player, String rank, String prefix){
        if (mainConfig.get("Ranks." + rank.toLowerCase()) != null) player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_exists"));
        List<String> list = new ArrayList<>();
        mainConfig.set("Ranks." + rank.toLowerCase() + ".prefix", prefix.substring(1));
        mainConfig.set("Ranks." + rank.toLowerCase() + ".players", list);
        Main.getInstance().saveConfig();
        Main.getInstance().getTablistManager().setAllPlayerTeams();
        player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_create_success").replace("%r", rank));
    }

    public static void deleteRank(Player player, String rank){
        if (mainConfig.get("Ranks." + rank.toLowerCase()) == null) player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_dontexists"));
        mainConfig.set("Ranks." + rank.toLowerCase(), null);
        Main.getInstance().saveConfig();
        player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_delete_success").replace("%r", rank.toLowerCase()));
        for (Player onlineplayer : Bukkit.getOnlinePlayers()){
            checkRank(onlineplayer);
        }
    }

    public static void setPrefix(Player player, String rank, String prefix){
        if (mainConfig.get("Ranks." + rank.toLowerCase()) == null) player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_dontexists"));
        mainConfig.set("Ranks." + rank.toLowerCase() + ".prefix", prefix.substring(1));
        Main.getInstance().saveConfig();
        Main.getInstance().getTablistManager().setAllPlayerTeams();
        player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("rank_setprefix_success").replace("%r", rank.toLowerCase()).replace("%p", prefix.substring(1)));
    }

    public static String getRank(Player player){
        for (String rank : mainConfig.getConfigurationSection("Ranks").getKeys(false)){
            if (mainConfig.getStringList("Ranks." + rank + ".players").contains(String.valueOf(player.getUniqueId()))) return rank;
        }
        return "player";
    }

    public static void checkRank(Player player){
        if (Objects.equals(getRank(player), "player")){
            Scoreboard scoreboard = player.getScoreboard();
            Team finalrank = scoreboard.getTeam("010player");
            finalrank.addEntry(player.getName());
        }
    }

}
