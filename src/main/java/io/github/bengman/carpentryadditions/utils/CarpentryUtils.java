package io.github.bengman.carpentryadditions.utils;

import io.github.bengman.carpentryadditions.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CarpentryUtils {
    public static String getWoodType(ItemStack stack) {

        if (!stack.is(net.minecraft.tags.ItemTags.PLANKS)) {
            return null;
        }

        ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        String path = id.getPath();

        if (path.endsWith("_planks")) {
            return path.substring(0, path.length() - "_planks".length());
        }

        return null;
    }
}
