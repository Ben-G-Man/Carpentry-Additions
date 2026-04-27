package io.github.bengman.carpentryadditions.menu;

import io.github.bengman.carpentryadditions.blockentity.ChipBinBlockEntity;
import io.github.bengman.carpentryadditions.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ChipBinMenu extends InventoryMenu<ChipBinBlockEntity> {

    public static final int DEPOSIT_ROWS = 2;
    public static final int DEPOSIT_COLS = 2;

    private static final int START_X = 10;
    private static final int START_Y = 10;

    public ChipBinMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, extraData.readBlockPos());
    }

    public ChipBinMenu(int containerId, Inventory inventory, BlockPos pos) {
        super(ModMenus.CHIP_BIN_MENU.get(), containerId, inventory, pos, ChipBinBlockEntity.class);

        /* ---- Deposit ---- */
        for (int row = 0; row < DEPOSIT_ROWS; row++) {
            for (int col = 0; col < DEPOSIT_COLS; col++) {
                this.addSlot(new SlotItemHandler(blockEntity.container,
                        col + row * 2,
                        START_X + col * 18,
                        START_Y + row * 18) {

                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                });
            }
        }

        /* ---- Player Inventory ---- */
        addDefaultPlayerInventory(inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack original = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();

            if (index >= 0 && index < DEPOSIT_ROWS * DEPOSIT_COLS) {
                original = stack.copy();

                if (!this.moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, true)) {
                    return ItemStack.EMPTY;
                }

                if (stack.isEmpty()) {
                    slot.set(ItemStack.EMPTY);
                } else {
                    slot.setChanged();
                }

                return original;
            }
        }

        return ItemStack.EMPTY;
    }
}