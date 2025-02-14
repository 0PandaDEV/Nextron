package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.SettingsAPI;
import net.pandadev.nextron.languages.TextAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@Command(name = "tpa")
@Permission("nextron.tpa")
public class TpaCommand extends HelpBase {

    public TpaCommand() {
        super("tpa, Sends a tpa request to a player, /tpa <player>");
    }

    @Execute
    public void tpaCommand(@Context Player player, @Arg Player target) {
        if (target.equals(player)) {
            player.sendMessage(Main.getPrefix() + TextAPI.get("tpa.error"));
            return;
        }
        if (SettingsAPI.allowsTPAs(target)) {
            player.sendMessage(Main.getPrefix() + TextAPI.get("tpa.allow"));
            return;
        }

        Main.tpa.put(target, player);

        target.sendMessage(Main.getPrefix() + TextAPI.get("tpa.target.success.1").replace("%p", player.getName()));

        ///////

        TextComponent component = new TextComponent(TextAPI.get("tpa.target.success.2"));

        TextComponent deny = new TextComponent("§cDeny");
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sädfgsklädfgosergopsmfgb09sej405t2poigms0fb89sew4t23ä2mfg908us-" + target.getName()));
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7Click to deny the tpa from §a" + player.getName())));

        TextComponent accept = new TextComponent("§aAccept");
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sädfgsklädfgosergopsmfgb09sej405t2poigms0fb89sew4t23ä2mfg908u-" + target.getName()));
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7Click to accept the tpa from §a" + player.getName())));

        component.addExtra(deny);
        component.addExtra("§8/");
        component.addExtra(accept);

        ///////

        target.spigot().sendMessage(ChatMessageType.SYSTEM, component);
        target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        player.sendMessage(Main.getPrefix() + TextAPI.get("tpa.sender.success").replace("%t", target.getName()));
    }


}
