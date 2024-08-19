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
import net.pandadev.nextron.apis.SettingsAPI;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@Command(name = "tpahere")
@Permission("nextron.tpahere")
public class TpahereCommand extends HelpBase {

    public TpahereCommand() {
        super("tpahere, Sends a tpa request to a player, /tpahere <player>");
    }

    @Execute
    void tpahereCommand(@Context Player player, @Arg Player target) {
        if (target.equals(player)) {
            player.sendMessage(Main.getPrefix() + TextAPI.get("tpahere.error"));
            return;
        }
        if (SettingsAPI.allowsTPAs(target)) {
            player.sendMessage(Main.getPrefix() + TextAPI.get("tpahere.allow"));
            return;
        }

        Main.tpahere.put(target, player);

        target.sendMessage(Main.getPrefix() + TextAPI.get("tpahere.target.success.1").replace("%p", player.getName()));

        /////////////////

        TextComponent component = new TextComponent(TextAPI.get("tpahere.target.success.2"));

        TextComponent deny = new TextComponent("§cDeny");
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/487rt6vw9847tv6n293847tv6239487rtvb9we8r6s897rtv6bh9a87evb6-" + target.getName()));
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to deny the tpahere request for §a" + player.getName()).create()));

        TextComponent accept = new TextComponent("§aAccept");
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/890w45tvb907n845tbn890w35v907n8w34v907n8234v7n890w34b-" + target.getName()));
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to accept the tpahere request from §a" + player.getName()).create()));

        component.addExtra(deny);
        component.addExtra("§8/");
        component.addExtra(accept);

        /////////////////

        target.spigot().sendMessage(ChatMessageType.SYSTEM, component);
        target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        player.sendMessage(Main.getPrefix() + TextAPI.get("tpahere.sender.success").replace("%t", target.getName()));
    }
}