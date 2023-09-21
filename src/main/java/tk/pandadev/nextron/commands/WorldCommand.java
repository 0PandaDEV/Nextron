package tk.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;

import java.util.ArrayList;
import java.util.List;

public class WorldCommand extends CommandBase implements TabCompleter {

    public WorldCommand() {
        super("world", "Allows you to change the world you're in", "/world <world>", "", "nextron.world");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }
        Player player = (Player) (sender);

        if (args.length == 2 && args[0].equalsIgnoreCase("tp")){
            List<World> worlds = Bukkit.getWorlds();
            List<String> world_names = new ArrayList<>();
            for (World world : worlds){
                world_names.add(world.getName());
            }

            if (!world_names.contains(args[1])){player.sendMessage(Main.getPrefix() + Text.get("world.error")); return;}

            for (World world : worlds){
                if (world.getName().equals(args[1])){
                    player.teleport(world.getSpawnLocation());
                    player.sendMessage(Main.getPrefix() + Text.get("world.success").replace("%w", world.getName()));
                }
            }
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("create")){
            WorldCreator wc = new WorldCreator(args[1]);

            wc.environment(World.Environment.NORMAL);
            wc.type(WorldType.NORMAL);

            player.sendMessage(Main.getPrefix() + "§7Word with the name §a" + args[1] + " §7is being created");
            Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), wc::createWorld);
            player.sendMessage(Main.getPrefix() + "§7Successfully created world with the name §a" + args[1]);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player) (sender);

        if (args.length == 1) {
            list.add("tp");
            list.add("create");
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("tp")){
            List<World> worlds = Bukkit.getWorlds();

            for (World world : worlds){
                list.add(world.getName());
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