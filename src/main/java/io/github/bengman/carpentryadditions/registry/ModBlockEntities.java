package io.github.bengman.carpentryadditions.registry;

import io.github.bengman.carpentryadditions.CarpentryAdditions;
import io.github.bengman.carpentryadditions.blockentity.ChipBinBlockEntity;
import io.github.bengman.carpentryadditions.blockentity.ResawBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITY_TYPES, CarpentryAdditions.MODID);

    public static final RegistryObject<BlockEntityType<ResawBlockEntity>> RESAW_BE = BLOCK_ENTITIES.register("resaw",
            () -> BlockEntityType.Builder.of(
                    ResawBlockEntity::new,
                    ModBlocks.RESAW.get()).build(null));

    public static final RegistryObject<BlockEntityType<ChipBinBlockEntity>> CHIP_BIN_BE = BLOCK_ENTITIES.register(
            "chip_bin",
            () -> BlockEntityType.Builder.of(
                    ChipBinBlockEntity::new,
                    ModBlocks.CHIP_BIN.get()).build(null));
}