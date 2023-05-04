package tk.pandadev.nextron.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.Configs;
import tk.pandadev.nextron.utils.LanguageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TphereCommand extends CommandBase implements CommandExecutor, TabCompleter {

    public TphereCommand(){
        super("tphere", "Teleports a player to you", "/tphere <player>", "/tph", "nextron.tphere");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        Player player = (Player) (sender);

        if (label.equalsIgnoreCase("tphere") || label.equalsIgnoreCase("tph") && args.length == 1){
            if (player.hasPermission("nextron.tphere")){
                Player target = Bukkit.getPlayer(args[0]);

                if (target != null){

                    if (!Objects.equals(target.getName(), player.getName())) {
                        target.teleport(player.getLocation());

                        if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")){
                            player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tphere_success").replace("%t", target.getName()));
                        }

                    } else {
                        player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("tphere_error"));
                    }
                } else {
                    player.sendMessage(Main.getInvalidPlayer());
                }
            }
        } else {
            player.sendMessage(Main.getPrefix() + "§c/tphere <player>");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        ArrayList<String> list = new ArrayList<String>();

        if (args.length == 1){
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        }

        return list;
    }
}
