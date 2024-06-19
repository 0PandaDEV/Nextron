package net.pandadev.nextron.arguments.objects;

import lombok.Getter;

@Getter
public class Feature {
    private final String name;

    public Feature(String name) {
        this.name = name;
    }

}
