package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.Main;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RootCommand
public class GamemodeCommand extends HelpBase {

    public GamemodeCommand() {
        super(
                "gamemode, Changes the gamemode, /gamemode <gamemode> [player]\n/gm <gamemode> [player]",
                "gms, Change the gamemode to survival, /gms [player]",
                "gmc, Change the gamemode to creative, /gmc [player]",
                "gma, Change the gamemode to adventure, /gma [player]",
                "gmsp, Change the gamemode to spectator, /gmsp [player]",
                "survival, Change the gamemode to survival, /survival [player]",
                "creative, Change the gamemode to creative, /creative [player]",
                "adventure, Change the gamemode to adventure, /adventure [player]",
                "spectator, Change the gamemode to spectator, /spectator [player]",
                "gm0, Change the gamemode to survival, /gm0 [player]",
                "gm1, Change the gamemode to creative, /gm1 [player]",
                "gm2, Change the gamemode to adventure, /gm2 [player]",
                "gm3, Change the gamemode to spectator, /gm3 [player]"
        );
    }


    @Execute(name = "gmc", aliases = {"creative", "gm1"})
    @Permission("nextron.gamemode.creative")
    void creative(@Context CommandSender sender, @OptionalArg Player target) {
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§6This command can only be run by a player!");
                return;
            }

            Player player = (Player) (sender);
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(Main.getPrefix() + Text.get("gamemode.success").replace("%g", GameMode.CREATIVE.toString().toLowerCase()));
            return;
        }

        target.setGameMode(GameMode.CREATIVE);
        sender.sendMessage(Main.getPrefix() + Text.get("gamemode.other.success").replace("%t", target.getName()).replace("%g", GameMode.CREATIVE.toString().toLowerCase()));
    }

    @Execute(name = "gms", aliases = {"survival", "gm0"})
    @Permission("nextron.gamemode.survival")
    void survival(@Context CommandSender sender, @OptionalArg Player target) {
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§6This command can only be run by a player!");
                return;
            }

            Player player = (Player) (sender);
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(Main.getPrefix() + Text.get("gamemode.success").replace("%g", GameMode.SURVIVAL.toString().toLowerCase()));
            return;
        }

        target.setGameMode(GameMode.SURVIVAL);
        sender.sendMessage(Main.getPrefix() + Text.get("gamemode.other.success").replace("%t", target.getName()).replace("%g", GameMode.SURVIVAL.toString().toLowerCase()));
    }

    @Execute(name = "gmsp", aliases = {"spectator", "gm3"})
    @Permission("nextron.gamemode.spectator")
    void spectator(@Context CommandSender sender, @OptionalArg Player target) {
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§6This command can only be run by a player!");
                return;
            }

            Player player = (Player) (sender);
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(Main.getPrefix() + Text.get("gamemode.success").replace("%g", GameMode.SPECTATOR.toString().toLowerCase()));
            return;
        }

        target.setGameMode(GameMode.SPECTATOR);
        sender.sendMessage(Main.getPrefix() + Text.get("gamemode.other.success").replace("%t", target.getName()).replace("%g", GameMode.SPECTATOR.toString().toLowerCase()));
    }

    @Execute(name = "gma", aliases = {"adventure", "gm2"})
    @Permission("nextron.gamemode.adventure")
    void adventure(@Context CommandSender sender, @OptionalArg Player target) {
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§6This command can only be run by a player!");
                return;
            }

            Player player = (Player) (sender);

            player.setGameMode(GameMode.ADVENTURE);
            player.sendMessage(Main.getPrefix() + Text.get("gamemode.success").replace("%g", GameMode.ADVENTURE.toString().toLowerCase()));
            return;
        }

        target.setGameMode(GameMode.ADVENTURE);
        sender.sendMessage(Main.getPrefix() + Text.get("gamemode.other.success").replace("%t", target.getName()).replace("%g", GameMode.ADVENTURE.toString().toLowerCase()));
    }

    @Execute(name = "gamemode", aliases = {"gm"})
    @Permission("nextron.gamemode")
    void gamemodeCommand(@Context CommandSender sender, @Arg GameMode gamemode, @OptionalArg Player target) {
        if (target == null) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§6This command can only be run by a player!");
                return;
            }

            Player player = (Player) (sender);

            setGamemode(player, gamemode);
            player.sendMessage(Main.getPrefix() + Text.get("gamemode.success").replace("%g", gamemode.toString().toLowerCase()));

        } else {
            if (!sender.hasPermission("nextron.gamemode.other")) {
                sender.sendMessage("§cYou do not have permission to change others' gamemode.");
                return;
            }
            setGamemode(target, gamemode);
            sender.sendMessage(Main.getPrefix() + Text.get("gamemode.other.success").replace("%t", target.getName()).replace("%g", gamemode.toString().toLowerCase()));
        }
    }

    private void setGamemode(Player player, GameMode gamemode) {
        if (player.getGameMode().equals(gamemode)) {
            return;
        }
        player.setGameMode(gamemode);
    }
}
