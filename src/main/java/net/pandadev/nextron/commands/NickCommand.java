package net.pandadev.nextron.commands;

import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand extends CommandBase {

    public NickCommand() {
        super("nick", "Set your nickname", "/nick\n/resetnick", "nextron.nick");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }
        Player player = (Player) (sender);

        if (args.length == 1) {
            player.setDisplayName(args[0]);
            Configs.settings.set(player.getUniqueId() + ".nick", args[0]);
            Configs.saveSettingsConfig();
        } else if (args.length == 0 && label.equalsIgnoreCase("resetnick")) {
            player.setDisplayName(player.getName());
            Configs.settings.set(player.getUniqueId() + ".nick", player.getName());
            Configs.saveSettingsConfig();
        } else {
            player.sendMessage(Main.getPrefix() + "§c/nick <name>");
        }

        Main.getInstance().getTablistManager().setAllPlayerTeams();

    }
}