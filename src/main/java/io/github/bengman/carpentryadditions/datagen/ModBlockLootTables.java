package io.github.bengman.carpentryadditions.datagen;

import io.github.bengman.carpentryadditions.registry.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        ModBlocks.BATTEN_BLOCKS.forEach((type, block) -> {
            dropSelf(block.get());
        });
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BATTEN_BLOCKS.values().stream()
                .map(reg -> reg.get())
                .toList();
    }
}