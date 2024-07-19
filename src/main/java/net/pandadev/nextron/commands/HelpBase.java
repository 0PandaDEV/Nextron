package net.pandadev.nextron.commands;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class HelpBase {
    private String command;

    public static HashMap<String, ArrayList<String>> commands = new HashMap<>();

    protected HelpBase(String... commandData) {
        for (String data : commandData) {
            String[] parts = data.split(",", 3);
            if (parts.length < 3)
                continue;

            String command = parts[0].trim();
            String description = parts[1].trim();
            String usage = parts[2].trim();

            ArrayList<String> commandInfo = new ArrayList<>();
            commandInfo.add(usage);
            commandInfo.add(description);
            commands.put(command, commandInfo);
        }
    }

    public String getCommandName() {
        return command;
    }

    public static String getName(String name) {
        return name;
    }

    public static String getDescription(String name) {
        ArrayList<String> localCommandInfo = commands.get(name);
        return localCommandInfo.get(1);
    }

    public static String getUsage(String name) {
        ArrayList<String> localCommandInfo = commands.get(name);
        String usage = localCommandInfo.get(0);
        usage = usage.replace("\\n", "\n");
        return usage;
    }
}