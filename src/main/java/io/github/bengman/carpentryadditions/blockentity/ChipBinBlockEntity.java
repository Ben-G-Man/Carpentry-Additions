package io.github.bengman.carpentryadditions.blockentity;

import io.github.bengman.carpentryadditions.menu.ChipBinMenu;
import io.github.bengman.carpentryadditions.registry.ModBlockEntities;
import io.github.bengman.carpentryadditions.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.world.entity.player.Player;

public class ChipBinBlockEntity extends BlockEntity implements MenuProvider {

    private static final int CONTAINER_SIZE = ChipBinMenu.DEPOSIT_COLS * ChipBinMenu.DEPOSIT_ROWS;
    public static final int ITEM_CAPACITY = CONTAINER_SIZE * ModItems.WOOD_CHIPS.get().getMaxStackSize();

    public final ItemStackHandler container = new ItemStackHandler(CONTAINER_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", container.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        container.deserializeNBT(tag.getCompound("inventory"));
    }

    public ChipBinBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHIP_BIN_BE.get(), pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Chip Bin");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new ChipBinMenu(containerId, playerInventory, this.worldPosition);
    }

    public int getChipCount() {
        int total = 0;

        for (int i = 0; i < container.getSlots(); i++) {
            total += container.getStackInSlot(i).getCount();
        }

        return total;
    }

    public int addChips(int quantity) {

        System.out.println("ADDING " + quantity + " CHIPS TO BIN");

        if (quantity <= 0) {
            return getChipCount();
        }

        Item item = ModItems.WOOD_CHIPS.get();

        int remaining = quantity;

        int maxStack = item.getMaxStackSize();

        /* ---- Fill existing stacks ---- */
        for (int i = 0; i < container.getSlots(); i++) {

            ItemStack stack = container.getStackInSlot(i);

            if (!stack.isEmpty() && stack.is(item)) {

                int space = maxStack - stack.getCount();

                if (space > 0) {
                    int toAdd = Math.min(space, remaining);
                    stack.grow(toAdd);
                    remaining -= toAdd;

                    container.setStackInSlot(i, stack);

                    if (remaining <= 0) {
                        return getChipCount();
                    }
                }
            }
        }

        /* ---- Fill empty slots ---- */
        for (int i = 0; i < container.getSlots(); i++) {

            ItemStack stack = container.getStackInSlot(i);

            if (stack.isEmpty()) {

                int toAdd = Math.min(maxStack, remaining);

                container.setStackInSlot(i, new ItemStack(item, toAdd));

                remaining -= toAdd;

                if (remaining <= 0) {
                    return getChipCount();
                }
            }
        }

        System.out.println("ENCOUNTERED " + remaining + " OVERFLOW ITEMS");

        /* ---- Overflow discarded ---- */
        return getChipCount();
    }
}