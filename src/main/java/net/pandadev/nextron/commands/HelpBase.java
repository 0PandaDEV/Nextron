package net.pandadev.nextron.commands;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class HelpBase {
    private final String command;

    public static HashMap<String, ArrayList<String>> commands = new HashMap<>();

    protected HelpBase(String command, String description, String usage) {
        this.command = command;
        ArrayList<String> commandInfo = new ArrayList();
        commandInfo.add(0, usage);
        commandInfo.add(1, description);
        commands.put(command, commandInfo);
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