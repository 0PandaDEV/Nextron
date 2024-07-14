package net.pandadev.nextron.tablist;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.SettingsAPI;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TablistManager {

    public void setAllPlayerTeams() {
        Bukkit.getOnlinePlayers().forEach(this::setPlayerTeams);
    }

    public void setPlayerTeams(Player player){
        Scoreboard scoreboard = player.getScoreboard();
        ConfigurationSection ranks = Main.getInstance().getConfig().getConfigurationSection("Ranks");

        if (ranks == null) return;

        for (String rank : ranks.getKeys(false)) {
            Team teamRank = scoreboard.getTeam(rank);
            if (teamRank == null) teamRank = scoreboard.registerNewTeam(rank);

            if (Configs.feature.getBoolean("rank_system")){
                teamRank.setPrefix(ranks.getString(rank + ".prefix"));
            } else {
                teamRank.setPrefix("");
                player.setPlayerListName(SettingsAPI.getNick(player));
                return;
            }

            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (ranks.getStringList(rank + ".players").contains(String.valueOf(player1.getUniqueId()))) {
                    String displayName = ranks.get(rank + ".prefix") + SettingsAPI.getNick(player1);

                    teamRank.addEntry(player1.getName());
                    player1.setPlayerListName(displayName);
                }
            }
        }
    }

//    public void setPlayerTeams(Player player){
//        Scoreboard scoreboard = player.getScoreboard();
//
//        ConfigurationSection ranks = Main.getInstance().getConfig().getConfigurationSection("Ranks");
//
//        for (String rank : ranks.getKeys(false)){
//            Team finalrank = scoreboard.getTeam("010" + rank.toLowerCase());
//            if (finalrank == null){
//                finalrank = scoreboard.registerNewTeam("010" + rank.toLowerCase());
//            }
//            if (Configs.feature.getBoolean("rank_system")) {
//                finalrank.setPrefix(ranks.getString(rank + ".prefix"));
//            } else {
//                finalrank.setPrefix("");
//                player.setDisplayName(player.getName());
//            }
//            for (Player target : Bukkit.getOnlinePlayers()){
//                if (ranks.getStringList(rank + ".players").contains(String.valueOf(target.getUniqueId()))) {
//                    finalrank.addEntry(target.getName());
//                }
//            }
//        }
//
//    }

}
