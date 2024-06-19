package net.pandadev.nextron.arguments.objects;

import lombok.Getter;

@Getter
public class Seed {
    // Getter
    private final long seed;

    public Seed(long seed) {
        this.seed = seed;
    }

}
