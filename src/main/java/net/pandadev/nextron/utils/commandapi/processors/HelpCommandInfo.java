package net.pandadev.nextron.utils.commandapi.processors;

import lombok.Getter;

@Getter
public class HelpCommandInfo {
    private final String command;
    private final String name;
    private final String description;
    private final String usage;

    public HelpCommandInfo(String command, String name, String description, String usage) {
        this.command = command;
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

}