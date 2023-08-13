package tk.pandadev.nextron.commands;

import ch.hekates.languify.language.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tk.pandadev.nextron.Main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LanguageCommand extends CommandBase implements TabCompleter {

    public LanguageCommand() {
        super("language", "Allows you to change the plugins language", "/language <lanugage>", "", "nextron.language");
    }

    @Override
    protected void execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getCommandInstance());
            return;
        }
        Player player = (Player) (sender);

        if (args.length == 1){
            List<File> dataFolder = Arrays.stream(new File(Main.getInstance().getDataFolder() + "/lang").listFiles()).toList();
            List<String> languages = new ArrayList<>();
            for (File lanugage : dataFolder){
                languages.add(lanugage.getName().replace(".json", ""));
            }
            if (!languages.contains(args[0])) {player.sendMessage(Main.getPrefix() + Text.get("language.set.error").replace("%l", languages.toString().replace("[", "").replace("]", ""))); return;}

            Main.getInstance().getConfig().set("language", args[0]);
            Main.getInstance().saveConfig();
            player.sendMessage(Main.getPrefix() + Text.get("language.set.success").replace("%l", args[0]));
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        Player playert = (Player) (sender);

        if (args.length == 1){
            File dataFolder = new File(Main.getInstance().getDataFolder() + "/lang");
            for (File language : Arrays.stream(dataFolder.listFiles()).toList()){
                list.add(language.getName().replace(".json", ""));
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