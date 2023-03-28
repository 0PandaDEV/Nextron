package tk.pandadev.nextron.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.Configs;

import java.util.ArrayList;
import java.util.List;

public class BackCommand extends CommandBase implements TabCompleter {

    public BackCommand(){
        super("back", "Teleports the player back to the last (death, tpa, home, warp) position.", "/back", "nextron.back");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        
        if (!(sender instanceof Player)) { sender.sendMessage(Main.getCommandInstance()); return; }
        Player player = (Player) (sender);

        if (args.length == 0){
            Configs.settings.set(player.getUniqueId() + ".lastback", player.getLocation());
            Configs.settings.set(player.getUniqueId() + ".isback", true);
            Configs.saveSettingsConfig();
            player.teleport((Location) Configs.settings.get(player.getUniqueId() + ".lastpos"));

        } else {
            player.sendMessage(Main.getPrefix() + "§c/back");
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
            if (!s1.startsWith(currentarg)) continue;
            completerList.add(s);
        }

        return completerList;
    }

}