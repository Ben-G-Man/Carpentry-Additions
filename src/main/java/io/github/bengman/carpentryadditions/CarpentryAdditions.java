package io.github.bengman.carpentryadditions;

import net.minecraft.world.item.BlockItem;

// import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import io.github.bengman.carpentryadditions.event.ModFuelHandler;
import io.github.bengman.carpentryadditions.registry.ModBlockEntities;
import io.github.bengman.carpentryadditions.registry.ModBlocks;
import io.github.bengman.carpentryadditions.registry.ModItems;
import io.github.bengman.carpentryadditions.registry.ModMenus;

@Mod(CarpentryAdditions.MODID)
public class CarpentryAdditions {

    public static final String MODID = "carpentryadditions";

    /* ---- Register BLOCKS, ITEMS, etc. with the event bus ---- */

    public CarpentryAdditions(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModMenus.MENUS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModFuelHandler::init);
    }

    /* Adding items to creative inventory */
    private void addCreative(CreativeModeTabEvent.BuildContents event) {

        if (event.getTab() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModItems.RESAW_ITEM);
            event.accept(ModItems.CHIP_BIN_ITEM);
        }

        if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
            for (RegistryObject<BlockItem> battenBlockRegObj : ModItems.BATTEN_BLOCK_ITEMS.values()) {
                event.accept(battenBlockRegObj.get());
            }
        }

        if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
            for (RegistryObject<Item> battenRegObj : ModItems.BATTENS.values()) {
                event.accept(battenRegObj.get());
            }

            event.accept(ModItems.WOOD_CHIPS.get());
        }
    }
}
