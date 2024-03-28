package net.pandadev.nextron.utils.commandapi.processors;

import lombok.Getter;

@Getter
public class HomeInfo {
    private final String name;

    public HomeInfo(String name) {
        this.name = name;
    }

}
