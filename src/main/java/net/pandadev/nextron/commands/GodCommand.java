package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import net.pandadev.nextron.Main;

import java.util.ArrayList;
import java.util.List;

public class GodCommand extends CommandBase implements CommandExecutor, TabCompleter {

    public GodCommand() {
        super("god", "Makes you invulnerable", "/god [player]", "", "nextron.god");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }
            Player player = (Player) (sender);

            if (!player.hasPermission("nextron.god")) {
                player.sendMessage(Main.getNoPerm());
                return;
            }

            player.setInvulnerable(!player.isInvulnerable());
            if (player.isInvulnerable())
                player.sendMessage(Main.getPrefix() + Text.get("god.on"));
            else
                player.sendMessage(Main.getPrefix() + Text.get("god.off"));

        } else if (args.length == 1) {
            if (!sender.hasPermission("nextron.god.other")) {
                sender.sendMessage(Main.getNoPerm());
                return;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(Main.getInvalidPlayer());
                return;
            }

            target.setInvulnerable(!target.isInvulnerable());
            if (target.isInvulnerable())
                sender.sendMessage(Main.getPrefix() + Text.get("god.on.other").replace("%p", target.getName()));
            else
                sender.sendMessage(Main.getPrefix() + Text.get("god.off.other").replace("%p", target.getName()));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player) (sender);

        if (args.length == 1) {
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