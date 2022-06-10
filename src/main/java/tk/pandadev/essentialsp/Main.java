package tk.pandadev.essentialsp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import tk.pandadev.essentialsp.commands.*;
import tk.pandadev.essentialsp.listeners.ChatEditor;
import tk.pandadev.essentialsp.listeners.JoinListener;
import tk.pandadev.essentialsp.listeners.QuitListener;
import tk.pandadev.essentialsp.tablist.TablistManager;
import tk.pandadev.essentialsp.utils.Config;
import tk.pandadev.essentialsp.utils.VanishManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class Main extends JavaPlugin {

    private static Main instance;
    private Config config;
    private static String Prefix = "§x§5§5§f§f§5§5§lE§x§5§5§f§f§6§1§lS§x§5§5§f§f§6§d§lS§x§5§5§f§f§7§9§lE§x§5§5§f§f§8§5§lN§x§5§5§f§f§9§2§lT§x§5§5§f§f§9§e§lI§x§5§5§f§f§a§a§lA§x§5§5§f§f§b§6§lL§x§5§5§f§f§c§2§lS §8» ";
    private static String NoPerm = Prefix + "§cDazu hast du keine Berechtigung";
    private static String InvalidPlayer = Prefix + "§cDieser Spieler ist nicht Online";
    private VanishManager vanishManager;
    private TablistManager tablistManager;

    public static HashMap<Player, Player> tpa = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        vanishManager = new VanishManager(this);
        tablistManager = new TablistManager();
        instance = this;
        Bukkit.getConsoleSender().sendMessage(Prefix + "§aAktiviert");

        registerListeners();
        registerCommands();

        for (Player player : Bukkit.getOnlinePlayers()) {
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
    }

    private void registerListeners(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new ChatEditor(), this);
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

}
