package tk.pandadev.nextron.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private FileConfiguration configuration;
    private File file;

    public Config(String name, File path) {
        this.file = new File(path, name);
        if (!this.file.exists()) {
            path.mkdirs();
        }
        try {
            this.file.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.configuration = new YamlConfiguration();
        try {
            this.configuration.load(this.file);
        }
        catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfiguration() {
        return this.configuration;
    }

    public void save() {
        try {
            this.configuration.save(this.file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            this.configuration.load(this.file);
        }
        catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Object get(String path) {
        return this.configuration.get(path);
    }

    public void set(String path, Object obj) {
        this.configuration.set(path, obj);
    }

    public boolean contains(String path) {
        return this.configuration.contains(path);
    }

    public boolean isClear() {
        return this.configuration.getKeys(false).size() == 0;
    }
}
