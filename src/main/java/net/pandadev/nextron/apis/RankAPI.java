package net.pandadev.nextron.apis;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RankAPI {
    public static void createPlayerTeam(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Team finalrank = scoreboard.getTeam("999player");
        if (finalrank == null)
            finalrank = scoreboard.registerNewTeam("999player");
        if (Configs.feature.getBoolean("rank_system")) {
            finalrank.setPrefix("§9Player §8• §7");
        } else {
            finalrank.setPrefix("");
        }
        finalrank.setColor(ChatColor.GRAY);
    }

    public static void setRank(CommandSender sender, Player player, String rank) {
        if (Main.getInstance().getConfig().get("Ranks." + rank.toLowerCase()) == null) {
            sender.sendMessage(Main.getPrefix() + Text.get("rank.dontexists"));
            return;
        }
        if (Main.getInstance().getConfig().getStringList("Ranks." + rank.toLowerCase() + ".players").contains(player)) {
            sender.sendMessage(Main.getPrefix() + Text.get("rank.set.error").replace("%p", player.getName()).replace("%r", rank.toLowerCase()));
            return;
        }
        removeRanks(player);
        List<String> list = Main.getInstance().getConfig().getStringList("Ranks." + rank.toLowerCase() + ".players");
        list.add(String.valueOf(player.getUniqueId()));
        Main.getInstance().getConfig().set("Ranks." + rank.toLowerCase() + ".players", list);
        Main.getInstance().saveConfig();
        Main.getInstance().getTablistManager().setAllPlayerTeams();
        sender.sendMessage(Main.getPrefix() + Text.get("rank.set.success").replace("%p", player.getName()).replace("%r", rank.toLowerCase()));
    }

    public static void removeRanks(Player player) {
        for (String rank : Main.getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false)) {
            List<String> list = Main.getInstance().getConfig().getStringList("Ranks." + rank.toLowerCase() + ".players");
            list.remove(String.valueOf(player.getUniqueId()));
            Main.getInstance().getConfig().set("Ranks." + rank.toLowerCase() + ".players", list);
            Main.getInstance().saveConfig();
        }
        checkRank(player);
        Main.getInstance().getTablistManager().setAllPlayerTeams();
    }

    public static void createRank(Player player, String rank, String prefix) {
        if (Main.getInstance().getConfig().get("Ranks." + rank.toLowerCase()) != null) {
            player.sendMessage(Main.getPrefix() + Text.get("rank.exists"));
            return;
        }
        List<String> list = new ArrayList<>();
        Main.getInstance().getConfig().set("Ranks." + rank.toLowerCase() + ".prefix", prefix.substring(1));
        Main.getInstance().getConfig().set("Ranks." + rank.toLowerCase() + ".players", list);
        Main.getInstance().saveConfig();
        Main.getInstance().getTablistManager().setAllPlayerTeams();
        player.sendMessage(Main.getPrefix() + Text.get("rank.create.success").replace("%r", rank));
    }

    public static void deleteRank(Player player, String rank) {
        if (Main.getInstance().getConfig().get("Ranks." + rank.toLowerCase()) == null) {
            player.sendMessage(Main.getPrefix() + Text.get("rank.dontexists"));
            return;
        }
        Main.getInstance().getConfig().set("Ranks." + rank.toLowerCase(), null);
        Main.getInstance().saveConfig();
        player.sendMessage(Main.getPrefix() + Text.get("rank.delete.success").replace("%r", rank.toLowerCase()));
        for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
            checkRank(onlineplayer);
        }
        Main.getInstance().getTablistManager().setAllPlayerTeams();
    }

    public static void setPrefix(Player player, String rank, String prefix) {
        if (Main.getInstance().getConfig().get("Ranks." + rank.toLowerCase()) == null) {
            player.sendMessage(Main.getPrefix() + Text.get("rank.dontexists"));
            return;
        }
        Main.getInstance().getConfig().set("Ranks." + rank.toLowerCase() + ".prefix", prefix.substring(1));
        Main.getInstance().saveConfig();
        Main.getInstance().getTablistManager().setAllPlayerTeams();
        player.sendMessage(Main.getPrefix() + Text.get("rank.setprefix.success").replace("%r", rank.toLowerCase()).replace("%p", prefix.substring(1)));
    }

    public static void rename(Player player, String rank, String name) {
        if (Main.getInstance().getConfig().get("Ranks." + rank.toLowerCase()) == null) {
            player.sendMessage(Main.getPrefix() + Text.get("rank.dontexists"));
            return;
        }
        Main.getInstance().getConfig().set("Ranks." + name.substring(1).toLowerCase() + ".prefix", Main.getInstance().getConfig().get("Ranks." + rank.toLowerCase() + ".prefix"));
        Main.getInstance().getConfig().set("Ranks." + name.substring(1).toLowerCase() + ".players", Main.getInstance().getConfig().get("Ranks." + rank.toLowerCase() + ".players"));
        Main.getInstance().getConfig().set("Ranks." + rank.toLowerCase(), null);
        Main.getInstance().saveConfig();
        player.sendMessage(Main.getPrefix() + Text.get("rank.rename.success").replace("%r", rank.toLowerCase()).replace("%n", name.toLowerCase().substring(1)));
        Main.getInstance().getTablistManager().setAllPlayerTeams();
    }

    public static String getRank(Player player) {
        for (String rank : Main.getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false)) {
            if (Main.getInstance().getConfig().getStringList("Ranks." + rank + ".players").contains(String.valueOf(player.getUniqueId())))
                return rank;
        }
        return "player";
    }

    public static boolean hasRank(Player player) {
        for (String rank : Main.getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false)) {
            if (Main.getInstance().getConfig().getStringList("Ranks." + rank + ".players").contains(String.valueOf(player.getUniqueId())))
                return true;
        }
        return false;
    }

    public static void checkRank(Player player) {
        createPlayerTeam(player);
        if (Main.getInstance().getConfig().getConfigurationSection("Ranks") == null || !hasRank(player)) {
            Scoreboard scoreboard = player.getScoreboard();
            Team finalrank = scoreboard.getTeam("999player");
            finalrank.addEntry(player.getName());
            if (Configs.feature.getBoolean("rank_system")) {
                String displayName = "§9Player §8• §f" + SettingsAPI.getNick(player);
                player.setDisplayName(displayName);
                player.setPlayerListName(displayName);
            } else {
                player.setDisplayName(SettingsAPI.getNick(player));
                player.setPlayerListName(SettingsAPI.getNick(player));
            }
            return;
        }
        Main.getInstance().getTablistManager().setAllPlayerTeams();
    }

    public static String getHighestNumber() {
        int highestNumber = 0;
        for (String rank : Main.getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false)) {
            String numberPart = rank.substring(0, 3);
            try {
                int number = Integer.parseInt(numberPart);
                if (number > highestNumber && number < 998) {
                    highestNumber = number;
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return String.format("%03d", Math.min(highestNumber + 1, 998));
    }


    public static void migration() {
        ConfigurationSection ranksSection = Main.getInstance().getConfig().getConfigurationSection("Ranks");
        if (ranksSection == null)
            return;

        Map<String, Object> oldRanks = new LinkedHashMap<>(ranksSection.getValues(false));
        ranksSection.getKeys(false).forEach(key -> ranksSection.set(key, null));

        boolean needsMigration = oldRanks.keySet().stream()
                .anyMatch(key -> !key.matches("^\\d{3}.*"));

        if (!needsMigration)
            return;

        int counter = 1;
        for (Map.Entry<String, Object> entry : oldRanks.entrySet()) {
            String oldName = entry.getKey();
            String newName = String.format("%03d%s", counter, oldName);

            ranksSection.set(newName, entry.getValue());
            ranksSection.set(oldName, null);
            counter++;
        }

        Main.getInstance().saveConfig();

    }

}
