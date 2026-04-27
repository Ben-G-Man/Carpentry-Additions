package io.github.bengman.carpentryadditions.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class InventoryMenu<T extends BlockEntity> extends AbstractContainerMenu {

    public static final int PLAYER_INV_START = 0;
    public static final int PLAYER_INV_END = 36;

    protected final BlockPos pos;
    protected final Level level;
    protected final T blockEntity;

    protected InventoryMenu(MenuType<?> type,
            int containerId,
            Inventory inventory,
            BlockPos pos,
            Class<T> clazz) {

        super(type, containerId);

        this.pos = pos;
        this.level = inventory.player.level;

        BlockEntity be = level.getBlockEntity(pos);

        if (!clazz.isInstance(be)) {
            throw new IllegalStateException("Wrong block entity at " + pos);
        }

        this.blockEntity = clazz.cast(be);
    }

    protected void addDefaultPlayerInventory(Inventory inv) {
        addPlayerInventory(inv, 8, 85);
    }

    protected void addPlayerInventory(Inventory inv, int startX, int startY) {

        // main inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv,
                        col + row * 9 + 9,
                        startX + col * 18,
                        startY + row * 18));
            }
        }

        // hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inv,
                    col,
                    startX + col * 18,
                    startY + 58));
        }
    }

    public T getBlockEntity() {
        return blockEntity;
    }

    public BlockPos getBlockPos() {
        return pos;
    }

    @Override
    public boolean stillValid(net.minecraft.world.entity.player.Player player) {
        return player.level.getBlockEntity(pos) == blockEntity &&
                player.distanceToSqr(
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5) < 64.0;
    }
}