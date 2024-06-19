package net.pandadev.nextron.arguments.objects;

import lombok.Getter;

@Getter
public class Home {
    private final String name;

    public Home(String name) {
        this.name = name;
    }

}
