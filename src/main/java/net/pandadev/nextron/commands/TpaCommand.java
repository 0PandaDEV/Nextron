package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class TpaCommand extends HelpBase {

    public TpaCommand() {
        super("tpa, Sends a tpa request to a player, /tpa <player>");
    }

    @Command(names = "tpa", permission = "nextron.tpa", playerOnly = true)
    public void tpaCommand(Player player, @Param(name = "target") Player target) {
        if (target.equals(player)) {
            player.sendMessage(Main.getPrefix() + Text.get("tpa.error"));
            return;
        }
        if (!Configs.settings.getBoolean(target.getUniqueId() + ".allowtpas")) {
            player.sendMessage(Main.getPrefix() + Text.get("tpa.allow"));
            return;
        }

        Main.tpa.put(target, player);

        target.sendMessage(Main.getPrefix() + Text.get("tpa.target.success.1").replace("%p", player.getName()));

        ///////

        TextComponent component = new TextComponent(Text.get("tpa.target.success.2"));

        TextComponent deny = new TextComponent("§cDeny");
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sädfgsklädfgosergopsmfgb09sej405t2poigms0fb89sew4t23ä2mfg908us-" + target.getName()));
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to deny the tpa from §a" + player.getName()).create()));

        TextComponent accept = new TextComponent("§aAccept");
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sädfgsklädfgosergopsmfgb09sej405t2poigms0fb89sew4t23ä2mfg908u-" + target.getName()));
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to accept the tpa from §a" + player.getName()).create()));

        component.addExtra(deny);
        component.addExtra("§8/");
        component.addExtra(accept);

        ///////

        target.spigot().sendMessage(ChatMessageType.SYSTEM, component);
        target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        player.sendMessage(Main.getPrefix() + Text.get("tpa.sender.success").replace("%t", target.getName()));
    }


}
