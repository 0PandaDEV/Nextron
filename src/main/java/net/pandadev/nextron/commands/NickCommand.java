package net.pandadev.nextron.commands;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.pandadev.nextron.apis.SettingsAPI;
import org.bukkit.entity.Player;

@RootCommand
public class NickCommand extends HelpBase {

    public NickCommand() {
        super("nick, Set your nickname, /nick <nickname>",
                "resetnick, Reset your nickname, /resetnick");
    }

    @Execute(name = "nick")
    @Permission("nextron.nick")
    public void nickCommand(@Context Player player, @Arg String nick) {
        player.setDisplayName(nick);
        SettingsAPI.setNick(player, nick);
    }

    @Execute(name = "resetnick")
    @Permission("nextron.resetnick")
    public void resetnickCommand(@Context Player player) {
        player.setDisplayName(player.getName());
        SettingsAPI.setNick(player, player.getName());
    }
}