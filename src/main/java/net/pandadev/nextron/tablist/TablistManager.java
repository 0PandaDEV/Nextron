package net.pandadev.nextron.tablist;

import net.pandadev.nextron.apis.FeatureAPI;
import net.pandadev.nextron.apis.RankAPI;
import net.pandadev.nextron.apis.SettingsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class TablistManager {

    // TODO: Packet ranks

    public void setAllPlayerTeams() {
        Bukkit.getOnlinePlayers().forEach(this::setPlayerTeams);
    }
    
    public void setPlayerTeams(Player player) {
        Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();

        for (String rank : RankAPI.getRanks()) {
            Team teamRank = scoreboard.getTeam(rank);
            if (teamRank == null) teamRank = scoreboard.registerNewTeam(rank);

            if (FeatureAPI.getFeature("rank_system")) {
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
