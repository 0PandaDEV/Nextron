package tk.pandadev.essentialsp.commands;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.essentialsp.Main;

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

            if (player.hasPermission("essentialsp.setwarp")){
                if (Main.getInstance().getConfig().get("Warps." + args[0].toLowerCase()) == null) {
                    Main.getInstance().getConfig().set("Warps." + args[0].toLowerCase(), player.getLocation());
                    Main.getInstance().saveConfig();
                    player.sendMessage(Main.getPrefix() + "§7Du hast den Warp §a" + args[0] + "§7 erstellt!");
                    return true;
                }
                player.sendMessage(Main.getPrefix() + "§cDen Warp §6" + args[0] + "§c gibt es schon");
            } else {
                player.sendMessage(Main.getNoPerm());
            }

        } else if (label.equalsIgnoreCase("delwarp") && args.length == 1){

            if (player.hasPermission("essentialsp.delwarp")){
                if (Main.getInstance().getConfig().get("Warps." + args[0].toLowerCase()) != null) {
                    Main.getInstance().getConfig().set("Warps." + args[0].toLowerCase(), null);
                    Main.getInstance().saveConfig();
                    player.sendMessage(Main.getPrefix() + "§7Du hast den Warp §a" + args[0] + "§7 gelöscht!");
                    return true;
                }
                player.sendMessage(Main.getPrefix() + "§cDen Warp §6" + args[0] + "§c gibt es nicht");
            } else {
                player.sendMessage(Main.getNoPerm());
            }

        } else if (label.equalsIgnoreCase("warp") && args.length == 1){

            if (Main.getInstance().getConfig().get("Warps." + args[0].toLowerCase()) != null) {
                Location location = (Location) Main.getInstance().getConfig().get("Warps." + args[0].toLowerCase());
                player.teleport(location);
                if (Main.getInstance().getSettingsConfig().getBoolean(player.getUniqueId() + ".feedback")){
                    player.sendMessage(Main.getPrefix() + "§7Du wurdest zum Warp §a" + args[0] + "§7 teleportiert!");
                }
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                return true;
            } else {
                player.sendMessage(Main.getPrefix() + "§cDen Warp §6" + args[0].toLowerCase() + "§c gibt es nicht");
            }

        } else {
            player.sendMessage(Main.getPrefix() + "§c/warp|setwarp|delwarp <NAME>");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        if (args.length == 1) {
            list.addAll(Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("Warps")).getKeys(false));
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
