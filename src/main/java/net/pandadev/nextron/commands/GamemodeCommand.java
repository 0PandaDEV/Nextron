package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

    @Command(names = {"gmc", "creative", "gm1"}, permission = "nextron.gamemode.creative")
    public void creative(CommandSender sender, @Param(name = "target", required = false) Player target) {
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

    @Command(names = {"gms", "survival", "gm0"}, permission = "nextron.gamemode.survival")
    public void survival(CommandSender sender, @Param(name = "target", required = false) Player target) {
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

    @Command(names = {"gmsp", "spectator", "gm3"}, permission = "nextron.gamemode.spectator")
    public void spectator(CommandSender sender, @Param(name = "target", required = false) Player target) {
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

    @Command(names = {"gma", "adventure", "gm2"}, permission = "nextron.gamemode.adventure")
    public void adventure(CommandSender sender, @Param(name = "target", required = false) Player target) {
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

    @Command(names = {"gamemode", "gm"}, permission = "nextron.gamemode")
    public void gamemodeCommand(CommandSender sender, @Param(name = "gamemode") GameMode gamemode, @Param(name = "target", required = false) Player target) {
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
