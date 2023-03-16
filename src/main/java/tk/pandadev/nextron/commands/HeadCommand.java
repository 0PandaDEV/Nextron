package tk.pandadev.nextron.commands;

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
import tk.pandadev.nextron.Main;
import tk.pandadev.nextron.utils.Configs;
import tk.pandadev.nextron.utils.LanguageLoader;

import java.util.ArrayList;
import java.util.List;

public class HeadCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return false;
        }

        Player player = (Player) (sender);

        if (args.length != 1){ player.sendMessage(Main.getPrefix() + "§c/head <player>"); return false; }
            if (!player.hasPermission("essentialsp.head")){ player.sendMessage(Main.getNoPerm()); return false; }

            ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setDisplayName(args[0]);
            meta.setOwner(args[0]);
            item.setItemMeta(meta);
            Inventory inventory = player.getInventory();
            inventory.addItem(item);
            if (Configs.settings.getBoolean(player.getUniqueId() + ".feedback")) player.sendMessage(Main.getPrefix() + LanguageLoader.translationMap.get("head_success").replace("%t", args[0]));

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        if (args.length == 1){
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        }

        return list;
    }
}
