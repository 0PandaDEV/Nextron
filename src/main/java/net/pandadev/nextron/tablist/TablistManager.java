package net.pandadev.nextron.tablist;

import net.pandadev.nextron.apis.RankAPI;
import net.pandadev.nextron.apis.SettingsAPI;
import net.pandadev.nextron.utils.Configs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TablistManager {

    public void setAllPlayerTeams() {
        Bukkit.getOnlinePlayers().forEach(this::setPlayerTeams);
    }

    public void setPlayerTeams(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        System.out.println("setteams");

        for (String rank : RankAPI.getRanks()) {
            Team teamRank = scoreboard.getTeam(rank);
            if (teamRank == null) teamRank = scoreboard.registerNewTeam(rank);

            if (Configs.feature.getBoolean("rank_system")) {
                teamRank.setPrefix(RankAPI.getRankPrefix(rank));
            } else {
                teamRank.setPrefix("");
                player.setPlayerListName(SettingsAPI.getNick(player));
                return;
            }

            if (RankAPI.getRank(player).equalsIgnoreCase(rank)) {
                String displayName = RankAPI.getRankPrefix(rank) + SettingsAPI.getNick(player);

                teamRank.addEntry(player.getName());
                player.setPlayerListName(displayName);
            }
        }
    }
}
