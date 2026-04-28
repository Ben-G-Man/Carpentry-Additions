package io.github.bengman.carpentryadditions.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import io.github.bengman.carpentryadditions.CarpentryAdditions;
import io.github.bengman.carpentryadditions.menu.ResawMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;

public class ResawScreen extends AbstractContainerScreen<ResawMenu> {

    private static final ResourceLocation DISCONNECTED_TEXTURE = new ResourceLocation(CarpentryAdditions.MODID,
            "textures/gui/resaw_disconnected.png");
    private static final ResourceLocation CONNECTED_TEXTURE = new ResourceLocation(CarpentryAdditions.MODID,
            "textures/gui/resaw_connected.png");

    public ResawScreen(ResawMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        ResourceLocation texture = menu.openedWithChipBin
                ? CONNECTED_TEXTURE
                : DISCONNECTED_TEXTURE;

        RenderSystem.setShaderTexture(0, texture);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        renderTooltip(poseStack, mouseX, mouseY);
    }
}