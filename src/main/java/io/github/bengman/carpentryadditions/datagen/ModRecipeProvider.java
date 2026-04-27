package io.github.bengman.carpentryadditions.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

import io.github.bengman.carpentryadditions.CarpentryAdditions;
import io.github.bengman.carpentryadditions.registry.ModItems;
import io.github.bengman.carpentryadditions.utils.BattenWoodTypes;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

        for (String wood : BattenWoodTypes.BATTEN_TYPES) {
            generateStairRecipe(consumer, wood);
            generateSlabRecipe(consumer, wood);
            generateDoorRecipe(consumer, wood);
            generateTrapdoorRecipe(consumer, wood);
            generateFenceRecipe(consumer, wood);
            generateBlockRecipe(consumer, wood);
            generateBattenRecipe(consumer, wood);
        }
    }

    /*
     * NOTE: We do not need to worry about unsupported wood types below because we
     * are iterating over the master wood type list.
     */

    // TODO: Refactor this into a reusable data gen function

    private void generateStairRecipe(Consumer<FinishedRecipe> consumer, String woodType) {
        ResourceLocation resultId = new ResourceLocation("minecraft", woodType + "_stairs");

        Item battenItem = ModItems.BATTENS.get(woodType).get();
        Item result = ForgeRegistries.ITEMS.getValue(resultId);

        if (result == null)
            return;

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .pattern("B ")
                .pattern(" B")
                .define('B', battenItem)
                .unlockedBy("has_batten", has(battenItem))
                .save(consumer, new ResourceLocation(CarpentryAdditions.MODID,
                        woodType + "_stairs_from_batten"));
    }

    private void generateSlabRecipe(Consumer<FinishedRecipe> consumer, String woodType) {
        ResourceLocation resultId = new ResourceLocation("minecraft", woodType + "_slab");

        Item battenItem = ModItems.BATTENS.get(woodType).get();
        Item result = ForgeRegistries.ITEMS.getValue(resultId);

        if (result == null)
            return;

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result)
                .requires(battenItem)
                .unlockedBy("has_batten", has(battenItem))
                .save(consumer, new ResourceLocation(CarpentryAdditions.MODID,
                        woodType + "_slab_from_batten"));
    }

    private void generateDoorRecipe(Consumer<FinishedRecipe> consumer, String woodType) {
        ResourceLocation resultId = new ResourceLocation("minecraft", woodType + "_door");

        Item battenItem = ModItems.BATTENS.get(woodType).get();
        Item result = ForgeRegistries.ITEMS.getValue(resultId);

        if (result == null)
            return;

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .pattern("BB")
                .pattern("BB")
                .define('B', battenItem)
                .unlockedBy("has_batten", has(battenItem))
                .save(consumer, new ResourceLocation(CarpentryAdditions.MODID,
                        woodType + "_door_from_batten"));
    }

    private void generateTrapdoorRecipe(Consumer<FinishedRecipe> consumer, String woodType) {
        ResourceLocation resultId = new ResourceLocation("minecraft", woodType + "_trapdoor");

        Item battenItem = ModItems.BATTENS.get(woodType).get();
        Item result = ForgeRegistries.ITEMS.getValue(resultId);

        if (result == null)
            return;

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .pattern("BBB")
                .pattern("BBB")
                .define('B', battenItem)
                .unlockedBy("has_batten", has(battenItem))
                .save(consumer, new ResourceLocation(CarpentryAdditions.MODID,
                        woodType + "_trapdoor_from_batten"));
    }

    private void generateFenceRecipe(Consumer<FinishedRecipe> consumer, String woodType) {
        ResourceLocation resultId = new ResourceLocation("minecraft", woodType + "_fence");

        Item battenItem = ModItems.BATTENS.get(woodType).get();
        Item result = ForgeRegistries.ITEMS.getValue(resultId);

        if (result == null)
            return;

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .pattern("BB")
                .pattern("SS")
                .define('B', battenItem)
                .define('S', ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", "stick")))
                .unlockedBy("has_batten", has(battenItem))
                .save(consumer, new ResourceLocation(CarpentryAdditions.MODID,
                        woodType + "_fence_from_batten"));
    }

    private void generateBlockRecipe(Consumer<FinishedRecipe> consumer, String woodType) {

        Item battenItem = ModItems.BATTENS.get(woodType).get();
        Item result = ModItems.BATTEN_BLOCK_ITEMS.get(woodType).get();

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .pattern("B")
                .pattern("B")
                .define('B', battenItem)
                .unlockedBy("has_batten", has(battenItem))
                .save(consumer, new ResourceLocation(CarpentryAdditions.MODID,
                        woodType + "_batton_block_from_batten"));
    }

    private void generateBattenRecipe(Consumer<FinishedRecipe> consumer, String woodType) {
        Item battenBlockItem = ModItems.BATTEN_BLOCK_ITEMS.get(woodType).get();
        Item result = ModItems.BATTENS.get(woodType).get();

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 2)
                .requires(battenBlockItem)
                .unlockedBy("has_batten_block", has(battenBlockItem))
                .save(consumer, new ResourceLocation(CarpentryAdditions.MODID,
                        woodType + "_batten_from_batten_block"));
    }
}
