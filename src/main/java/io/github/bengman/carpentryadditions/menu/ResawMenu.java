package io.github.bengman.carpentryadditions.menu;

import io.github.bengman.carpentryadditions.blockentity.ResawBlockEntity;
import io.github.bengman.carpentryadditions.registry.ModBlocks;
import io.github.bengman.carpentryadditions.registry.ModItems;
import io.github.bengman.carpentryadditions.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.ItemTags;
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

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private static final int PLAYER_INV_START = 2;
    private static final int HOTBAR_END = 38;

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
        this.addSlot(new Slot(container, INPUT_SLOT, 26, 36) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ItemTags.PLANKS);
            }

            @Override
            public void setChanged() {
                super.setChanged();
                updateOutput();
            }
        });

        /* ---- Output ---- */
        this.addSlot(new Slot(container, OUTPUT_SLOT, 134, 36) {

            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {

                Slot inputSlot = slots.get(INPUT_SLOT);
                ItemStack input = inputSlot.getItem();

                if (input.is(net.minecraft.tags.ItemTags.PLANKS)) {
                    input.shrink(1);
                }

                updateOutput();

                super.onTake(player, stack);
            }
        });

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

        updateOutput();
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(ContainerLevelAccess.create(level, pos),
                player,
                ModBlocks.RESAW.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {

        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {

            ItemStack stack = slot.getItem();
            result = stack.copy();

            /* ---- Clicked Output ---- */
            if (index == OUTPUT_SLOT) {

                quickProcessAll(player);

                return ItemStack.EMPTY;
            }

            /* ---- Clicked Player Inventory ---- */
            else if (index >= PLAYER_INV_START) {

                /* Move planks into input */
                if (stack.is(net.minecraft.tags.ItemTags.PLANKS)) {

                    if (!this.moveItemStackTo(stack, INPUT_SLOT, INPUT_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            /* ---- Clicked Input ---- */
            else if (index == INPUT_SLOT) {

                if (!this.moveItemStackTo(stack, PLAYER_INV_START, HOTBAR_END, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return result;
    }

    private void quickProcessAll(Player player) {

        Slot inputSlot = this.slots.get(INPUT_SLOT);
        Slot outputSlot = this.slots.get(OUTPUT_SLOT);

        ItemStack input = inputSlot.getItem();

        /* ---- Check input ---- */
        if (!input.is(net.minecraft.tags.ItemTags.PLANKS)) {
            return;
        }

        int plankCount = input.getCount();
        int totalBattens = plankCount * 2;

        /* ---- Clear machine slots ---- */
        inputSlot.set(ItemStack.EMPTY);
        outputSlot.set(ItemStack.EMPTY);

        /* ---- Create result stack ---- */
        ItemStack battens = new ItemStack(ModItems.OAK_BATTEN.get(), totalBattens);

        /* ---- Insert into player inventory ---- */
        player.getInventory().placeItemBackInInventory(battens);
    }

    private void updateOutput() {

        Slot inputSlot = this.slots.get(INPUT_SLOT);
        Slot outputSlot = this.slots.get(OUTPUT_SLOT);

        ItemStack input = inputSlot.getItem();

        if (!input.is(net.minecraft.tags.ItemTags.PLANKS)) {
            outputSlot.set(ItemStack.EMPTY);
            return;
        }

        outputSlot.set(new ItemStack(ModItems.OAK_BATTEN.get(), 2));
    }
}