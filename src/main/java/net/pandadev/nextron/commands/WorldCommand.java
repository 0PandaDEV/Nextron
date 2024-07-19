package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.arguments.objects.Seed;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.joml.Random;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RootCommand
@Permission("nextron.world.*")
public class WorldCommand extends HelpBase {

    public WorldCommand() {
        super("world, Allows you to manage your worlds on a server, /world tp <world>\n/world create <name>\n/world delete <world\n/world load <world>\n/world unload <world>");
    }

    @Execute(name = "world tp")
    @Permission("nextron.world.tp")
    public void worldCommand(@Context Player player, @Arg World world) {
        List<World> worlds = Bukkit.getWorlds();
        List<String> world_names = new ArrayList<>();
        for (World worldl : worlds) {
            world_names.add(worldl.getName());
        }

        if (!world_names.contains(world.getName())) {
            player.sendMessage(Main.getPrefix() + TextAPI.get("world.error"));
            return;
        }

        for (World worldl : worlds) {
            if (worldl.getName().equals(world.getName())) {
                player.teleport(worldl.getSpawnLocation());
                player.sendMessage(Main.getPrefix() + TextAPI.get("world.success").replace("%w", worldl.getName()));
            }
        }
    }

    @Execute(name = "world create")
    @Permission("nextron.world.create")
    public void createWorldCommand(@Context CommandSender sender, @Arg String name, @OptionalArg Seed seed) {
        sender.sendMessage(Main.getPrefix() + TextAPI.get("world.create.start").replace("%w", name));

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            WorldCreator wc = new WorldCreator(name);

            wc.environment(World.Environment.NORMAL);
            wc.type(WorldType.NORMAL);

            if (seed != null) {
                wc.seed(seed.getSeed());
            }

            wc.createWorld();
            sender.sendMessage(Main.getPrefix() + TextAPI.get("world.create.finished").replace("%w", name));
        });


        FileWriter writer = null;
        try {
            writer = new FileWriter("worlds.txt", true);
            writer.write(name + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Execute(name = "world delete")
    @Permission("nextron.world.delete")
    public void deleteWorldCommand(@Context CommandSender sender, @Arg World worldName) {
        if (worldName.getName().equals("world")) {
            sender.sendMessage(Main.getPrefix() + TextAPI.get("world.delete.default.error"));
            return;
        }
        World world = Bukkit.getWorld(worldName.getName());
        if (world == null) {
            sender.sendMessage(Main.getPrefix() + TextAPI.get("world.delete.error").replace("%w", worldName.getName()));
            return;
        }
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

        ///////////////

        sender.sendMessage(Main.getPrefix() + TextAPI.get("world.delete.start").replace("%w", worldName.getName()));
        if (deleteWorld(worldName.getName())) {
            sender.sendMessage(Main.getPrefix() + TextAPI.get("world.delete.finished").replace("%w", worldName.getName()));
        } else {
            sender.sendMessage(Main.getPrefix() + TextAPI.get("world.delete.error").replace("%w", worldName.getName()));
        }
    }

    @Execute(name = "world load")
    @Permission("nextron.world.load")
    public void loadCommand(@Context CommandSender sender, @Arg String worldName) {
        File worldFolder = new File(Bukkit.getServer().getWorldContainer().getAbsolutePath(), worldName);
        if (!worldFolder.exists()) {
            sender.sendMessage(Main.getPrefix() + TextAPI.get("world.load.notexist").replace("%w", worldName));
            return;
        }
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            sender.sendMessage(Main.getPrefix() + TextAPI.get("world.load.start").replace("%w", worldName));
            WorldCreator wc = new WorldCreator(worldName);
            wc.createWorld();
            sender.sendMessage(Main.getPrefix() + TextAPI.get("world.load.success").replace("%w", worldName));
        } else {
            sender.sendMessage(Main.getPrefix() + TextAPI.get("world.load.error").replace("%w", worldName));
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter("worlds.txt", true);
            writer.write(worldName + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Execute(name = "world unload")
    @Permission("nextron.world.unload")
    public void unloadCommand(@Context CommandSender sender, @Arg World worldName) {
        if (worldName.getName().equals("world")) {
            sender.sendMessage(Main.getPrefix() + TextAPI.get("world.unload.default.error"));
            return;
        }
        World world = Bukkit.getWorld(worldName.getName());
        if (world != null) {
            List<World> worlds = Bukkit.getWorlds();
            worlds.remove(world);
            ;
            World newWorld = Bukkit.getWorld("world");
            if (newWorld == null && !worlds.isEmpty()) {
                newWorld = worlds.get(new Random().nextInt(worlds.size()));
            }
            if (newWorld != null) {
                for (Player p : world.getPlayers()) {
                    p.teleport(newWorld.getSpawnLocation());
                }
            }

            sender.sendMessage(Main.getPrefix() + TextAPI.get("world.unload.start").replace("%w", worldName.getName()));
            if (Bukkit.unloadWorld(world, true)) {
                sender.sendMessage(Main.getPrefix() + TextAPI.get("world.unload.success").replace("%w", worldName.getName()));
            } else {
                sender.sendMessage(Main.getPrefix() + TextAPI.get("world.unload.error").replace("%w", worldName.getName()));
            }
        } else {
            sender.sendMessage(Main.getPrefix() + TextAPI.get("world.unload.error").replace("%w", worldName.getName()));
        }
    }


    public boolean deleteWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            // World doesn't exist
            return false;
        }

        // Unload the world
        if (!Bukkit.unloadWorld(world, true)) {
            // Failed to unload the world
            return false;
        }

        // Delete the world folder
        Path worldFolder = Paths.get(Bukkit.getWorldContainer().getAbsolutePath(), worldName);
        try {
            Files.walk(worldFolder).map(Path::toFile).sorted((o1, o2) -> -o1.compareTo(o2)).forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


}