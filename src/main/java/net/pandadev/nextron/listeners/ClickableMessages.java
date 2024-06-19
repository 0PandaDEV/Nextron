package net.pandadev.nextron.listeners;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ClickableMessages implements Listener {

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage();
        if (command.startsWith("/aisdvja4f89dfjvwe4p9r8jdfvjw34r8q0dvj34-")) {
            event.setCancelled(true);

            String home = command.replace("/aisdvja4f89dfjvwe4p9r8jdfvjw34r8q0dvj34-", "");

            Configs.home.set("Homes." + event.getPlayer().getUniqueId() + "." + home, event.getPlayer().getLocation());
            Configs.saveHomeConfig();

            event.getPlayer().sendMessage(Main.getPrefix() + Text.get("home.reset.success").replace("%h", home));
        } else if (command.startsWith("/sädfgsklädfgosergopsmfgb09sej405t2poigms0fb89sew4t23ä2mfg908us-")) {
            event.setCancelled(true);

            Player target = Main.tpa.get(Bukkit.getPlayer(
                    command.replace("/sädfgsklädfgosergopsmfgb09sej405t2poigms0fb89sew4t23ä2mfg908us-", "")));

            if (target == null) {
                event.getPlayer().sendMessage(Main.getPrefix() + Text.get("tpaccept.error"));
                return;
            }

            Main.tpa.remove(event.getPlayer());
            Main.tpa.remove(target);

            event.getPlayer().sendMessage(Main.getPrefix() + Text.get("tpdeny.player").replace("%p", target.getName()));
            target.sendMessage(Main.getPrefix() + Text.get("tpdeny.target").replace("%p", event.getPlayer().getName()));

            target.playSound(target.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
        } else if (command.startsWith("/sädfgsklädfgosergopsmfgb09sej405t2poigms0fb89sew4t23ä2mfg908u-")) {
            event.setCancelled(true);

            Player target = Main.tpa.get(Bukkit
                    .getPlayer(command.replace("/sädfgsklädfgosergopsmfgb09sej405t2poigms0fb89sew4t23ä2mfg908u-", "")));

            if (target == null) {
                event.getPlayer().sendMessage(Main.getPrefix() + Text.get("tpaccept.error"));
                return;
            }

            target.teleport(event.getPlayer().getLocation());

            if (Configs.settings.getBoolean(target.getUniqueId() + ".feedback")) {
                target.sendMessage(Main.getPrefix()
                        + Text.get("tpaccept.player.success").replace("%p", event.getPlayer().getName()));
            }
            if (Configs.settings.getBoolean(event.getPlayer().getUniqueId() + ".feedback")) {
                event.getPlayer().sendMessage(
                        Main.getPrefix() + Text.get("tpaccept.target.success").replace("%t", target.getName()));
            }

            target.playSound(event.getPlayer().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

            Main.tpa.remove(event.getPlayer());
            Main.tpa.remove(target);
        }
    }

}
