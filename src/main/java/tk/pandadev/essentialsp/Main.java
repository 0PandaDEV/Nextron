package tk.pandadev.essentialsp;

import games.negative.framework.BasePlugin;
import games.negative.framework.database.Column;
import games.negative.framework.database.ColumnType;
import games.negative.framework.database.Database;
import games.negative.framework.database.builder.TableBuilder;
import games.negative.framework.scoreboard.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import tk.pandadev.essentialsp.commands.*;
import tk.pandadev.essentialsp.listeners.*;
import tk.pandadev.essentialsp.tablist.TablistManager;
import tk.pandadev.essentialsp.utils.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public final class Main extends BasePlugin {

    private static Main instance;
    private static final String Prefix = "§a§lEssentialsP §8» ";
    private VanishManager vanishManager;
    private VanishAPI vanishAPI;
    private TablistManager tablistManager;
    public static String NoPerm;
    public static String InvalidPlayer;
    public static String CommandInstance;

    public static HashMap<Player, Player> tpa = new HashMap<>();

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        saveDefaultConfig();
        Configs.createSettingsConfig();
        Configs.createHomeConfig();
        Configs.createWarpConfig();
        Configs.saveHomeConfig();
        Configs.saveWarpConfig();
        vanishManager = new VanishManager(this);
        vanishAPI = new VanishAPI(this);
        tablistManager = new TablistManager();
        new LanguageLoader(this);

        NoPerm = Prefix + LanguageLoader.translationMap.get("no_perms");
        InvalidPlayer = Prefix + LanguageLoader.translationMap.get("invalid_player");
        CommandInstance = Prefix + LanguageLoader.translationMap.get("command_instance_error");

        Bukkit.getConsoleSender().sendMessage(Prefix + LanguageLoader.translationMap.get("activate_message"));

        registerListeners();
        registerCommands();
        getTablistManager().setAllPlayerTeams();

        for (Player player : Bukkit.getOnlinePlayers()) {
            SettingsConfig.checkSettings(player);
            RankAPI.createPlayerTeam(player);
            RankAPI.checkRank(player);
        }
    }

    @Override
    public void onDisable() {
        instance = null;
        Bukkit.getConsoleSender().sendMessage(Prefix + LanguageLoader.translationMap.get("deactivate_message"));
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
        getCommand("rl").setExecutor(new ReloadCommand());
    }


    private void registerListeners(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new ChatEditor(), this);
        pluginManager.registerEvents(new InputListener(), this);
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

    public VanishAPI getVanishAPI() {
        return vanishAPI;
    }

    public static String getCommandInstance() {
        return CommandInstance;
    }
}
