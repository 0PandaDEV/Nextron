package tk.pandadev.essentialsp.tablist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import tk.pandadev.essentialsp.Main;

import java.util.Objects;

public class TablistManager {

    public void setAllPlayerTeams(){
        Bukkit.getOnlinePlayers().forEach(this::setPlayerTeams);
    }

    public void setPlayerTeams(Player player){
        Scoreboard scoreboard = player.getScoreboard();

        ConfigurationSection ranks = Main.getInstance().getConfig().getConfigurationSection("Ranks");

        for (String rank : ranks.getKeys(false)){
            Team finalrank = scoreboard.getTeam("010" + rank.toLowerCase());
            if (finalrank == null){
                finalrank = scoreboard.registerNewTeam("010" + rank.toLowerCase());
            }
            finalrank.setPrefix(ranks.getString(rank + ".prefix"));
            finalrank.setColor(ChatColor.GRAY);
            for (Player target : Bukkit.getOnlinePlayers()){
                if (ranks.getStringList(rank + ".players").contains(String.valueOf(target.getUniqueId()))) {
                    finalrank.addEntry(target.getName());
                }
            }
        }

    }

}
