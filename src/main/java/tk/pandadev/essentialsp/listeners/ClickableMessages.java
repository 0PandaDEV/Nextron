package tk.pandadev.essentialsp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import tk.pandadev.essentialsp.Main;
import tk.pandadev.essentialsp.utils.Configs;
import tk.pandadev.essentialsp.utils.LanguageLoader;

public class ClickableMessages implements Listener {

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage();
        if (command.startsWith("/aisdvja4f89dfjvwe4p9r8jdfvjw34r8q0dvj34-")) {
            event.setCancelled(true);

            String home = command.replace("/aisdvja4f89dfjvwe4p9r8jdfvjw34r8q0dvj34-", "");


            Configs.home.set("Homes." + event.getPlayer().getUniqueId() + "." + home, event.getPlayer().getLocation());
            Configs.saveHomeConfig();

            event.getPlayer().sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("home_reset_success").replace("%h", home));
        }
    }

}
