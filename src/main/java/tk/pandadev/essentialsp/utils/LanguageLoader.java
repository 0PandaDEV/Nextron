package tk.pandadev.essentialsp.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.pandadev.essentialsp.Main;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;

public class LanguageLoader {

    public static HashMap<String, String> translationMap = new HashMap<String, String>();
    private File en;

    public LanguageLoader(Main plugin){
        File languageDirectory = new File(plugin.getDataFolder(), "languages/");
        File defaultLanguageFile = new File(plugin.getDataFolder(), "languages/en_US.yml");

        if (!languageDirectory.exists()){
            languageDirectory.mkdir();
        }

        Main.getInstance().saveResource("languages/en_US.yml", true);
        Main.getInstance().saveResource("languages/de_DE.yml", true);

        if (plugin.getConfig().getString("language").equals("en_US")){
            FileConfiguration translations = YamlConfiguration.loadConfiguration(defaultLanguageFile);
            for (String translation : translations.getKeys(false)){
                translationMap.put(translation, translations.getString(translation));
            }
        } else {
            FileConfiguration translations = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "languages/" + plugin.getConfig().getString("language") + ".yml"));
            for (String translation : translations.getKeys(false)){
                translationMap.put(translation, translations.getString(translation));
            }
        }

    }

    public void copyToFile(InputStream inputStream, File file) throws IOException {
        try(OutputStream outputStream = Files.newOutputStream(file.toPath())) {
            IOUtils.copy(inputStream, outputStream);
        }
    }

}