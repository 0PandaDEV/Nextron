package net.pandadev.nextron.arguments.objects;

import lombok.Getter;

@Getter
public class Language {
    private final String language;

    public Language(String language) {
        this.language = language;
    }

    public String getName() {
        return language;
    }

}
