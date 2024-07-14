package net.pandadev.nextron.config;

import lombok.Getter;
import net.pandadev.nextron.Main;
import org.bukkit.Bukkit;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Migrations {

    private static final Pattern MIGRATION_FILE_PATTERN = Pattern.compile("V(\\d+)__.*\\.sql");

    public static void checkAndApplyMigrations() {
        if (!isDatabaseFileExists()) {
            Bukkit.getLogger().info("Database file does not exist. Creating a new one.");
        }
        copyDefaultMigrations();
        createMigrationsTable();
        applyMigrations();
    }

    private static boolean isDatabaseFileExists() {
        File dbFile = new File("plugins/Nextron/data.db");
        return dbFile.exists();
    }

    private static void createMigrationsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS migrations (id INTEGER PRIMARY KEY, version INTEGER NOT NULL)";
        try (Connection conn = Config.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to create migrations table", e);
        }
    }

    private static void applyMigrations() {
        int currentVersion = getCurrentVersion();
        List<MigrationFile> migrationFiles = getMigrationFiles();

        for (MigrationFile file : migrationFiles) {
            if (file.getVersion() > currentVersion) {
                applyMigration(file);
                updateMigrationVersion(file.getVersion());
            }
        }
    }

    private static int getCurrentVersion() {
        String sql = "SELECT MAX(version) FROM migrations";
        try (Connection conn = Config.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to get current migration version", e);
        }
        return 0;
    }

    private static List<MigrationFile> getMigrationFiles() {
        List<MigrationFile> files = new ArrayList<>();
        File migrationsFolder = new File(Main.getInstance().getDataFolder(), "migrations");

        if (!migrationsFolder.exists()) {
            if (!migrationsFolder.mkdirs()) {
                Bukkit.getLogger().log(Level.SEVERE, "Failed to create migrations folder");
                return files;
            }
        }

        File[] sqlFiles = migrationsFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".sql"));

        if (sqlFiles != null && sqlFiles.length > 0) {
            files = Arrays.stream(sqlFiles)
                    .map(file -> new MigrationFile(file.getName()))
                    .sorted()
                    .collect(Collectors.toList());
        }

        return files;
    }

    private static void applyMigration(MigrationFile file) {
        try (InputStream in = new FileInputStream(new File(Main.getInstance().getDataFolder(), "migrations/" + file.getFileName()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             Connection conn = Config.getConnection()) {

            String sql = reader.lines().collect(Collectors.joining("\n"));

            conn.setAutoCommit(false);
            try (Statement stmt = conn.createStatement()) {
                String[] statements = sql.split(";");
                for (String statement : statements) {
                    if (!statement.trim().isEmpty()) {
                        stmt.executeUpdate(statement);
                    }
                }
            }
            conn.commit();
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to apply migration: " + file.getFileName(), e);
        }
    }

    private static void updateMigrationVersion(int version) {
        String sql = "INSERT INTO migrations (version) VALUES (?)";
        try (Connection conn = Config.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, version);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to update migration version", e);
        }
    }

    @Getter
    private static class MigrationFile implements Comparable<MigrationFile> {
        private final String fileName;
        private final int version;

        public MigrationFile(String fileName) {
            this.fileName = fileName;
            Matcher matcher = MIGRATION_FILE_PATTERN.matcher(fileName);
            if (matcher.matches()) {
                this.version = Integer.parseInt(matcher.group(1));
            } else {
                throw new IllegalArgumentException("Invalid migration file name: " + fileName);
            }
        }

        @Override
        public int compareTo(MigrationFile other) {
            return Integer.compare(this.version, other.version);
        }
    }

    private static void copyDefaultMigrations() {
        File migrationsFolder = new File(Main.getInstance().getDataFolder(), "migrations");
        if (!migrationsFolder.exists()) {
            if (!migrationsFolder.mkdirs()) {
                Bukkit.getLogger().log(Level.SEVERE, "Failed to create migrations folder");
                return;
            }
        }

        String[] defaultMigrations = {"V1__initial_tables.sql"};
        for (String migrationFile : defaultMigrations) {
            File file = new File(migrationsFolder, migrationFile);
            if (!file.exists()) {
                Main.getInstance().saveResource("migrations/" + migrationFile, false);
            }
        }
    }
}