package io.github.bengman.carpentryadditions.event;

import java.util.HashMap;
import java.util.Map;

import io.github.bengman.carpentryadditions.CarpentryAdditions;
import io.github.bengman.carpentryadditions.registry.ModItems;
import io.github.bengman.carpentryadditions.utils.BattenWoodTypes;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CarpentryAdditions.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModFuelHandler {

    private static final Map<Item, Integer> FUEL_VALUES = new HashMap<>();

    public static void init() {

        ModItems.BATTENS.forEach((wood, item) -> {
            if (!BattenWoodTypes.NON_FLAMMABLE_WOODS.contains(wood)) {
                FUEL_VALUES.put(item.get(), 150);
            }
        });

        ModItems.BATTEN_BLOCK_ITEMS.forEach((wood, item) -> {
            if (!BattenWoodTypes.NON_FLAMMABLE_WOODS.contains(wood)) {
                FUEL_VALUES.put(item.get(), 300);
            }
        });
    }

    @SubscribeEvent
    public static void onFuel(FurnaceFuelBurnTimeEvent event) {
        Item item = event.getItemStack().getItem();

        Integer burn = FUEL_VALUES.get(item);
        if (burn != null) {
            event.setBurnTime(burn);
        }
    }
}