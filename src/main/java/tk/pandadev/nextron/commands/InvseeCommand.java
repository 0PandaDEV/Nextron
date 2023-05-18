package tk.pandadev.nextron.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.LanguageLoader;

import java.util.ArrayList;
import java.util.List;

public class InvseeCommand extends CommandBase implements CommandExecutor, TabCompleter {

    public InvseeCommand(){
        super("invsee", "Lets you inspect and control another player's inventory", "/invsee <player>", "", "nextron.invsee");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        Player player = (Player) (sender);

        if (args.length == 1){

            if (player.hasPermission("nextron.invsee")) {

                Player target = Bukkit.getPlayer(args[0]);

                if (target != null){

                    if (target != player){
                        player.openInventory(target.getInventory());
                    } else {
                        player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("invsee_error"));
                    }

                }else {
                    player.sendMessage(Main.getInvalidPlayer());
                }

            } else {
                player.sendMessage(Main.getNoPerm());
            }


        } else {
            player.sendMessage(Main.getPrefix() + "§c/invsee <player>");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player)(sender);

        if (args.length == 1 && playert.hasPermission("nextron.invsee")){
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
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
