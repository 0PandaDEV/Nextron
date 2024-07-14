package net.pandadev.nextron.apis;

import ch.hekates.languify.language.Text;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.config.Config;
import net.pandadev.nextron.utils.Configs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RankAPI {
    private static final Logger LOGGER = Logger.getLogger(RankAPI.class.getName());

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
        List<String> ranks = getRanks();
        if (!ranks.contains(rank.toLowerCase())) {
            sender.sendMessage(Main.getPrefix() + Text.get("rank.dontexists"));
            return;
        }

        String updateSql = "UPDATE ranks SET uuids = ? WHERE name = ?";

        try {
            removeRanks(player);

            PreparedStatement ps = Config.getConnection().prepareStatement(updateSql);

            String selectSql = "SELECT uuids FROM ranks WHERE name = ?";
            PreparedStatement selectPs = Config.getConnection().prepareStatement(selectSql);
            selectPs.setString(1, rank.toLowerCase());
            ResultSet rs = selectPs.executeQuery();

            ArrayList<String> uuids;
            if (rs.next()) {
                String existingUuids = rs.getString("uuids");
                uuids = existingUuids.isEmpty() ? new ArrayList<>() : new Gson().fromJson(existingUuids, new TypeToken<ArrayList<String>>() {
                }.getType());
            } else {
                uuids = new ArrayList<>();
            }

            uuids.add(player.getUniqueId().toString());

            ps.setString(1, new Gson().toJson(uuids));
            ps.setString(2, rank.toLowerCase());
            ps.executeUpdate();

            Main.getInstance().getTablistManager().setAllPlayerTeams();
            sender.sendMessage(Main.getPrefix() + Text.get("rank.set.success").replace("%p", player.getName()).replace("%r", rank));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error setting rank for player: " + player.getName(), e);
        }
    }

    public static void removeRanks(Player player) {
        String sql = "SELECT name, uuids FROM ranks";
        try {
            Config.executeQuery(sql, (ResultSet rs) -> {
                try {
                    while (rs.next()) {
                        String name = rs.getString("name");
                        String uuidsString = rs.getString("uuids");
                        if (!uuidsString.isEmpty()) {
                            ArrayList<String> uuids = new Gson().fromJson(uuidsString, new TypeToken<ArrayList<String>>() {
                            }.getType());
                            if (uuids.remove(player.getUniqueId().toString())) {
                                String updateSql = "UPDATE ranks SET uuids = ? WHERE name = ?";
                                PreparedStatement ps = Config.getConnection().prepareStatement(updateSql);
                                ps.setString(1, new Gson().toJson(uuids));
                                ps.setString(2, name);
                                ps.executeUpdate();
                            }
                        }
                    }
                    checkRank(player);
                    Main.getInstance().getTablistManager().setAllPlayerTeams();
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Error removing ranks for player: " + player.getName(), e);
                }
            });
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error removing ranks for player: " + player.getName(), e);
        }
    }

    public static void createRank(Player player, String rank, String prefix) {
        String checkSql = "SELECT COUNT(*) FROM ranks WHERE name = ?";
        String insertSql = "INSERT INTO ranks (name, prefix, uuids) VALUES (?, ?, '')";

        try {
            Config.executeQuery(checkSql, (ResultSet rs) -> {
                try {
                    if (rs.next() && rs.getInt(1) > 0) {
                        player.sendMessage(Main.getPrefix() + Text.get("rank.exists"));
                        return;
                    }

                    try {
                        PreparedStatement ps = Config.getConnection().prepareStatement(insertSql);
                        ps.setString(1, rank.toLowerCase());
                        ps.setString(2, prefix.substring(1));
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        LOGGER.log(Level.SEVERE, "Error creating rank: " + rank, e);
                    }

                    Main.getInstance().getTablistManager().setAllPlayerTeams();
                    player.sendMessage(Main.getPrefix() + Text.get("rank.create.success").replace("%r", rank));
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Error creating rank: " + rank, e);
                }
            });

            PreparedStatement ps = Config.getConnection().prepareStatement(checkSql);
            ps.setString(1, rank.toLowerCase());
            ps.executeQuery();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating rank: " + rank, e);
        }
    }

    public static void deleteRank(Player player, String rank) {
        String sql = "DELETE FROM ranks WHERE name = ?";
        try {
            PreparedStatement ps = Config.getConnection().prepareStatement(sql);
            ps.setString(1, rank.toLowerCase());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                player.sendMessage(Main.getPrefix() + Text.get("rank.dontexists"));
                return;
            }
            player.sendMessage(Main.getPrefix() + Text.get("rank.delete.success").replace("%r", rank.toLowerCase()));
            for (Player onlineplayer : Bukkit.getOnlinePlayers()) {
                checkRank(onlineplayer);
            }
            Main.getInstance().getTablistManager().setAllPlayerTeams();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting rank: " + rank, e);
        }
    }

    public static void setPrefix(Player player, String rank, String prefix) {
        String sql = "UPDATE ranks SET prefix = ? WHERE name = ?";
        try {
            PreparedStatement ps = Config.getConnection().prepareStatement(sql);
            ps.setString(1, prefix.substring(1));
            ps.setString(2, rank.toLowerCase());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                player.sendMessage(Main.getPrefix() + Text.get("rank.dontexists"));
                return;
            }
            Main.getInstance().getTablistManager().setAllPlayerTeams();
            player.sendMessage(Main.getPrefix() + Text.get("rank.setprefix.success").replace("%r", rank.toLowerCase()).replace("%p", prefix.substring(1)));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error setting prefix for rank: " + rank, e);
        }
    }

    public static void rename(Player player, String oldRank, String newRank) {
        String sql = "UPDATE ranks SET name = ? WHERE name = ?";
        try {
            PreparedStatement ps = Config.getConnection().prepareStatement(sql);
            ps.setString(1, newRank.substring(1).toLowerCase());
            ps.setString(2, oldRank.toLowerCase());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                player.sendMessage(Main.getPrefix() + Text.get("rank.dontexists"));
                return;
            }
            player.sendMessage(Main.getPrefix() + Text.get("rank.rename.success").replace("%r", oldRank.toLowerCase()).replace("%n", newRank.toLowerCase().substring(1)));
            Main.getInstance().getTablistManager().setAllPlayerTeams();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error renaming rank: " + oldRank + " to " + newRank, e);
        }
    }

    public static String getRank(Player player) {
        String sql = "SELECT name, uuids FROM ranks";
        try {
            final String[] rank = {"player"};
            Config.executeQuery(sql, (ResultSet rs) -> {
                try {
                    while (rs.next()) {
                        String uuidsString = rs.getString("uuids");
                        if (!uuidsString.isEmpty()) {
                            ArrayList<String> uuids = new Gson().fromJson(uuidsString, new TypeToken<ArrayList<String>>() {
                            }.getType());
                            if (uuids.contains(player.getUniqueId().toString())) {
                                rank[0] = rs.getString("name");
                                break;
                            }
                        }
                    }
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Error getting rank for player: " + player.getName(), e);
                }
            });
            return rank[0];
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting rank for player: " + player.getName(), e);
        }
        return "player";
    }

    public static boolean hasRank(Player player) {
        String sql = "SELECT uuids FROM ranks";
        try {
            final boolean[] hasRank = {false};
            Config.executeQuery(sql, (ResultSet rs) -> {
                try {
                    while (rs.next()) {
                        String uuidsString = rs.getString("uuids");
                        if (!uuidsString.isEmpty()) {
                            ArrayList<String> uuids = new Gson().fromJson(uuidsString, new TypeToken<ArrayList<String>>() {
                            }.getType());
                            if (uuids.contains(player.getUniqueId().toString())) {
                                hasRank[0] = true;
                                break;
                            }
                        }
                    }
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Error checking if player has rank: " + player.getName(), e);
                }
            });
            return hasRank[0];
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking if player has rank: " + player.getName(), e);
        }
        return false;
    }

    public static void checkRank(Player player) {
        createPlayerTeam(player);
        if (!hasRank(player)) {
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
    }

    public static List<String> getRanks() {
        String sql = "SELECT name FROM ranks ORDER BY name";
        List<String> ranks = new ArrayList<>();
        try {
            Config.executeQuery(sql, rs -> {
                while (rs.next()) {
                    ranks.add(rs.getString("name"));
                }
            });
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting ranks", e);
        }
        return ranks;
    }

    public static String getRankPrefix(String rank) {
        String sql = "SELECT prefix FROM ranks WHERE LOWER(name) = LOWER(?)";
        try (PreparedStatement ps = Config.getConnection().prepareStatement(sql)) {
            ps.setString(1, rank);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String prefix = rs.getString("prefix");
                    return prefix != null ? prefix : "";
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting rank prefix for rank: " + rank, e);
        }
        return "";
    }

    public static String getHighestNumber() {
        String sql = "SELECT MAX(CAST(SUBSTR(name, 1, 3) AS INTEGER)) AS max_num FROM ranks WHERE SUBSTR(name, 1, 3) GLOB '[0-9][0-9][0-9]' AND CAST(SUBSTR(name, 1, 3) AS INTEGER) < 998";
        try {
            final int[] highestNumber = {0};
            Config.executeQuery(sql, (ResultSet rs) -> {
                try {
                    if (rs.next()) {
                        highestNumber[0] = rs.getInt("max_num");
                    }
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Error getting highest rank number", e);
                }
            });
            return String.format("%03d", Math.min(highestNumber[0] + 1, 998));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting highest rank number", e);
        }
        return "001";
    }

    public static void migration() {
        String checkSql = "SELECT COUNT(*) FROM ranks WHERE SUBSTR(name, 1, 3) NOT GLOB '[0-9][0-9][0-9]'";
        String selectSql = "SELECT name, prefix, uuids FROM ranks ORDER BY name";
        String deleteSql = "DELETE FROM ranks";
        String insertSql = "INSERT INTO ranks (name, prefix, uuids) VALUES (?, ?, ?)";

        try {
            Config.executeQuery(checkSql, (ResultSet rs) -> {
                try {
                    if (rs.next() && rs.getInt(1) == 0) {
                        return; // No migration needed
                    }

                    List<String[]> ranks = new ArrayList<>();
                    Config.executeQuery(selectSql, (ResultSet rs2) -> {
                        try {
                            while (rs2.next()) {
                                ranks.add(new String[]{rs2.getString("name"), rs2.getString("prefix"), rs2.getString("uuids")});
                            }
                        } catch (SQLException e) {
                            LOGGER.log(Level.SEVERE, "Error during rank migration", e);
                        }
                    });

                    Config.executeUpdate(deleteSql);

                    int counter = 1;
                    for (String[] rank : ranks) {
                        String newName = String.format("%03d%s", counter, rank[0]);
                        try {
                            PreparedStatement ps = Config.getConnection().prepareStatement(insertSql);
                            ps.setString(1, newName);
                            ps.setString(2, rank[1]);
                            ps.setString(3, rank[2]);
                            ps.executeUpdate();
                        } catch (SQLException e) {
                            LOGGER.log(Level.SEVERE, "Error inserting migrated rank: " + newName, e);
                        }
                        counter++;
                    }
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Error during rank migration", e);
                }
            });
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error during rank migration", e);
        }
    }
}