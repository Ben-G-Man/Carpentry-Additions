package io.github.bengman.carpentryadditions;

// import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import io.github.bengman.carpentryadditions.registry.ModBlockEntities;
import io.github.bengman.carpentryadditions.registry.ModBlocks;
import io.github.bengman.carpentryadditions.registry.ModItems;
import io.github.bengman.carpentryadditions.registry.ModMenus;
// import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CarpentryAdditions.MODID)
public class CarpentryAdditions {

    public static final String MODID = "carpentryadditions";

    // private static final Logger LOGGER = LogUtils.getLogger();

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
        /* Currently no common setup code */
    }

    /* Adding items to creative inventory */
    private void addCreative(CreativeModeTabEvent.BuildContents event) {

        if (event.getTab() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModItems.RESAW_ITEM);
        }

        if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModItems.OAK_BATTON_BLOCK_ITEM);
        }
    }
}
