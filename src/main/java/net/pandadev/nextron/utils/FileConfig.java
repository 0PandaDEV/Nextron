package net.pandadev.nextron.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class FileConfig extends YamlConfiguration {

    private final String path;

    public FileConfig(String folder, String filname){
        this.path = "plugins/" + folder + "/" + filname;

        try {
            load(this.path);
        } catch (InvalidConfigurationException | IOException ex){
            ex.printStackTrace();
        }
    }

    public FileConfig(String filname){
        this("LobbySystem", filname);
    }

    public void saveConfig(){
        try {
            save(this.path);
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
