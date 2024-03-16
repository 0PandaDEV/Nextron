package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.pandadev.nextron.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GetPosCommand extends CommandBase implements TabCompleter {

    public GetPosCommand() {
        super("getposition", "Gives you the coordinates of a player", "/getposition <player>\n/getpos <player>", "nextron.getposition");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }
        Player player = (Player) (sender);

        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                Main.getInvalidPlayer();
                return;
            }

            if (player.isOp() && player.hasPermission("nextron.getposition.teleport")) {
                TextComponent teleport = new TextComponent("§2[§aTeleport§2]");
                teleport.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + player.getName() + " " + target.getName()));
                teleport.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Text.get("getpos.hover") + "§a" + target.getName()).create()));

                player.sendMessage(Main.getPrefix() + Text.get("getpos.success").replace("%p", target.getName()) + " X: §a" + Math.floor(target.getLocation().getX()) + "§7 Y: §a" + Math.floor(target.getLocation().getY()) + "§7 Z: §a" + Math.floor(target.getLocation().getZ()));
                player.spigot().sendMessage(ChatMessageType.SYSTEM, teleport);
                return;
            }

            player.sendMessage(Main.getPrefix() + Text.get("getpos.success").replace("%p", target.getName()) + " X: §a" + Math.floor(target.getLocation().getX()) + "§7 Y: §a" + Math.floor(target.getLocation().getY()) + "§7 Z: §a" + Math.floor(target.getLocation().getZ()));
        } else {
            player.sendMessage(Main.getPrefix() + "§c/getposition <player>");
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        if (args.length == 1) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                list.add(onlinePlayer.getName());
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