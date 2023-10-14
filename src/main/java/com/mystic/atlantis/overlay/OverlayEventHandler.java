package com.mystic.atlantis.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mystic.atlantis.init.BlockInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class OverlayEventHandler implements IGuiOverlay {

    protected static final ResourceLocation COCONUT_BLUR = new ResourceLocation("atlantis", "textures/misc/coconutblur.png");

    public OverlayEventHandler() {
    }

    private static final Minecraft minecraft = Minecraft.getInstance();

    @Override
    public void render(ForgeGui gui, GuiGraphics poseStack, float partialTick, int width, int height) {
        gui.setupOverlayRenderState(true, false);
        renderCoconutBlur(gui, poseStack, width, height);
    }

    public void renderCoconutBlur(ForgeGui gui, GuiGraphics stack, int screenWidth, int screenHeight) {
        if (minecraft.player != null) {
            if (minecraft.options.getCameraType().isFirstPerson()) {
                if (!minecraft.player.isScoping()) {
                    ItemStack itemstack = minecraft.player.getInventory().getArmor(3);
                    if (itemstack.is(BlockInit.CARVED_COCONUT.get().asItem())) {
                        renderTextureOverlay(stack, COCONUT_BLUR, 1.0f, screenWidth, screenHeight);
                    }
                }
            }
        }
    }

    public static void renderTextureOverlay(GuiGraphics pGuiGraphics, ResourceLocation pShaderLocation, float pAlpha, int width, int height) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, pAlpha);
        pGuiGraphics.blit(pShaderLocation, 0, 0, -90, 0.0F, 0.0F, width, height, width, height);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}