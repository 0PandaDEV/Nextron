package net.pandadev.nextron.listeners;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.apis.HomeAPI;
import net.pandadev.nextron.apis.SettingsAPI;
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

            // executed when clicked on RESET to a home reset

            event.setCancelled(true);

            String home = command.replace("/aisdvja4f89dfjvwe4p9r8jdfvjw34r8q0dvj34-", "");

            HomeAPI.setHome(event.getPlayer(), home, event.getPlayer().getLocation());

            event.getPlayer().sendMessage(Main.getPrefix() + Text.get("home.reset.success").replace("%h", home));
        } else if (command.startsWith("/sädfgsklädfgosergopsmfgb09sej405t2poigms0fb89sew4t23ä2mfg908us-")) {

            // executed when clicked on DENY to a tpa

            event.setCancelled(true);

            Player target = Main.tpa.get(Bukkit.getPlayer(command.replace("/sädfgsklädfgosergopsmfgb09sej405t2poigms0fb89sew4t23ä2mfg908us-", "")));

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

            // executed when clicked on ACCEPT to a tpa

            event.setCancelled(true);

            Player target = Main.tpa.get(Bukkit.getPlayer(command.replace("/sädfgsklädfgosergopsmfgb09sej405t2poigms0fb89sew4t23ä2mfg908u-", "")));

            if (target == null) {
                event.getPlayer().sendMessage(Main.getPrefix() + Text.get("tpaccept.error"));
                return;
            }

            target.teleport(event.getPlayer().getLocation());

            if (SettingsAPI.allowsFeedback(target)) {
                target.sendMessage(Main.getPrefix() + Text.get("tpaccept.player.success").replace("%p", event.getPlayer().getName()));
            }
            if (SettingsAPI.allowsFeedback(event.getPlayer())) {
                event.getPlayer().sendMessage(Main.getPrefix() + Text.get("tpaccept.target.success").replace("%t", target.getName()));
            }

            target.playSound(event.getPlayer().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

            Main.tpa.remove(event.getPlayer());
            Main.tpa.remove(target);
        } else if (command.startsWith("/487rt6vw9847tv6n293847tv6239487rtvb9we8r6s897rtv6bh9a87evb6-")) {

            // executed when clicked on DENY to a tpahere

            event.setCancelled(true);

            Player target = Main.tpahere.get(Bukkit.getPlayer(command.replace("/487rt6vw9847tv6n293847tv6239487rtvb9we8r6s897rtv6bh9a87evb6-", "")));

            if (target == null) {
                event.getPlayer().sendMessage(Main.getPrefix() + Text.get("tpaccept.error"));
                return;
            }

            Main.tpahere.remove(event.getPlayer());
            Main.tpahere.remove(target);

            event.getPlayer().sendMessage(Main.getPrefix() + Text.get("tpdenyhere.player").replace("%p", target.getName()));
            target.sendMessage(Main.getPrefix() + Text.get("tpdenyhere.target").replace("%p", event.getPlayer().getName()));

            target.playSound(target.getLocation(), Sound.ENTITY_PILLAGER_AMBIENT, 100, 0.5f);
        } else if (command.startsWith("/890w45tvb907n845tbn890w35v907n8w34v907n8234v7n890w34b-")) {

            // executed when clicked on ACCEPT to a tpahere

            event.setCancelled(true);

            Player target = Main.tpahere.get(Bukkit.getPlayer(command.replace("/890w45tvb907n845tbn890w35v907n8w34v907n8234v7n890w34b-", "")));

            if (target == null) {
                event.getPlayer().sendMessage(Main.getPrefix() + Text.get("tpaccept.error"));
                return;
            }

            event.getPlayer().teleport(target.getLocation());

            if (SettingsAPI.allowsFeedback(target)) {
                target.sendMessage(Main.getPrefix() + Text.get("tpaccept.target.success").replace("%t", event.getPlayer().getName()));
            }
            if (SettingsAPI.allowsFeedback(event.getPlayer())) {
                event.getPlayer().sendMessage(Main.getPrefix() + Text.get("tpaccept.player.success").replace("%p", target.getName()));
            }

            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

            Main.tpahere.remove(event.getPlayer());
            Main.tpahere.remove(target);
        }
    }

}
