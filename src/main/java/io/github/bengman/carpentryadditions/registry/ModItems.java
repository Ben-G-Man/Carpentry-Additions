package io.github.bengman.carpentryadditions.registry;

import io.github.bengman.carpentryadditions.CarpentryAdditions;
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

    public static final RegistryObject<Item> OAK_BATTEN_BLOCK_ITEM = ITEMS.register("oak_batten_block",
            () -> new BlockItem(ModBlocks.OAK_BATTEN_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> OAK_BATTEN = ITEMS.register("oak_batten",
            () -> new Item(new Item.Properties()));
}
