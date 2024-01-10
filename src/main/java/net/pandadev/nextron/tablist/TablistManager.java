package net.pandadev.nextron.tablist;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;

public class TablistManager {

    public void setAllPlayerTeams() {
        Bukkit.getOnlinePlayers().forEach(this::setPlayerTeams);
    }

    public void setPlayerTeams(Player player){
        Scoreboard scoreboard = player.getScoreboard();
        ConfigurationSection ranks = Main.getInstance().getConfig().getConfigurationSection("Ranks");

        if (ranks == null) return;

        for (String rank : ranks.getKeys(false)) {
            Team teamRank = scoreboard.getTeam("010" + rank);
            if (teamRank == null) teamRank = scoreboard.registerNewTeam("010" + rank);

            if (Configs.feature.getBoolean("rank_system")){
                teamRank.setPrefix(ranks.getString(rank + ".prefix"));
            } else {
                teamRank.setPrefix("");
                player.setPlayerListName(Configs.settings.getString(player.getUniqueId() + ".nick"));
                return;
            }

            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (ranks.getStringList(rank + ".players").contains(String.valueOf(player1.getUniqueId()))) {
                    String displayName = ranks.get(rank + ".prefix") + Configs.settings.getString(player1.getUniqueId() + ".nick");

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
