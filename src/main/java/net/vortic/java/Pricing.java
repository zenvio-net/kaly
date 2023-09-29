package net.vortic.java;

import net.vortic.java.types.Tuple;

public class Pricing {

    public static Tuple<Integer, Integer> getDiscordBotPricing(String packageId) {
        return switch (packageId) {
            case "basic" -> new Tuple<>(500, 250);
            case "starter" -> new Tuple<>(800, 500);
            case "advanced" -> new Tuple<>(1200, 750);
            default -> null;
        };
    }

}
