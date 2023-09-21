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
import org.joml.Random;
import tk.pandadev.nextron.Main;

import java.io.File;
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

        if (args.length == 3 && args[0].equalsIgnoreCase("create")){
            WorldCreator wc = new WorldCreator(args[1]);

            wc.environment(World.Environment.NORMAL);
            wc.type(WorldType.NORMAL);

            String seed = args[2];
            wc.seed(seed.hashCode());

            player.sendMessage(Main.getPrefix() + Text.get("world.create.start").replace("%w", args[1]));
            Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), wc::createWorld);
            player.sendMessage(Main.getPrefix() + Text.get("world.create.finished").replace("%w", args[1]));
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
            String worldName = args[1];
            World world = Bukkit.getWorld(worldName);
            if (world != null) {
                List<World> worlds = Bukkit.getWorlds();
                worlds.remove(world);
                World newWorld = Bukkit.getWorld("world");
                if (newWorld == null && !worlds.isEmpty()) {
                    newWorld = worlds.get(new Random().nextInt(worlds.size()));
                }
                if (newWorld != null) {
                    for (Player p : world.getPlayers()) {
                        p.teleport(newWorld.getSpawnLocation());
                    }
                }

                player.sendMessage(Main.getPrefix() + Text.get("world.delete.start").replace("%w", worldName));
                Bukkit.unloadWorld(world, true);
                deleteWorld(new File(Bukkit.getServer().getWorldContainer().getAbsolutePath(), worldName));
                player.sendMessage(Main.getPrefix() + Text.get("world.delete.finished").replace("%w", worldName));
            } else {
                player.sendMessage(Main.getPrefix() + Text.get("world.delete.error").replace("%w", worldName));
            }
        }
    }

    public void deleteWorld(File path) {
        if (path.exists()) {
            File files[] = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player) (sender);

        if (args.length == 1) {
            list.add("tp");
            list.add("create");
            list.add("delete");
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("create")){
            list.add("<seed>");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("create")){
            list.add("<name>");
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("tp")){
            List<World> worlds = Bukkit.getWorlds();

            for (World world : worlds){
                list.add(world.getName());
            }
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("delete")){
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