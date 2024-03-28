package net.pandadev.nextron.utils.commandapi.paramter;

import lombok.Data;
import lombok.Getter;
import net.pandadev.nextron.utils.commandapi.node.ArgumentNode;
import net.pandadev.nextron.utils.commandapi.paramter.impl.*;
import net.pandadev.nextron.utils.commandapi.processors.*;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class ParamProcessor {
    @Getter
    private static final HashMap<Class<?>, Processor<?>> processors = new HashMap<>();
    private static boolean loaded = false;

    private final ArgumentNode node;
    private final String supplied;
    private final CommandSender sender;

    /**
     * Processes the param into an object
     *
     * @return Processed Object
     */
    public Object get() {
        if (!loaded) loadProcessors();

        Processor<?> processor = processors.get(node.getParameter().getType());
        if (processor == null) return supplied;

        return processor.process(sender, supplied);
    }

    /**
     * Gets the tab completions for the param processor
     *
     * @return Tab Completions
     */
    public List<String> getTabComplete() {
        if (!loaded) loadProcessors();

        Processor<?> processor = processors.get(node.getParameter().getType());
        if (processor == null) return new ArrayList<>();

        return processor.tabComplete(sender, supplied);
    }

    /**
     * Creates a new processor
     *
     * @param processor Processor
     */
    public static void createProcessor(Processor<?> processor) {
        processors.put(processor.getType(), processor);
    }

    /**
     * Loads the processors
     */
    public static void loadProcessors() {
        loaded = true;

        processors.put(int.class, new IntegerProcessor());
        processors.put(long.class, new LongProcessor());
        processors.put(double.class, new DoubleProcessor());
        processors.put(boolean.class, new BooleanProcessor());

        processors.put(ChatColor.class, new ChatColorProcessor());
        processors.put(Player.class, new PlayerProcessor());
        processors.put(OfflinePlayer.class, new OfflinePlayerProcessor());
        processors.put(World.class, new WorldProcessor());
        processors.put(Duration.class, new DurationProcessor());
        processors.put(GameMode.class, new GamemodeProcessor());
        processors.put(HelpCommandInfo.class, new HelpProcessor());
        processors.put(HomeInfo.class, new HomeProcessor());
        processors.put(Language.class, new LanguageProcessor());
        processors.put(Rank.class, new RankProcessor());
        processors.put(Feature.class, new FeatureProcessor());
    }
}
