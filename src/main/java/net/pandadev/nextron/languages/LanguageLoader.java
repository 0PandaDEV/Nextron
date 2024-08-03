package net.pandadev.nextron.languages;

import net.pandadev.nextron.Main;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
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
            Path myPath;
            FileSystem fileSystem = null;
            try {
                if (uri.getScheme().equals("jar")) {
                    try {
                        fileSystem = FileSystems.getFileSystem(uri);
                    } catch (FileSystemNotFoundException e) {
                        fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
                    }
                    myPath = fileSystem.getPath("/lang");
                } else {
                    myPath = Paths.get(uri);
                }

                try (Stream<Path> walk = Files.walk(myPath, 1)) {
                    walk.filter(path -> !Files.isDirectory(path))
                            .forEach(path -> {
                                String fileName = path.getFileName().toString();
                                File outFile = new File(langFolder, fileName);
                                try (InputStream in = plugin.getResource("lang/" + fileName);
                                        OutputStream out = new FileOutputStream(outFile)) {
                                    if (in == null) {
                                        plugin.getLogger().warning("Language file not found in jar: " + fileName);
                                        return;
                                    }
                                    byte[] buffer = new byte[1024];
                                    int length;
                                    while ((length = in.read(buffer)) > 0) {
                                        out.write(buffer, 0, length);
                                    }
                                } catch (IOException e) {
                                    plugin.getLogger().severe("Failed to save language file: " + fileName);
                                    e.printStackTrace();
                                }
                            });
                }
            } finally {
                if (fileSystem != null && fileSystem != FileSystems.getDefault()) {
                    fileSystem.close();
                }
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to save language files");
            e.printStackTrace();
        }
    }

    private static void deleteDirectory(File directory) {
        if (directory.exists()) {
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
}