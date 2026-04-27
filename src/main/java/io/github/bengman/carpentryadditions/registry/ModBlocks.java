package io.github.bengman.carpentryadditions.registry;

import java.util.HashMap;
import java.util.Map;

import io.github.bengman.carpentryadditions.CarpentryAdditions;
import io.github.bengman.carpentryadditions.block.ChipBinBlock;
import io.github.bengman.carpentryadditions.block.ResawBlock;
import io.github.bengman.carpentryadditions.utils.BattenWoodTypes;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.material.Material;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            CarpentryAdditions.MODID);

    public static final RegistryObject<Block> RESAW = BLOCKS.register("resaw",
            () -> new ResawBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.5f).noOcclusion()));

    public static final RegistryObject<Block> CHIP_BIN = BLOCKS.register("chip_bin",
            () -> new ChipBinBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5f).noOcclusion()));

    /* ---- Auto-Gen Batten Blocks ---- */

    public static final Map<String, RegistryObject<Block>> BATTEN_BLOCKS = new HashMap<>();

    static {
        for (String woodType : BattenWoodTypes.BATTEN_TYPES) {
            BATTEN_BLOCKS.put(woodType, createBattenBlock(woodType));
        }
    }

    private static RegistryObject<Block> createBattenBlock(String woodType) {
        return BLOCKS.register(woodType + "_batten_block",
                () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f)));
    }

    public static RegistryObject<Block> getDefaultBattenBlock() {
        return BATTEN_BLOCKS.get(BattenWoodTypes.DEFAULT_BATTEN_TYPE);
    }
}