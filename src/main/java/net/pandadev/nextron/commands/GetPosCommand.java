package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "getposition", aliases = {"getpos"})
@Permission("nextron.getposition")
public class GetPosCommand extends HelpBase {

    public GetPosCommand() {
        super("getposition, Gives you the coordinates of a player, /getposition <player>\n/getpos <player>");
    }

    @Execute
    public void getPositionCommand(@Context CommandSender sender, @Arg Player target) {
        if (sender instanceof Player) {
            Player player = (Player) (sender);

            if (player.isOp() && player.hasPermission("nextron.getposition.teleport")) {
                TextComponent teleport = new TextComponent("§2[§aTeleport§2]");
                teleport.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + player.getName() + " " + target.getName()));
                teleport.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextAPI.get("getpos.hover") + "§a" + target.getName()).create()));

                player.sendMessage(Main.getPrefix() + TextAPI.get("getpos.success").replace("%p", target.getName()) + " X: §a" + Math.floor(target.getLocation().getX()) + "§7 Y: §a" + Math.floor(target.getLocation().getY()) + "§7 Z: §a" + Math.floor(target.getLocation().getZ()));
                player.spigot().sendMessage(ChatMessageType.SYSTEM, teleport);
                return;
            }
        }

        sender.sendMessage(Main.getPrefix() + TextAPI.get("getpos.success").replace("%p", target.getName()) + " X: §a" + Math.floor(target.getLocation().getX()) + "§7 Y: §a" + Math.floor(target.getLocation().getY()) + "§7 Z: §a" + Math.floor(target.getLocation().getZ()));
    }

}