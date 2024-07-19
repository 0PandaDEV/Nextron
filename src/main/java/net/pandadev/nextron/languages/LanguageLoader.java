package net.pandadev.nextron.languages;

import net.pandadev.nextron.Main;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.Collections;
import java.util.stream.Stream;

public class LanguageLoader {
    private static String currentLanguage = "en";

    public static void setLanguage(String language) {
        currentLanguage = language;
    }

    public static String getLanguage() {
        return currentLanguage;
    }

    public static void saveLanguages() {
        JavaPlugin plugin = Main.getInstance();
        File langFolder = new File(plugin.getDataFolder(), "lang");
        
        if (langFolder.exists()) {
            deleteDirectory(langFolder);
        }
        
        if (!langFolder.mkdirs()) {
            plugin.getLogger().severe("Failed to create language folder");
            return;
        }

        try {
            URL url = plugin.getClass().getResource("/lang");
            if (url == null) {
                plugin.getLogger().severe("Language resource folder not found");
                return;
            }

            URI uri = url.toURI();
            Path path = uri.getScheme().equals("jar") ? FileSystems.newFileSystem(uri, Collections.emptyMap()).getPath("/lang") : Paths.get(uri);
            try (Stream<Path> paths = Files.walk(path, 1)) {
                paths.filter(p -> p.toString().endsWith(".json"))
                        .forEach(p -> {
                            try (InputStream in = Files.newInputStream(p)) {
                                File outFile = new File(langFolder, p.getFileName().toString());
                                Files.copy(in, outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e) {
                                plugin.getLogger().severe("Failed to save language file: " + p.getFileName() + ". Error: " + e.getMessage());
                            }
                        });
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to load language files. Error: " + e.getMessage());
        }
    }

    private static void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }
}
