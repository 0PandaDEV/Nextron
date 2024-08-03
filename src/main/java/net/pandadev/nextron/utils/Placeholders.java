package net.pandadev.nextron.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.HomeAPI;
import net.pandadev.nextron.apis.RankAPI;
import net.pandadev.nextron.apis.WarpAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {

    private final Main plugin;

    public Placeholders(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "Nextron";
    }

    @Override
    public @NotNull String getAuthor() {
        return "PandaDEV";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        if (params.equalsIgnoreCase("rank")) {
            return RankAPI.getRank(player);
        }

        if (params.equalsIgnoreCase("homes_count")) {
            return String.valueOf(HomeAPI.getHomes(player).size());
        }

        if (params.equalsIgnoreCase("warps_count")) {
            return String.valueOf(WarpAPI.getWarps().size());
        }

        return null;
    }
}