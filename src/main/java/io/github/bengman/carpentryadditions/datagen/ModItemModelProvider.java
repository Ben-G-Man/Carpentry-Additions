package io.github.bengman.carpentryadditions.datagen;

import io.github.bengman.carpentryadditions.CarpentryAdditions;
import io.github.bengman.carpentryadditions.utils.BattenWoodTypes;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CarpentryAdditions.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (String wood : BattenWoodTypes.BATTEN_TYPES) {
            blockItem(wood);
            battenItem(wood);
        }
    }

    private void blockItem(String wood) {
        String name = wood + "_batten_block";

        withExistingParent(name,
                modLoc("block/" + name));
    }

    private void battenItem(String wood) {
        String name = wood + "_batten";

        singleTexture(name,
                mcLoc("item/generated"),
                "layer0",
                modLoc("item/batten/" + wood));
    }
}