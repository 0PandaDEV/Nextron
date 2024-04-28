package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpawnCommand extends CommandBase implements TabCompleter {

    public SpawnCommand() {
        super("spawn", "Allows you to set a spawn point accessible with /spawn", "/spawn [player]\n/sp", "nextron.spawn");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }
        Player player = (Player) (sender);

        if (args.length == 0 && label.equalsIgnoreCase("setspawn")) {
            Main.getInstance().getConfig().set("spawn", player.getLocation());
            Main.getInstance().saveConfig();
            player.sendMessage(Main.getPrefix() + Text.get("setspawn.success"));
        } else if (args.length == 0) {
            if (Main.getInstance().getConfig().get("spawn") == null) {
                player.sendMessage(Main.getPrefix() + Text.get("setspawn.error"));
                return;
            }
            player.teleport((Location) Main.getInstance().getConfig().get("spawn"));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
            player.sendMessage(Main.getPrefix() + Text.get("spawn.teleport"));

        } else if (args.length == 1) {
            if (!player.hasPermission("nextron.spawn.other")){
                player.sendMessage(Main.getNoPerm());
                return;
            }

            if (Main.getInstance().getConfig().get("spawn") == null) {
                player.sendMessage(Main.getPrefix() + Text.get("setspawn.error"));
                return;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                player.sendMessage(Main.getInvalidPlayer());
                return;
            }

            target.teleport((Location) Main.getInstance().getConfig().get("spawn"));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
            target.sendMessage(Main.getPrefix() + Text.get("spawn.teleport"));
            player.sendMessage(Main.getPrefix() + Text.get("spawn.teleport.other").replace("%p", target.getName()));
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        if (args.length == 1 && !label.equalsIgnoreCase("setspawn"))
            for (Player player : Bukkit.getOnlinePlayers()) list.add(player.getName());

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