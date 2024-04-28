package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetPosCommand extends HelpBase {

    public GetPosCommand() {
        super("getposition, Gives you the coordinates of a player, /getposition <player>\n/getpos <player>");
    }

    @Command(names = {"getposition", "getpos"}, permission = "nextron.getposition")
    public void getPositionCommand(CommandSender sender, @Param(name = "target") Player target) {
        if (sender instanceof Player) {
            Player player = (Player) (sender);

            if (player.isOp() && player.hasPermission("nextron.getposition.teleport")) {
                TextComponent teleport = new TextComponent("§2[§aTeleport§2]");
                teleport.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + player.getName() + " " + target.getName()));
                teleport.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Text.get("getpos.hover") + "§a" + target.getName()).create()));

                player.sendMessage(Main.getPrefix() + Text.get("getpos.success").replace("%p", target.getName()) + " X: §a" + Math.floor(target.getLocation().getX()) + "§7 Y: §a" + Math.floor(target.getLocation().getY()) + "§7 Z: §a" + Math.floor(target.getLocation().getZ()));
                player.spigot().sendMessage(ChatMessageType.SYSTEM, teleport);
                return;
            }
        }

        sender.sendMessage(Main.getPrefix() + Text.get("getpos.success").replace("%p", target.getName()) + " X: §a" + Math.floor(target.getLocation().getX()) + "§7 Y: §a" + Math.floor(target.getLocation().getY()) + "§7 Z: §a" + Math.floor(target.getLocation().getZ()));
    }

}