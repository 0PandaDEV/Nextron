package tk.pandadev.essentialsp;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import tk.pandadev.essentialsp.commands.*;
import tk.pandadev.essentialsp.listeners.ChatEditor;
import tk.pandadev.essentialsp.listeners.InventoryClickListener;
import tk.pandadev.essentialsp.listeners.JoinListener;
import tk.pandadev.essentialsp.listeners.QuitListener;
import tk.pandadev.essentialsp.tablist.TablistManager;
import tk.pandadev.essentialsp.utils.Config;
import tk.pandadev.essentialsp.utils.SettingsConfig;
import tk.pandadev.essentialsp.utils.VanishAPI;
import tk.pandadev.essentialsp.utils.VanishManager;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class Main extends JavaPlugin {

    private static Main instance;
    private Config config;

    private File settingsConfig;
    private FileConfiguration settings;
    private static String Prefix = "§a§lEssentialsP §8» ";
    private static String NoPerm = Prefix + "§cDazu hast du keine Berechtigung";
    private static String InvalidPlayer = Prefix + "§cDieser Spieler ist nicht Online";
    private VanishManager vanishManager;
    private VanishAPI vanishAPI;
    private TablistManager tablistManager;

    public static HashMap<Player, Player> tpa = new HashMap<>();

    @Override
    public void onEnable() {
        createCustomConfig();
        saveDefaultConfig();
        vanishManager = new VanishManager(this);
        vanishAPI = new VanishAPI(this);
        tablistManager = new TablistManager();
        instance = this;
        Bukkit.getConsoleSender().sendMessage(Prefix + "§aAktiviert");

        registerListeners();
        registerCommands();

        for (Player player : Bukkit.getOnlinePlayers()) {
            SettingsConfig.checkSettings(player);
            Main.getInstance().getTablistManager().setAllPlayerTeams();
            run();
        }
    }

    @Override
    public void onDisable() {
        instance = null;
        Bukkit.getConsoleSender().sendMessage(Prefix + "§cDeaktiviert");
    }

    private void registerCommands(){
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("enderchest").setExecutor(new GamemodeCommand());
        getCommand("home").setExecutor(new HomeCommands());
        getCommand("invsee").setExecutor(new InvseeCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("enderchest").setExecutor(new EnderchestCommand());
        getCommand("tpa").setExecutor(new TpaCommand());
        getCommand("tpaccept").setExecutor(new TpacceptCommand());
        getCommand("warp").setExecutor(new WarpCommands());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("tphere").setExecutor(new TphereCommand());
        getCommand("speed").setExecutor(new SpeedCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("sudo").setExecutor(new SudoCommand());
        getCommand("head").setExecutor(new HeadCommand());
        getCommand("rank").setExecutor(new RankCommand());
        getCommand("menu").setExecutor(new MenuCommand());
    }

    private void registerListeners(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new ChatEditor(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
    }

    private void createCustomConfig() {
        settingsConfig = new File(getDataFolder(), "settings.yml");
        if (!settingsConfig.exists()) {
            settingsConfig.getParentFile().mkdirs();
            saveResource("settings.yml", false);
        }

        settings = new YamlConfiguration();
        try {
            settings.load(settingsConfig);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        /* User Edit:
            Instead of the above Try/Catch, you can also use
            YamlConfiguration.loadConfiguration(customConfigFile)
        */
    }

    public void saveSettingsConfig() {
        try{
            settings.save(settingsConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileConfiguration getSettingsConfig() {
        return this.settings;
    }

    public static Main getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return Prefix;
    }

    public static String getInvalidPlayer() {
        return InvalidPlayer;
    }

    public static String getNoPerm() {
        return NoPerm;
    }

    public VanishManager getVanishManager() {
        return vanishManager;
    }

    public TablistManager getTablistManager() {
        return tablistManager;
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Main.getInstance().getTablistManager().setAllPlayerTeams();
            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }

    public VanishAPI getVanishAPI() {
        return vanishAPI;
    }

}
