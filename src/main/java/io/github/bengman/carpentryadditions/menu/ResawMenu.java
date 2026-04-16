package io.github.bengman.carpentryadditions.menu;

import io.github.bengman.carpentryadditions.blockentity.ResawBlockEntity;
import io.github.bengman.carpentryadditions.registry.ModBlocks;
import io.github.bengman.carpentryadditions.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ResawMenu extends AbstractContainerMenu {

    private final BlockPos pos;
    private final Level level;

    public ResawMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, extraData.readBlockPos());
    }

    public ResawMenu(int containerId, Inventory inventory, BlockPos pos) {
        super(ModMenus.RESAW_MENU.get(), containerId);

        this.pos = pos;
        this.level = inventory.player.level;

        BlockEntity be = level.getBlockEntity(pos);

        if (!(be instanceof ResawBlockEntity resawBE)) {
            throw new IllegalStateException("Missing BlockEntity at Resaw position");
        }

        SimpleContainer container = resawBE.getInventory();

        /* ---- Input ---- */
        this.addSlot(new Slot(container, 0, 26, 36));

        /* ---- Output ---- */
        this.addSlot(new Slot(container, 1, 134, 23));
        this.addSlot(new Slot(container, 2, 134, 49));

        /* ---- Player Inventory ---- */
        int startX = 8;
        int startY = 85;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory,
                        col + row * 9 + 9,
                        startX + col * 18,
                        startY + row * 18));
            }
        }

        /* ---- Hotbar ---- */
        int hotbarY = 143;

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, startX + i * 18, hotbarY));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(ContainerLevelAccess.create(level, pos),
                player,
                ModBlocks.RESAW.get());
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'quickMoveStack'");
    }
}