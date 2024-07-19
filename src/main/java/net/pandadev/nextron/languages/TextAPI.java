package net.pandadev.nextron.languages;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import net.pandadev.nextron.Main;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class TextAPI {
    @Getter
    @Setter
    private static String prefix;

    public static String get(String path, Boolean usePrefix) {
        String language = LanguageLoader.getLanguage();
        Map<?, ?> langMap = loadLanguageFile(language);

        String text = (String) langMap.get(path);
        if (text == null) {
            return "Missing translation: " + path;
        }

        if (prefix == null || !usePrefix) {
            return text;
        } else {
            return prefix + " " + text;
        }
    }

    public static String get(String path, HashMap<String, String> replacements, Boolean usePrefix) {
        String text = get(path, usePrefix);
        return applyReplacements(text, replacements);
    }

    public static String get(String path) {
        return get(path, true);
    }

    public static String get(String path, HashMap<String, String> replacements) {
        return get(path, replacements, true);
    }

    private static Map<?, ?> loadLanguageFile(String language) {
        Gson gson = new Gson();
        File file = new File(Main.getInstance().getDataFolder() + "/lang/" + language + ".json");

        try (Reader reader = Files.newBufferedReader(file.toPath())) {
            return gson.fromJson(reader, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load language file: " + language, e);
        }
    }

    private static String applyReplacements(String text, HashMap<String, String> replacements) {
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());
        }
        return text;
    }
}