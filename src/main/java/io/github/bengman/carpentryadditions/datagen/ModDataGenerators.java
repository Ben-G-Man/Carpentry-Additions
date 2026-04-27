package io.github.bengman.carpentryadditions.datagen;

import io.github.bengman.carpentryadditions.CarpentryAdditions;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CarpentryAdditions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();

        /* Batten Recipes Data Gen */
        generator.addProvider(
                event.includeServer(),
                new ModRecipeProvider(event.getGenerator().getPackOutput()));

        /* Tags (E.g., axe efficiency) Data Gen */
        generator.addProvider(
                event.includeServer(),
                new ModBlockTagsProvider(output, lookupProvider, existingFileHelper));

        /* Loot Table Data Gen */
        generator.addProvider(event.includeServer(),
                new ModLootTableProvider(output));

        /* EN-US Labels for Generated Items */
        generator.addProvider(event.includeClient(),
                new ModEnLangProvider(output, CarpentryAdditions.MODID, "en_us"));

        /* Block Models and BlockStates */
        event.getGenerator().addProvider(event.includeClient(),
                new ModBlockStateProvider(output, existingFileHelper));

        /* Item Models */
        event.getGenerator().addProvider(event.includeClient(),
                new ModItemModelProvider(output, existingFileHelper));
    }
}