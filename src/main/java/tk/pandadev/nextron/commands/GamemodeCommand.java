package tk.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;

import java.util.ArrayList;
import java.util.List;
public class GamemodeCommand extends CommandBase implements CommandExecutor, TabCompleter {

    public GamemodeCommand() {
        super("gamemode", "Changes the gamemode", "/gamemode <gamemode> [player]",
                "/gm <gamemode> [player]\n/gms\n/gmc\n/gma\n/gmsp", "nextron.gamemode");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            Player player = (Player) (sender);

            if (!player.hasPermission("nextron.gamemode")) {
                player.sendMessage(Main.getNoPerm());
                return;
            }

            GameMode gamemode = null;
            if (label.equalsIgnoreCase("gms")) {
                gamemode = GameMode.SURVIVAL;
            } else if (label.equalsIgnoreCase("gmc")) {
                gamemode = GameMode.CREATIVE;
            } else if (label.equalsIgnoreCase("gma")) {
                gamemode = GameMode.ADVENTURE;
            } else if (label.equalsIgnoreCase("gmsp")) {
                gamemode = GameMode.SPECTATOR;
            } else {
                sender.sendMessage(Main.getPrefix() + Text.get("gamemode.invalid"));
                return;
            }

            if (player.getGameMode().equals(gamemode)) {
                player.sendMessage(
                        Main.getPrefix() + Text.get("gamemode.error").replace("%g", gamemode.toString().toLowerCase()));
                return;
            }

            player.setGameMode(gamemode);
            player.sendMessage(
                    Main.getPrefix() + Text.get("gamemode.success").replace("%g", gamemode.toString().toLowerCase()));
        } else if (args.length == 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            Player player = (Player) (sender);

            if (!player.hasPermission("nextron.gamemode")) {
                player.sendMessage(Main.getNoPerm());
                return;
            }

            GameMode gamemode = null;
            if (args[0].equalsIgnoreCase("0") || label.equalsIgnoreCase("gms")) {
                gamemode = GameMode.SURVIVAL;
            } else if (args[0].equalsIgnoreCase("1") || label.equalsIgnoreCase("gmc")) {
                gamemode = GameMode.CREATIVE;
            } else if (args[0].equalsIgnoreCase("2") || label.equalsIgnoreCase("gma")) {
                gamemode = GameMode.ADVENTURE;
            } else if (args[0].equalsIgnoreCase("3") || label.equalsIgnoreCase("gmsp")) {
                gamemode = GameMode.SPECTATOR;
            } else if (args[0].equalsIgnoreCase("survival")) {
                gamemode = GameMode.SURVIVAL;
            } else if (args[0].equalsIgnoreCase("creative")) {
                gamemode = GameMode.CREATIVE;
            } else if (args[0].equalsIgnoreCase("adventure")) {
                gamemode = GameMode.ADVENTURE;
            } else if (args[0].equalsIgnoreCase("spectator")) {
                gamemode = GameMode.SPECTATOR;
            } else if (args[0].equalsIgnoreCase("s")) {
                gamemode = GameMode.SURVIVAL;
            } else if (args[0].equalsIgnoreCase("c")) {
                gamemode = GameMode.CREATIVE;
            } else if (args[0].equalsIgnoreCase("a")) {
                gamemode = GameMode.ADVENTURE;
            } else if (args[0].equalsIgnoreCase("sp")) {
                gamemode = GameMode.SPECTATOR;
            } else {
                sender.sendMessage(Main.getPrefix() + Text.get("gamemode.invalid"));
                return;
            }

            if (player.getGameMode().equals(gamemode)) {
                player.sendMessage(
                        Main.getPrefix() + Text.get("gamemode.error").replace("%g", gamemode.toString().toLowerCase()));
                return;
            }

            player.setGameMode(gamemode);
            player.sendMessage(
                    Main.getPrefix() + Text.get("gamemode.success").replace("%g", gamemode.toString().toLowerCase()));
        } else if (args.length == 2) {
            if (!sender.hasPermission("nextron.gamemode.other")) {
                sender.sendMessage(Main.getNoPerm());
                return;
            }

            Player target = Bukkit.getPlayer(args[1]);
            GameMode gamemode = null;
            if (args[0].equalsIgnoreCase("0")) {
                gamemode = GameMode.SURVIVAL;
            } else if (args[0].equalsIgnoreCase("1")) {
                gamemode = GameMode.CREATIVE;
            } else if (args[0].equalsIgnoreCase("2")) {
                gamemode = GameMode.ADVENTURE;
            } else if (args[0].equalsIgnoreCase("3")) {
                gamemode = GameMode.SPECTATOR;
            } else if (args[0].equalsIgnoreCase("survival")) {
                gamemode = GameMode.SURVIVAL;
            } else if (args[0].equalsIgnoreCase("creative")) {
                gamemode = GameMode.CREATIVE;
            } else if (args[0].equalsIgnoreCase("adventure")) {
                gamemode = GameMode.ADVENTURE;
            } else if (args[0].equalsIgnoreCase("spectator")) {
                gamemode = GameMode.SPECTATOR;
            } else if (args[0].equalsIgnoreCase("s")) {
                gamemode = GameMode.SURVIVAL;
            } else if (args[0].equalsIgnoreCase("c")) {
                gamemode = GameMode.CREATIVE;
            } else if (args[0].equalsIgnoreCase("a")) {
                gamemode = GameMode.ADVENTURE;
            } else if (args[0].equalsIgnoreCase("sp")) {
                gamemode = GameMode.SPECTATOR;
            } else {
                System.out.println("asdad");
                sender.sendMessage(Main.getPrefix() + Text.get("gamemode.invalid"));
                return;
            }

            if (target.getGameMode().equals(gamemode)) {
                sender.sendMessage(Main.getPrefix() + Text.get("gamemode.other.error").replace("%t", target.getName())
                        .replace("%g", gamemode.toString().toLowerCase()));
                return;
            }

            target.setGameMode(gamemode);
            sender.sendMessage(Main.getPrefix() + Text.get("gamemode.other.success").replace("%t", target.getName())
                    .replace("%g", gamemode.toString().toLowerCase()));
        } else {
            sender.sendMessage(Main.getPrefix() + "§c/gamemode <mode> [player]");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player playert = (Player) (sender);
        ArrayList<String> list = new ArrayList<String>();
        if (args.length == 1 && playert.hasPermission("nextron.gamemode")) {
            list.add("0");
            list.add("1");
            list.add("2");
            list.add("3");
            list.add("survival");
            list.add("creative");
            list.add("adventure");
            list.add("spectator");
            list.add("s");
            list.add("c");
            list.add("a");
            list.add("sp");
        } else if (args.length == 2 && playert.hasPermission("nextron.gamemode.other")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        }

        ArrayList<String> completerList = new ArrayList<String>();
        String currentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (!s1.startsWith(currentarg))
                continue;
            completerList.add(s);
        }

        return completerList;
    }
}
