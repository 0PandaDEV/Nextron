package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TpaCommand extends CommandBase implements CommandExecutor, TabCompleter {

    public TpaCommand() {
        super("tpa", "Sends a tpa request to a player", "/tpa <player>", "nextron.tpa");
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
                player.sendMessage(Main.getInvalidPlayer());
                return;
            }
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
            deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to reset the position for §a" + args[0].toLowerCase()).create()));

            TextComponent accept = new TextComponent("§aAccept");
            accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sädfgsklädfgosergopsmfgb09sej405t2poigms0fb89sew4t23ä2mfg908u-" + target.getName()));
            accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to the tpa request from §a" + player.getName()).create()));

            component.addExtra(deny);
            component.addExtra("§8/");
            component.addExtra(accept);

            ///////

            target.spigot().sendMessage(ChatMessageType.SYSTEM, component);
            target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            player.sendMessage(Main.getPrefix() + Text.get("tpa.sender.success").replace("%t", target.getName()));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        if (args.length == 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
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
