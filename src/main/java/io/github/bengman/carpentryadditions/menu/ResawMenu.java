package io.github.bengman.carpentryadditions.menu;

import io.github.bengman.carpentryadditions.blockentity.ResawBlockEntity;
import io.github.bengman.carpentryadditions.registry.ModBlocks;
import io.github.bengman.carpentryadditions.registry.ModItems;
import io.github.bengman.carpentryadditions.registry.ModMenus;
import io.github.bengman.carpentryadditions.utils.BattenWoodTypes;
import io.github.bengman.carpentryadditions.utils.CarpentryUtils;
import java.util.Arrays;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ResawMenu extends InventoryMenu<ResawBlockEntity> {

    private final SimpleContainer processContainer;
    public final boolean openedWithChipBin;

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    public ResawMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, extraData.readBlockPos());
    }

    public ResawMenu(int containerId, Inventory inventory, BlockPos pos) {
        super(ModMenus.RESAW_MENU.get(), containerId, inventory, pos, ResawBlockEntity.class);

        this.processContainer = new SimpleContainer(2);
        this.openedWithChipBin = blockEntity.hasChipBin();

        /* ---- Input ---- */
        this.addSlot(
                new Slot(processContainer, INPUT_SLOT, 26, 28) {
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
        this.addSlot(
                new Slot(processContainer, OUTPUT_SLOT, 134, 28) {

                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }

                    @Override
                    public void onTake(Player player, ItemStack stack) {
                        if (!level.isClientSide) {
                            Slot inputSlot = slots.get(INPUT_SLOT);
                            ItemStack input = inputSlot.getItem();

                            if (input.is(ItemTags.PLANKS)) {
                                input.shrink(1);

                                if (openedWithChipBin) {
                                    blockEntity.getChipBin().addChips(rollChips(1));
                                }
                            }

                            updateOutput();

                            super.onTake(player, stack);
                        }
                    }
                });

        /* ---- Chip Bin ---- */
        if (blockEntity.hasChipBin()) {
            // Add chip bin icon
        }

        /* ---- Player Inventory ---- */
        addDefaultPlayerInventory(inventory);
    }

    @Override
    public boolean stillValid(Player player) {
        return (AbstractContainerMenu.stillValid(
                ContainerLevelAccess.create(level, pos), player, ModBlocks.RESAW.get())
                || blockEntity.hasChipBin() != openedWithChipBin);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {

        Slot slot = this.slots.get(index);

        if (slot == null || !slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();

        /* ---- Input slot ---- */
        if (index == INPUT_SLOT) {

            if (!this.moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, true)) {
                return ItemStack.EMPTY;
            }
        }

        /* ---- Output slot ---- */
        else if (index == OUTPUT_SLOT) {

            quickProcessAll(player);
            return ItemStack.EMPTY;
        }

        /* ---- Player inventory ---- */
        else {

            if (stack.is(ItemTags.PLANKS)) {

                if (!this.moveItemStackTo(stack, INPUT_SLOT, INPUT_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }

            } else {
                // IMPORTANT: explicitly reject non-planks
                return ItemStack.EMPTY;
            }
        }

        /* ---- Final cleanup ---- */
        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return original;
    }

    private void quickProcessAll(Player player) {

        Slot inputSlot = this.slots.get(INPUT_SLOT);
        Slot outputSlot = this.slots.get(OUTPUT_SLOT);

        ItemStack input = inputSlot.getItem();

        if (!input.is(ItemTags.PLANKS)) {
            return;
        }

        int plankCount = input.getCount();
        int totalBattens = plankCount * 2;

        inputSlot.set(ItemStack.EMPTY);
        outputSlot.set(ItemStack.EMPTY);

        ItemStack battens = new ItemStack(getOutputItem(input), totalBattens);
        player.getInventory().placeItemBackInInventory(battens);

        blockEntity.getChipBin().addChips(rollChips(plankCount));
    }

    private void updateOutput() {

        if (!level.isClientSide) {
            Slot inputSlot = this.slots.get(INPUT_SLOT);
            Slot outputSlot = this.slots.get(OUTPUT_SLOT);

            ItemStack input = inputSlot.getItem();

            if (!input.is(ItemTags.PLANKS)) {
                outputSlot.set(ItemStack.EMPTY);
                return;
            }

            outputSlot.set(new ItemStack(getOutputItem(input), 2));

            this.broadcastChanges();
        }
    }

    private Item getOutputItem(ItemStack input) {
        if (!input.is(ItemTags.PLANKS)) {
            return null;
        }

        String woodType = CarpentryUtils.getWoodType(input);

        /* If the wood type exists in the batten registry */
        if (Arrays.stream(BattenWoodTypes.BATTEN_TYPES).anyMatch(woodType::equals)) {
            return ModItems.BATTENS.get(woodType).get();
        } else {
            return ModItems.getDefaultBatten().get();
        }
    }

    /* Player exits from UI */
    @Override
    public void removed(Player player) {
        super.removed(player);

        if (!player.level.isClientSide) {

            ItemStack input = this.processContainer.getItem(INPUT_SLOT);

            if (!input.isEmpty()) {
                player.getInventory().placeItemBackInInventory(input);
                this.processContainer.setItem(INPUT_SLOT, ItemStack.EMPTY);
            }
        }
    }

    private static final int[] CHIPS_TABLE = { 0, 1, 1, 1, 2, 2, 3 };
    private static final Random RANDOM = new Random();

    public int rollChips(int processedItems) {
        int total = 0;

        for (int i = 0; i < processedItems; i++) {
            total += CHIPS_TABLE[RANDOM.nextInt(CHIPS_TABLE.length)];
        }

        return total;
    }
}
