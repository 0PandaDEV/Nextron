package tk.pandadev.nextron.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.LanguageLoader;

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

            if (target != null){
                Main.tpa.remove(player);
                Main.tpa.remove(target);

                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tpdeny_player").replace("%p", target.getName()));
                target.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tpdeny_target").replace("%p", player.getName()));

                target.playSound(target.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);

            } else {
                player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tpaccept_error"));
            }
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player) (sender);


        return list;
    }

}