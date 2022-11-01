package tk.pandadev.essentialsp.commands;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.Configs;
import tk.pandadev.essentialsp.utils.LanguageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WarpCommands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return false;
        }

        Player player = (Player) (sender);
        
        if (label.equalsIgnoreCase("setwarp") && args.length == 1){

            if (!player.hasPermission("essentialsp.setwarp")) {
                player.sendMessage(Main.getNoPerm());
                return false;
            }
            if (Configs.warp.get("Warps." + args[0].toLowerCase()) != null){
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("setwarp_error").replace("%w", args[0].toLowerCase()));
                return false;
            }
            Configs.warp.set("Warps." + args[0].toLowerCase(), player.getLocation());
            Configs.saveWarpConfig();
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("setwarp_success").replace("%w", args[0].toLowerCase()));
            return true;

        } else if (label.equalsIgnoreCase("delwarp") && args.length == 1){

            if (!player.hasPermission("essentialsp.delwarp")){
                player.sendMessage(Main.getNoPerm());
                return false;
            }
            if (Configs.warp.get("Warps." + args[0].toLowerCase()) == null) {
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("delwarp_error").replace("%w", args[0].toLowerCase()));
                return false;
            }
            Configs.warp.set("Warps." + args[0].toLowerCase(), null);
            Configs.saveWarpConfig();
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("delwarp_success").replace("%w", args[0].toLowerCase()));
            return true;

        } else if (label.equalsIgnoreCase("warp") && args.length == 1){

            if (Configs.warp.get("Warps." + args[0].toLowerCase()) == null) {
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("warp_error").replace("%w", args[0].toLowerCase()));
                return false;
            }
            player.teleport((Location) Configs.warp.get("Warps." + args[0].toLowerCase()));
            if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("warp_success").replace("%w", args[0].toLowerCase()));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            return true;

        } else if (label.equalsIgnoreCase("renamewarp") && args.length == 2) {

            if (Configs.warp.get("Warps." + args[0].toLowerCase()) == null) {
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("warp_error").replace("%w", args[0].toLowerCase()));
                return false;
            }

            Configs.warp.set("Warps." + args[1], (Location) Configs.warp.get("Warps." + args[0]));
            Configs.warp.set("Warps." + args[0], null);
            Configs.saveHomeConfig();
            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("warp_rename_success").replace("%h", args[0]).replace("%n", args[1]));

        } else {
            player.sendMessage(Main.getPrefix() + "§c/warp|setwarp|delwarp <NAME>");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        if (Configs.warp.getConfigurationSection("Warps") == null || Configs.warp.getConfigurationSection("Warps").getKeys(false).isEmpty()) {
            return null;
        } else if (args.length == 1 && label.equalsIgnoreCase("warp") || label.equalsIgnoreCase("delwarp") || label.equalsIgnoreCase("renamewarp")) {
            list.addAll(Objects.requireNonNull(Configs.warp.getConfigurationSection("Warps")).getKeys(false));
        }

        ArrayList<String> completerList = new ArrayList<String>();
        String currentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (!s1.startsWith(currentarg)) continue;
            completerList.add(s);
        }

        return completerList;
    }
}
