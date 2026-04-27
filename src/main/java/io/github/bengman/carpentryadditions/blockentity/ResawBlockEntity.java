package io.github.bengman.carpentryadditions.blockentity;

import io.github.bengman.carpentryadditions.menu.ResawMenu;
import io.github.bengman.carpentryadditions.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;

public class ResawBlockEntity extends BlockEntity implements MenuProvider {

    public ResawBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RESAW_BE.get(), pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Resaw");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new ResawMenu(containerId, playerInventory, this.worldPosition);
    }

    public ChipBinBlockEntity getChipBin() {
        if (level == null) {
            return null;
        }

        BlockPos belowPos = worldPosition.below();
        BlockEntity be = level.getBlockEntity(belowPos);

        if (be instanceof ChipBinBlockEntity chipBin) {
            return chipBin;
        }

        return null;
    }

    public boolean hasChipBin() {
        return getChipBin() != null;
    }
}