package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FlyCommand extends CommandBase implements CommandExecutor, TabCompleter {

    public FlyCommand() {
        super("fly", "Enables/disables fly for you or another player", "/fly [player]", "nextron.fly");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 1) {
            if (!sender.hasPermission("nextron.fly.other")) {
                sender.sendMessage(Main.getNoPerm());
                return;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Main.getInvalidPlayer());
                return;
            }

            if (target.getAllowFlight()) {
                sender.sendMessage(Main.getPrefix() + Text.get("fly.other.off").replace("%t", target.getName()));
            } else {
                sender.sendMessage(Main.getPrefix() + Text.get("fly.other.on").replace("%t", target.getName()));
            }

            if (target.getAllowFlight()) {
                target.setAllowFlight(false);
            } else {
                target.setAllowFlight(false);
            }

            target.setFallDistance(0.0f);

        } else if (args.length == 0) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            Player player = (Player) (sender);

            if (!player.hasPermission("nextron.fly")) {
                player.sendMessage(Main.getNoPerm());
                return;
            }

            if (player.getAllowFlight()) {
                if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) {
                    player.sendMessage(Main.getPrefix() + Text.get("fly.off"));
                }
            } else {
                if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) {
                    player.sendMessage(Main.getPrefix() + Text.get("fly.on"));
                }
            }

            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
            } else {
                player.setAllowFlight(true);
            }

            player.setFallDistance(0.0f);
        } else {
            sender.sendMessage(Main.getPrefix() + "§c/fly [player]");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        if (args.length == 1 && sender.hasPermission("nextron.fly.other")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        }

        ArrayList<String> completerList = new ArrayList<String>();
        String currentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (!s1.startsWith(currentarg))
                continue;
            completerList.add(s);
        }

        return completerList;
    }
}
