package net.pandadev.nextron.database;

import lombok.Getter;
import net.pandadev.nextron.Main;
import org.bukkit.Bukkit;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Migrations {

    private static final Pattern MIGRATION_FILE_PATTERN = Pattern.compile("V(\\d+)__.*\\.sql");

    public static void checkAndApplyMigrations() {
        ensureDatabaseFileExists();
        createMigrationsTable();
        applyMigrations();
    }

    private static void ensureDatabaseFileExists() {
        File dbFile = new File("plugins/Nextron/data.db");
        if (!dbFile.exists()) {
            try {
                dbFile.getParentFile().mkdirs();
                dbFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Failed to create database file", e);
            }
        }
    }

    private static void createMigrationsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS migrations (id INTEGER PRIMARY KEY, version INTEGER NOT NULL)";
        try (Connection conn = Config.getConnection(); Statement stmt = conn.createStatement()) {
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
        try {
            String path = "/migrations/";
            URL url = Main.class.getResource(path);
            if (url == null) {
                Bukkit.getLogger().log(Level.SEVERE, "Failed to find migrations folder in resources");
                return files;
            }

            if (url.getProtocol().equals("jar")) {
                String jarPath = url.getPath().substring(5, url.getPath().indexOf("!"));
                try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8))) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if (name.startsWith("migrations/") && name.endsWith(".sql")) {
                            files.add(new MigrationFile(name.substring(name.lastIndexOf('/') + 1)));
                        }
                    }
                }
            } else {
                File folder = new File(url.toURI());
                File[] listOfFiles = folder.listFiles();
                if (listOfFiles != null) {
                    for (File file : listOfFiles) {
                        if (file.isFile() && file.getName().endsWith(".sql")) {
                            files.add(new MigrationFile(file.getName()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to read migration files", e);
        }

        return files.stream().sorted().collect(Collectors.toList());
    }

    private static void applyMigration(MigrationFile file) {
        try (InputStream in = Main.class.getResourceAsStream("/migrations/" + file.getFileName())) {
            assert in != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));
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
            }
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to apply migration: " + file.getFileName(), e);
            throw new RuntimeException("Failed to apply migration: " + file.getFileName(), e);
        }
    }

    private static void updateMigrationVersion(int version) {
        String sql = "INSERT INTO migrations (version) VALUES (?)";
        try (Connection conn = Config.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
}