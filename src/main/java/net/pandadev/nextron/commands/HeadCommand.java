package net.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import net.pandadev.nextron.Main;
import net.pandadev.nextron.utils.Configs;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class HeadCommand extends CommandBase implements CommandExecutor, TabCompleter {

    public HeadCommand() {
        super("head", "Gives you the head of any player", "/head <player>", "", "nextron.head");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }

        Player player = (Player) (sender);

        if (args.length != 1) {
            player.sendMessage(Main.getPrefix() + "§c/head <player>");
            return;
        }
        if (!player.hasPermission("nextron.head")) {
            player.sendMessage(Main.getNoPerm());
            return;
        }

        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setDisplayName(args[0]);
        meta.setOwner(args[0]);
        item.setItemMeta(meta);
        Inventory inventory = player.getInventory();
        inventory.addItem(item);
        if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback"))
            player.sendMessage(Main.getPrefix() + Text.get("head.success").replace("%t", args[0]));
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
            if (!s1.startsWith(currentarg))
                continue;
            completerList.add(s);
        }

        return completerList;
    }
}
