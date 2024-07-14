package net.pandadev.nextron.arguments.objects;

import lombok.Getter;

@Getter
public class Help {
    private final String command;
    private final String name;
    private final String description;
    private final String usage;

    public Help(String command, String name, String description, String usage) {
        this.command = command;
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

}