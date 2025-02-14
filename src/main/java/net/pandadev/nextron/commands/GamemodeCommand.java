package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

@RootCommand
public class GamemodeCommand extends HelpBase {

    public GamemodeCommand() {
        super("gamemode, Changes the gamemode, /gamemode <gamemode> [player]\n/gm <gamemode> [player]", "gmc, Change the gamemode to creative, /gmc [player]", "gms, Change the gamemode to survival, /gms [player]", "gma, Change the gamemode to adventure, /gma [player]", "gmsp, Change the gamemode to spectator, /gmsp [player]", "survival, Change the gamemode to survival, /survival [player]", "creative, Change the gamemode to creative, /creative [player]", "adventure, Change the gamemode to adventure, /adventure [player]", "spectator, Change the gamemode to spectator, /spectator [player]", "gm0, Change the gamemode to survival, /gm0 [player]", "gm1, Change the gamemode to creative, /gm1 [player]", "gm2, Change the gamemode to adventure, /gm2 [player]", "gm3, Change the gamemode to spectator, /gm3 [player]");
    }

    @Execute(name = "gmc", aliases = {"creative", "gm1"})
    @Permission("nextron.gamemode.creative")
    public void creative(@Context CommandSender sender, @Arg Optional<Player> target) {
        if (target.isEmpty()) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(Main.getPrefix() + TextAPI.get("gamemode.success")
                    .replace("%g", "creative"));
        } else {
            Player pTarget = target.get();
            pTarget.setGameMode(GameMode.CREATIVE);
            sender.sendMessage(Main.getPrefix() + TextAPI.get("gamemode.other.success")
                    .replace("%t", pTarget.getName())
                    .replace("%g", "creative"));
        }
    }

    @Execute(name = "gms", aliases = {"survival", "gm0"})
    @Permission("nextron.gamemode.survival")
    public void survival(@Context CommandSender sender, @Arg Optional<Player> target) {
        if (target.isEmpty()) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(Main.getPrefix() + TextAPI.get("gamemode.success")
                    .replace("%g", "survival"));
        } else {
            Player pTarget = target.get();
            pTarget.setGameMode(GameMode.SURVIVAL);
            sender.sendMessage(Main.getPrefix() + TextAPI.get("gamemode.other.success")
                    .replace("%t", pTarget.getName())
                    .replace("%g", "survival"));
        }
    }

    @Execute(name = "gmsp", aliases = {"spectator", "gm3"})
    @Permission("nextron.gamemode.spectator")
    public void spectator(@Context CommandSender sender, @Arg Optional<Player> target) {
        if (target.isEmpty()) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(Main.getPrefix() + TextAPI.get("gamemode.success")
                    .replace("%g", "spectator"));
        } else {
            Player pTarget = target.get();
            pTarget.setGameMode(GameMode.SPECTATOR);
            sender.sendMessage(Main.getPrefix() + TextAPI.get("gamemode.other.success")
                    .replace("%t", pTarget.getName())
                    .replace("%g", "spectator"));
        }
    }

    @Execute(name = "gma", aliases = {"adventure", "gm2"})
    @Permission("nextron.gamemode.adventure")
    public void adventure(@Context CommandSender sender, @Arg Optional<Player> target) {
        if (target.isEmpty()) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }
            player.setGameMode(GameMode.ADVENTURE);
            player.sendMessage(Main.getPrefix() + TextAPI.get("gamemode.success")
                    .replace("%g", "adventure"));
        } else {
            Player pTarget = target.get();
            pTarget.setGameMode(GameMode.ADVENTURE);
            sender.sendMessage(Main.getPrefix() + TextAPI.get("gamemode.other.success")
                    .replace("%t", pTarget.getName())
                    .replace("%g", "adventure"));
        }
    }

    @Execute(name = "gamemode", aliases = {"gm"})
    @Permission("nextron.gamemode")
    public void gamemode(@Context CommandSender sender, @Arg GameMode gamemode, @Arg Optional<Player> target) {
        if (target.isEmpty()) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }
            player.setGameMode(gamemode);
            player.sendMessage(Main.getPrefix() + TextAPI.get("gamemode.success")
                    .replace("%g", gamemode.toString().toLowerCase()));
        } else {
            if (!sender.hasPermission("nextron.gamemode.other")) {
                sender.sendMessage(Main.getNoPerm());
                return;
            }
            Player pTarget = target.get();
            pTarget.setGameMode(gamemode);
            sender.sendMessage(Main.getPrefix() + TextAPI.get("gamemode.other.success")
                    .replace("%t", pTarget.getName())
                    .replace("%g", gamemode.toString().toLowerCase()));
        }
    }
}
