package io.github.bengman.carpentryadditions.datagen;

import io.github.bengman.carpentryadditions.CarpentryAdditions;
import io.github.bengman.carpentryadditions.registry.ModBlocks;
import io.github.bengman.carpentryadditions.utils.BattenWoodTypes;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RotatedPillarBlock;

import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CarpentryAdditions.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (String wood : BattenWoodTypes.BATTEN_TYPES) {
            registerStateAndModel(wood);
        }
    }

    private void registerStateAndModel(String wood) {
        String name = wood + "_batten_block";

        ResourceLocation top = modLoc("block/batten/top/" + wood);
        ResourceLocation side = modLoc("block/batten/side/" + wood);

        ModelFile verticalModel = models().withExistingParent(name, mcLoc("block/block"))
                .texture("particle", side)
                .texture("top", top)
                .texture("side", side)
                .element()
                .from(0, 0, 0)
                .to(16, 16, 16)

                .face(Direction.UP).uvs(0, 0, 16, 16).texture("#top").end()
                .face(Direction.DOWN).uvs(0, 0, 16, 16).texture("#top").end()

                .face(Direction.NORTH).uvs(0, 0, 16, 16).texture("#side").end()
                .face(Direction.SOUTH).uvs(0, 0, 16, 16).texture("#side").end()

                .face(Direction.WEST).uvs(16, 0, 0, 16).texture("#side").end()
                .face(Direction.EAST).uvs(16, 0, 0, 16).texture("#side").end()

                .end();

        axisBlock(
                (RotatedPillarBlock) ModBlocks.BATTEN_BLOCKS.get(wood).get(),
                verticalModel,
                verticalModel);
    }
}