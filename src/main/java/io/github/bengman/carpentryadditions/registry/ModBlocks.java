package io.github.bengman.carpentryadditions.registry;

import io.github.bengman.carpentryadditions.CarpentryAdditions;
import io.github.bengman.carpentryadditions.block.ResawBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            CarpentryAdditions.MODID);

    public static final RegistryObject<Block> RESAW = BLOCKS.register("resaw",
            () -> new ResawBlock(BlockBehaviour.Properties.of(Material.METAL).strength(1.0f).noOcclusion()));

    public static final RegistryObject<Block> OAK_BATTEN_BLOCK = BLOCKS.register("oak_batten_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(0.4f)));

}
