package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import net.pandadev.nextron.Main;

import java.util.ArrayList;
import java.util.List;

public class TpdenyCommand extends CommandBase implements TabCompleter {

    public TpdenyCommand() {
        super("tpdeny", "Denys an incoming tpa request", "/tpdeny", "/tpd", "nextron.tpdeny");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        Player player = (Player) (sender);

        if (args.length == 0) {

            Player target = Main.tpa.get(player);

            if (target != null) {
                Main.tpa.remove(player);
                Main.tpa.remove(target);

                player.sendMessage(Main.getPrefix() + Text.get("tpdeny.player").replace("%p", target.getName()));
                target.sendMessage(Main.getPrefix() + Text.get("tpdeny.target").replace("%p", player.getName()));

                target.playSound(target.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);

            } else {
                player.sendMessage(Main.getPrefix() + Text.get("tpaccept.error"));
            }
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player) (sender);

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
