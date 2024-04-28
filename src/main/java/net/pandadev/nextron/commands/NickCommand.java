package net.pandadev.nextron.commands;

import net.pandadev.nextron.utils.Configs;
import net.pandadev.nextron.utils.commandapi.Command;
import net.pandadev.nextron.utils.commandapi.paramter.Param;
import org.bukkit.entity.Player;

public class NickCommand extends HelpBase {

    public NickCommand() {
        super("nick, Set your nickname, /nick <nickname>",
                "resetnick, Reset your nickname, /resetnick");
    }

    @Command(names = {"nick"}, permission = "nextron.nick")
    public void nickCommand(Player player, @Param(name = "nickname") String nick) {
        player.setDisplayName(nick);
        Configs.settings.set(player.getUniqueId() + ".nick", nick);
        Configs.saveSettingsConfig();
    }

    @Command(names = {"resetnick"}, permission = "nextron.resetnick")
    public void resetnickCommand(Player player) {
        player.setDisplayName(player.getName());
        Configs.settings.set(player.getUniqueId() + ".nick", player.getName());
        Configs.saveSettingsConfig();
    }
}