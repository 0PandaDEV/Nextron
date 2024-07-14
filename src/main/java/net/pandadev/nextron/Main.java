package net.pandadev.nextron;

import ch.hekates.languify.Languify;
import ch.hekates.languify.language.LangLoader;
import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import net.pandadev.nextron.arguments.*;
import net.pandadev.nextron.arguments.objects.*;
import net.pandadev.nextron.commands.*;
import net.pandadev.nextron.listeners.*;
import net.pandadev.nextron.tablist.TablistManager;
import net.pandadev.nextron.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
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
    private LiteCommands<CommandSender> liteCommands;

    public static HashMap<Player, Player> tpa = new HashMap<>();
    public static HashMap<Player, Player> tpahere = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        tablistManager = new TablistManager();

        RankAPI.migration();

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
            RankAPI.checkRank(player);
        }

        tablistManager.setAllPlayerTeams();
        vanishAPI = new VanishAPI(this);

        NoPerm = Prefix + Text.get("no.perms");
        InvalidPlayer = Prefix + Text.get("invalid.player");
        CommandInstance = Prefix + Text.get("command.instance.error");

        registerListeners();

        this.liteCommands = LiteBukkitFactory.builder("nextron", this)
                .commands(
                        new BackCommand(),
                        new EnderchestCommand(),
                        new FeatureCommand(),
                        new FlyCommand(),
                        new GamemodeCommand(),
                        new GetPosCommand(),
                        new GodCommand(),
                        new HatCommand(),
                        new HeadCommand(),
                        new HealCommand(),
                        new HelpCommand(),
                        new HomeCommands(),
                        new InvseeCommand(),
                        new LanguageCommand(),
                        new MenuCommand(),
                        new NickCommand(),
                        new NightVisionCommand(),
                        new PingCommand(),
                        new RankCommand(),
                        new ReloadCommand(),
                        new RenameCommand(),
                        new RepairCommand(),
                        new SpawnCommand(),
                        new SpeedCommand(),
                        new SudoCommand(),
                        new TimeCommand(),
                        new TopCommand(),
                        new TpacceptCommand(),
                        new TpaCommand(),
                        new TpdenyCommand(),
                        new TphereCommand(),
                        new VanishCommand(),
                        new WarpCommands(),
                        new WorldCommand()
                )

                .argument(Feature.class, new FeatureArgument())
                .argument(GameMode.class, new GameModeArgument())
                .argument(Help.class, new HelpArgument())
                .argument(Home.class, new HomeArgument())
                .argument(Language.class, new LanguageArgument())
                .argument(Rank.class, new RankArgument())
                .argument(Seed.class, new SeedArgument())
                .argument(Warp.class, new WarpArgument())

                .message(LiteBukkitMessages.PLAYER_NOT_FOUND, getInvalidPlayer())
                .message(LiteBukkitMessages.PLAYER_ONLY, getCommandInstance())
                .message(LiteBukkitMessages.MISSING_PERMISSIONS, getNoPerm())
                .message(LiteBukkitMessages.INVALID_USAGE, invalidUsage -> getPrefix() + "§cUsage: " + invalidUsage.getSchematic().first())

                .build();

        int pluginId = 20704;
        new Metrics(this, pluginId);

        Bukkit.getConsoleSender().sendMessage(Prefix + Text.get("console.activate"));
    }

    @Override
    public void onDisable() {
        this.liteCommands.unregister();

        LangLoader.saveLanguages(getName(), "-" + getDescription().getVersion());
        LangLoader.loadLanguage(getConfig().getString("language"));

        Configs.saveHomeConfig();
        Configs.saveWarpConfig();
        Configs.saveFeatureConfig();

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
                player.getScoreboard().getTeam(rank).removeEntry(player.getName());
            }
            player.getScoreboard().getTeam("999player").removeEntry(player.getName());
            RankAPI.checkRank(player);

            for (String rank : Main.getInstance().getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                Team finalrank = player.getScoreboard().getTeam(rank.toLowerCase());
                finalrank.setPrefix("");
                player.setDisplayName(player.getName());
            }
        }

        Bukkit.getConsoleSender().sendMessage(Prefix + Text.get("console.disabled"));
        instance = null;
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