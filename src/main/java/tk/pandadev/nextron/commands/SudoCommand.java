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

public class SudoCommand extends CommandBase implements CommandExecutor, TabCompleter {

    public SudoCommand(){
        super("sudo", "Forces a player to execute a command", "/sudo <player> <command>", "nextron.sudo");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length >= 2){
            if (sender.hasPermission("nextron.sudo")){
                Player target = Bukkit.getPlayer(args[0]);

                if (target != null){

                    StringBuilder sb = new StringBuilder();
                    for(int i = 1; i < args.length; i++) {
                        if (i > 1) sb.append(" ");
                        sb.append(args[i]);
                    }

                    target.chat("/" + sb);

                    sender.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("sudo_success").replace("%t", target.getName()).replace("%b", sb));
                } else {
                    sender.sendMessage(Main.getInvalidPlayer());
                }
            }
        }else {
            sender.sendMessage(Main.getPrefix() + "§c/sudo <player> <command>");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player playert = (Player) (sender);
        ArrayList<String> list = new ArrayList<String>();
        if (args.length == 1){
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
