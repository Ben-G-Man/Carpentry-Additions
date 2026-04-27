package io.github.bengman.carpentryadditions.registry;

import io.github.bengman.carpentryadditions.CarpentryAdditions;
import io.github.bengman.carpentryadditions.screen.ChipBinScreen;
import io.github.bengman.carpentryadditions.screen.ResawScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CarpentryAdditions.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModScreens {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenus.RESAW_MENU.get(), ResawScreen::new);
            MenuScreens.register(ModMenus.CHIP_BIN_MENU.get(), ChipBinScreen::new);
        });
    }
}