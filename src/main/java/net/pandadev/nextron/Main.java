package net.pandadev.nextron;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import lombok.Getter;
import net.pandadev.nextron.apis.*;
import net.pandadev.nextron.arguments.*;
import net.pandadev.nextron.arguments.objects.*;
import net.pandadev.nextron.commands.*;
import net.pandadev.nextron.database.Migrations;
import net.pandadev.nextron.languages.LanguageLoader;
import net.pandadev.nextron.languages.TextAPI;
import net.pandadev.nextron.listeners.*;
import net.pandadev.nextron.tablist.TablistManager;
import net.pandadev.nextron.utils.Metrics;
import net.pandadev.nextron.utils.Placeholders;
import net.pandadev.nextron.utils.UpdateChecker;
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
import java.util.Objects;
import java.util.stream.Collectors;

public final class Main extends JavaPlugin {

    @Getter
    private static Main instance;
    @Getter
    private static final String Prefix = "§x§b§1§8§0§f§f§lNextron §8» ";
    @Getter
    private VanishAPI vanishAPI;
    @Getter
    private TablistManager tablistManager;
    @Getter
    public static String NoPerm;
    @Getter
    public static String InvalidPlayer;
    @Getter
    public static String CommandInstance;
    private UpdateChecker updateChecker;
    private LiteCommands<CommandSender> liteCommands;

    public static HashMap<Player, Player> tpa = new HashMap<>();
    public static HashMap<Player, Player> tpahere = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        tablistManager = new TablistManager();

        // database migration
        Migrations.checkAndApplyMigrations();

        // register all the APIs for configs
        RankAPI.migration();
        HomeAPI.migration();
        WarpAPI.migration();
        FeatureAPI.migration();
        SettingsAPI.migration();

        // register placeholderAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholders(this).register();
        }

        // translation system init
        LanguageLoader.saveLanguages();
        LanguageLoader.setLanguage(getConfig().getString("language"));

        // command system init
        this.liteCommands = LiteBukkitFactory.builder("nextron", this)
                .commands(new BackCommand(), new EnderchestCommand(), new FeatureCommand(), new FlyCommand(),
                        new GamemodeCommand(), new GetPosCommand(), new GodCommand(), new HatCommand(),
                        new HeadCommand(), new HealCommand(), new HelpCommand(), new HomeCommands(),
                        new InvseeCommand(), new LanguageCommand(), new MenuCommand(), new NickCommand(),
                        new NightVisionCommand(), new PingCommand(), new RankCommand(), new ReloadCommand(),
                        new RenameCommand(), new RepairCommand(), new SpawnCommand(), new SpeedCommand(),
                        new SudoCommand(), new TimeCommand(), new TopCommand(), new TpacceptCommand(), new TpaCommand(),
                        new TpdenyCommand(), new TphereCommand(), new VanishCommand(), new WarpCommands(),
                        new WorldCommand(),
                        new TpahereCommand())

                .argument(Feature.class, new FeatureArgument()).argument(GameMode.class, new GameModeArgument())
                .argument(Help.class, new HelpArgument()).argument(Home.class, new HomeArgument())
                .argument(Language.class, new LanguageArgument()).argument(Rank.class, new RankArgument())
                .argument(Seed.class, new SeedArgument()).argument(Warp.class, new WarpArgument())

                .message(LiteBukkitMessages.PLAYER_NOT_FOUND, getInvalidPlayer())
                .message(LiteBukkitMessages.PLAYER_ONLY, getCommandInstance())
                .message(LiteBukkitMessages.MISSING_PERMISSIONS, getNoPerm())
                .message(LiteBukkitMessages.INVALID_USAGE,
                        invalidUsage -> getPrefix() + "§cUsage: " + invalidUsage.getSchematic().first())

                .build();

        // check for updates from github
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            updateChecker = new UpdateChecker(this, "0pandadev/nextron");
            updateChecker.checkForUpdates();
        });

        loadWorlds();
        saveDefaultConfig();
        reloadConfig();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setDisplayName(SettingsAPI.getNick(player));
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            SettingsAPI.initializeUser(player);
            RankAPI.checkRank(player);
            VanishAPI.executeVanish(player);
        }

        tablistManager.setAllPlayerTeams();

        NoPerm = Prefix + TextAPI.get("no.perms");
        InvalidPlayer = Prefix + TextAPI.get("invalid.player");
        CommandInstance = Prefix + TextAPI.get("command.instance.error");

        registerListeners();

        int pluginId = 20704;
        new Metrics(this, pluginId);

        Bukkit.getConsoleSender().sendMessage(Prefix + TextAPI.get("console.activate"));
    }

    @Override
    public void onDisable() {
        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }

        for (Team team : Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard().getTeams()) {
            team.unregister();
        }

        Bukkit.getConsoleSender().sendMessage(Prefix + TextAPI.get("console.disabled"));
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
                List<String> validWorldNames = worldNames.stream().filter(this::worldExists)
                        .collect(Collectors.toList());
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

}