package io.github.bengman.carpentryadditions.registry;

import java.util.HashMap;
import java.util.Map;

import io.github.bengman.carpentryadditions.CarpentryAdditions;
import io.github.bengman.carpentryadditions.utils.BattenWoodTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            CarpentryAdditions.MODID);

    public static final RegistryObject<Item> RESAW_ITEM = ITEMS.register("resaw",
            () -> new BlockItem(ModBlocks.RESAW.get(), new Item.Properties()));

    public static final RegistryObject<Item> CHIP_BIN_ITEM = ITEMS.register("chip_bin",
            () -> new BlockItem(ModBlocks.CHIP_BIN.get(), new Item.Properties()));

    public static final RegistryObject<Item> WOOD_CHIPS = ITEMS.register("wood_chips",
            () -> new Item(new Item.Properties()));

    /* ---- Auto-Gen Battens ---- */

    public static final Map<String, RegistryObject<Item>> BATTENS = new HashMap<>();
    public static final Map<String, RegistryObject<BlockItem>> BATTEN_BLOCK_ITEMS = new HashMap<>();

    static {
        for (String woodType : BattenWoodTypes.BATTEN_TYPES) {
            BATTENS.put(woodType, createBatten(woodType));
            BATTEN_BLOCK_ITEMS.put(woodType, createBattenBlockItem(woodType));
        }
    }

    private static RegistryObject<Item> createBatten(String woodType) {
        return ITEMS.register(woodType + "_batten",
                () -> new Item(new Item.Properties()));
    }

    private static RegistryObject<BlockItem> createBattenBlockItem(String woodType) {
        return ITEMS.register(woodType + "_batten_block",
                () -> new BlockItem(ModBlocks.BATTEN_BLOCKS.get(woodType).get(), new Item.Properties()));
    }

    /* ---- Default Getters ---- */

    public static RegistryObject<Item> getDefaultBatten() {
        return BATTENS.get(BattenWoodTypes.DEFAULT_BATTEN_TYPE);
    }

    public static RegistryObject<BlockItem> getDefaultBattenBlockItem() {
        return BATTEN_BLOCK_ITEMS.get(BattenWoodTypes.DEFAULT_BATTEN_TYPE);
    }
}
