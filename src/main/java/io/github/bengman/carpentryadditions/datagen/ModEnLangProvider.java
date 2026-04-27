package io.github.bengman.carpentryadditions.datagen;

import io.github.bengman.carpentryadditions.registry.ModBlocks;
import io.github.bengman.carpentryadditions.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnLangProvider extends LanguageProvider {

    public ModEnLangProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {

        add(ModBlocks.CHIP_BIN.get(), "Shaving Bin");
        add(ModBlocks.RESAW.get(), "Resaw Table");

        ModItems.BATTENS.forEach((wood, item) -> {

            String baseName = formatWoodName(wood);

            add(item.get(), baseName + " Batten");
        });

        ModItems.BATTEN_BLOCK_ITEMS.forEach((wood, item) -> {

            String baseName = formatWoodName(wood);

            add(item.get(), baseName + " Batten Block");
        });
    }

    private String formatWoodName(String woodType) {
        String[] parts = woodType.split("_");
        StringBuilder formatted = new StringBuilder();

        for (String part : parts) {
            if (!part.isEmpty()) {
                formatted.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1))
                        .append(" ");
            }
        }
        return formatted.toString().trim();
    }
}