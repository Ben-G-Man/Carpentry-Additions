package io.github.bengman.carpentryadditions.datagen;

import io.github.bengman.carpentryadditions.CarpentryAdditions;
import io.github.bengman.carpentryadditions.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CarpentryAdditions.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        var axeTag = tag(BlockTags.MINEABLE_WITH_AXE);

        ModBlocks.BATTEN_BLOCKS.forEach((type, block) -> {
            axeTag.add(block.get());
        });
    }
}