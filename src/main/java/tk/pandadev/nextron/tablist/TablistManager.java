package tk.pandadev.nextron.tablist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.Configs;

public class TablistManager {

    public void setAllPlayerTeams() {
        Bukkit.getOnlinePlayers().forEach(this::setPlayerTeams);
    }

    public void setPlayerTeams(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        ConfigurationSection ranks = Main.getInstance().getConfig().getConfigurationSection("Ranks");

        if (ranks == null) return;

        for (String rank : ranks.getKeys(false)) {
            if (!Configs.feature.getBoolean("rank_system")) return;

            Team playerRank = scoreboard.getTeam("010" + rank.toLowerCase());
            if (playerRank == null) playerRank = scoreboard.registerNewTeam("010" + rank.toLowerCase());

            playerRank.setColor(ChatColor.GRAY);
            playerRank.setPrefix(ranks.getString(rank + ".prefix"));

            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (ranks.getStringList(rank + ".players").contains(String.valueOf(player1.getUniqueId()))) {
                    String displayName = Main.getInstance().getConfig().get("Ranks." + rank + ".prefix") + Configs.settings.getString(player1.getUniqueId() + ".nick");

                    playerRank.addEntry(player1.getName());
                    player1.setDisplayName(displayName); // Set the player's display name to include rank and nickname
                    player1.setPlayerListName(displayName); // Set the player's name in the tablist to include rank and nickname

                    System.out.println(playerRank.getEntries());
                }
            }
        }
    }

}
