package io.github.bengman.carpentryadditions.utils;

import java.util.Set;

public class BattenWoodTypes {

    public static final String[] BATTEN_TYPES = {
            "oak",
            "spruce",
            "birch",
            "jungle",
            "acacia",
            "dark_oak",
            "mangrove",
            "crimson",
            "warped"
    };

    public static final Set<String> NON_FLAMMABLE_WOODS = Set.of(
            "crimson",
            "warped");

    public static final String DEFAULT_BATTEN_TYPE = "oak";
}