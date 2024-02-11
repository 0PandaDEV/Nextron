package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
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

public class HealCommand extends CommandBase implements CommandExecutor, TabCompleter {

    public HealCommand() {
        super("heal", "Fills up your hunger and hearts", "/heal [player]", "", "nextron.heal");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getCommandInstance());
                return;
            }

            Player player = (Player) (sender);

            if (!player.hasPermission("nextron.heal")) {
                player.sendMessage(Main.getNoPerm());
                return;
            }

            if (player.getHealth() != player.getMaxHealth() || player.getFoodLevel() != 20) {
                player.setHealth(player.getMaxHealth());
                player.setFoodLevel(20);
                if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) {
                    player.sendMessage(Main.getPrefix() + Text.get("heal.success"));
                }
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                return;
            }
            player.sendMessage(Main.getPrefix() + Text.get("heal.error"));

        } else if (args.length == 1) {

            if (!sender.hasPermission("nextron.heal.other")) {
                sender.sendMessage(Main.getNoPerm());
                return;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Main.getInvalidPlayer());
                return;
            }

            if (target.getHealth() != 20.0 || target.getFoodLevel() != 20) {
                target.setHealth(20.0);
                target.setFoodLevel(20);
                target.sendMessage(Main.getPrefix() + Text.get("heal.other.success").replace("%p",
                        sender instanceof Player ? sender.getName() : "Console"));
                target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                return;
            }
            sender.sendMessage(Main.getPrefix() + Text.get("heal.other.error").replace("%t", target.getName()));

        } else {
            sender.sendMessage(Main.getPrefix() + "§c/heal [player]");
        }
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        if (args.length == 1 && sender.hasPermission("nextron.heal.other")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        }

        ArrayList<String> completerList = new ArrayList<String>();
        String currentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (!s1.startsWith(currentarg))
                continue;
            completerList.add(s);
        }

        return completerList;
    }
}