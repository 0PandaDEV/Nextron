package net.pandadev.nextron;

import ch.hekates.languify.Languify;
import ch.hekates.languify.language.LangLoader;
import ch.hekates.languify.language.Text;
import net.pandadev.nextron.commands.*;
import net.pandadev.nextron.listeners.*;
import net.pandadev.nextron.tablist.TablistManager;
import net.pandadev.nextron.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public final class Main extends JavaPlugin {

    private static Main instance;
    private static final String Prefix = "§x§b§1§8§0§f§f§lNextron §8» ";
    private VanishAPI vanishAPI;
    private TablistManager tablistManager;
    public static String NoPerm;
    public static String InvalidPlayer;
    public static String CommandInstance;
    private UpdateChecker updateChecker;

    public static HashMap<Player, Player> tpa = new HashMap<>();
    public static HashMap<Player, Player> tpahere = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        tablistManager = new TablistManager();

        Languify.setup(this, this.getDataFolder().toString());
        LangLoader.saveLanguages(getName(), "-" + getDescription().getVersion());
        LangLoader.loadLanguage(getConfig().getString("language"));

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            updateChecker = new UpdateChecker(this, "0pandadev/nextron");
            updateChecker.checkForUpdates();
        });

        loadWorlds();
        saveDefaultConfig();
        Configs.createSettingsConfig();
        Configs.createHomeConfig();
        Configs.createWarpConfig();
        Configs.createFeatureConfig();
        Configs.saveHomeConfig();
        Configs.saveWarpConfig();
        Configs.saveFeatureConfig();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setDisplayName(Configs.settings.getString(player.getUniqueId() + ".nick"));
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            SettingsConfig.checkSettings(player);
            RankAPI.createPlayerTeam(player);
            RankAPI.checkRank(player);
        }

        tablistManager.setAllPlayerTeams();
        vanishAPI = new VanishAPI(this);

        NoPerm = Prefix + Text.get("no.perms");
        InvalidPlayer = Prefix + Text.get("invalid.player");
        CommandInstance = Prefix + Text.get("command.instance.error");

        Bukkit.getConsoleSender().sendMessage(Prefix + Text.get("console.activate"));

        registerListeners();
        registerCommands();

        int pluginId = 20704;
        new Metrics(this, pluginId);
    }

    @Override
    public void onDisable() {
        LangLoader.saveLanguages(getName(), "-" + getDescription().getVersion());
        LangLoader.loadLanguage(getConfig().getString("language"));

        Configs.saveHomeConfig();
        Configs.saveWarpConfig();
        Configs.saveFeatureConfig();

        Bukkit.getConsoleSender().sendMessage(Prefix + Text.get("console.disabled"));

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Main.getInstance().getConfig().getConfigurationSection("Ranks") == null) {
                break;
            }
            for (String rank : getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                for (Team team : player.getScoreboard().getTeams()) {
                    for (String entry : team.getEntries()) {
                        team.removeEntry(entry);
                    }
                }
                player.getScoreboard().getTeam("010" + rank).removeEntry(player.getName());
            }
            player.getScoreboard().getTeam("010player").removeEntry(player.getName());
            RankAPI.checkRank(player);

            for (String rank : Main.getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                Team finalrank = player.getScoreboard().getTeam("010" + rank.toLowerCase());
                finalrank.setPrefix("");
                player.setDisplayName(player.getName());
            }
        }

        instance = null;
    }

    private void registerCommands() {
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
        getCommand("features").setExecutor(new FeatureCommand());
        getCommand("back").setExecutor(new BackCommand());
        getCommand("rename").setExecutor(new RenameCommand());
        getCommand("god").setExecutor(new GodCommand());
        getCommand("help").setExecutor(new HelpCommand());
        getCommand("rl").setExecutor(new ReloadCommand());
        getCommand("tpdeny").setExecutor(new TpdenyCommand());
        getCommand("nightvision").setExecutor(new NightVisionCommand());
        getCommand("nick").setExecutor(new NickCommand());
        getCommand("language").setExecutor(new LanguageCommand());
        getCommand("world").setExecutor(new WorldCommand());
        getCommand("day").setExecutor(new TimeCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("getposition").setExecutor(new GetPosCommand());
        getCommand("hat").setExecutor(new HatCommand());
        getCommand("top").setExecutor(new TopCommand());
        getCommand("tpahere").setExecutor(new TpahereCommand());
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new ChatEditor(), this);
        pluginManager.registerEvents(new InputListener(), this);
        pluginManager.registerEvents(new ClickableMessages(), this);
        pluginManager.registerEvents(new BackCommandListener(), this);
    }

    public void loadWorlds() {
        Path worldsFile = Paths.get("worlds.txt");
        if (Files.exists(worldsFile)) {
            try {
                List<String> worldNames = Files.readAllLines(worldsFile);
                List<String> validWorldNames = worldNames.stream().filter(this::worldExists).collect(Collectors.toList());
                for (String worldName : validWorldNames) {
                    Bukkit.createWorld(new WorldCreator(worldName));
                }
                Files.write(worldsFile, validWorldNames);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean worldExists(String worldName) {
        File worldFolder = new File(Bukkit.getWorldContainer(), worldName);
        return worldFolder.exists();
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
