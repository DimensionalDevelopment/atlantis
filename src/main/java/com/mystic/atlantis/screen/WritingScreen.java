package com.mystic.atlantis.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.blocks.base.LinguisticGlyph;
import com.mystic.atlantis.init.ItemInit;
import com.mystic.atlantis.inventory.WritingMenu;
import com.mystic.atlantis.recipes.WritingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class WritingScreen extends AbstractContainerScreen<WritingMenu> {
    private static final ResourceLocation BG_LOCATION = new ResourceLocation("textures/gui/container/stonecutter.png");
    private static final ResourceLocation GRADIENT = Atlantis.id("textures/gui/container/gradient.png");
    private static final ResourceLocation GRADIENT_TOP = Atlantis.id("textures/gui/container/gradient_top.png");
    private static final int SCROLLER_WIDTH = 12;
    private static final int SCROLLER_HEIGHT = 15;
    private static final int RECIPES_COLUMNS = 4;
    private static final int RECIPES_ROWS = 3;
    private static final int RECIPES_IMAGE_SIZE_WIDTH = 16;
    private static final int RECIPES_IMAGE_SIZE_HEIGHT = 18;
    private static final int SCROLLER_FULL_HEIGHT = 54;
    private static final int RECIPES_X = 52;
    private static final int RECIPES_Y = 14;
    private float scrollOffs;
    /**
     * Is {@code true} if the player clicked on the scroll wheel in the GUI.
     */
    private boolean scrolling;
    /**
     * The index of the first recipe to display.
     * The number of recipes displayed at any time is 12 (4 recipes per row, and 3 rows). If the player scrolled down one row, this value would be 4 (representing the index of the first slot on the second row).
     */
    private int startIndex;
    private boolean displayRecipes;

    public WritingScreen(WritingMenu arg, Inventory arg2, Component arg3) {
        super(arg, arg2, arg3);
        arg.registerUpdateListener(this::containerChanged);
    }

    @Override
    public void render(GuiGraphics poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics poseStack, float partialTick, int mouseX, int mouseY) {
        this.renderBackground(poseStack, mouseX, mouseY, partialTick);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        int i = this.leftPos;
        int j = this.topPos;
        poseStack.blit(BG_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        int k = (int) (41.0f * this.scrollOffs);
        poseStack.blit(BG_LOCATION, i + 119, j + 15 + k, 176 + (this.isScrollBarActive() ? 0 : 12), 0, 12, 15);
        int l = this.leftPos + 52;
        int m = this.topPos + 14;
        int n = this.startIndex + 12;
        this.renderButtons(poseStack, mouseX, mouseY, l, m, n);
        this.renderRecipes(poseStack, l, m, n);
        poseStack.blit(GRADIENT_TOP, i - 4, j - 4, 0, 0, imageWidth + 7, this.imageHeight + 7);

        RenderSystem.enableBlend();

        //This by the way is what's needed to pull off opengl equivlent of the Multiply Blend Mode of gimp.
        RenderSystem.blendFunc(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.ZERO);

        poseStack.blit(GRADIENT, i - 4, j - 4, 0, 0, this.imageWidth + 7, this.imageHeight + 7);
        RenderSystem.disableBlend();

    }

    @Override
    protected void renderTooltip(GuiGraphics poseStack, int x, int y) {
        super.renderTooltip(poseStack, x, y);
        if (this.displayRecipes) {
            int i = this.leftPos + 52;
            int j = this.topPos + 14;
            int k = this.startIndex + 12;
            List<RecipeHolder<WritingRecipe>> list = this.menu.getRecipes();
            for (int l = this.startIndex; l < k && l < this.menu.getNumRecipes(); ++l) {
                int m = l - this.startIndex;
                int n = i + m % 4 * 16;
                int o = j + m / 4 * 18 + 2;
                if (x < n || x >= n + 16 || y < o || y >= o + 18) continue;
                assert Minecraft.getInstance().level != null;
                poseStack.renderTooltip(font, list.get(l).value().getResultItem(this.minecraft.level.registryAccess()), x, y);
            }
        }
    }

    private void renderButtons(GuiGraphics poseStack, int mouseX, int mouseY, int x, int y, int lastVisibleElementIndex) {
        for (int i = this.startIndex; i < lastVisibleElementIndex && i < this.menu.getNumRecipes(); ++i) {
            int j = i - this.startIndex;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            int n = this.imageHeight;
            if (i == this.menu.getSelectedRecipeIndex()) {
                n += 18;
            } else if (mouseX >= k && mouseY >= m && mouseX < k + 16 && mouseY < m + 18) {
                n += 36;
            }
            poseStack.blit(BG_LOCATION, k, m - 1, 0, n, 16, 18);
        }
    }

    private void renderRecipes(GuiGraphics guiGraphics, int left, int top, int recipeIndexOffsetMax) {
        List<RecipeHolder<WritingRecipe>> list = this.menu.getRecipes();
        for (int i = this.startIndex; i < recipeIndexOffsetMax && i < this.menu.getNumRecipes(); ++i) {
            int j = i - this.startIndex;
            int k = left + j % 4 * 16;
            int l = j / 4;
            int m = top + l * 18 + 2;
            guiGraphics.renderFakeItem(ItemInit.getScroll(intToGlyph(i)).get().getDefaultInstance(), k, m);
        }
    }

    public static LinguisticGlyph intToGlyph(int scroll) {
        return switch (scroll) {
            case 1 -> LinguisticGlyph.ZERO;
            case 2 -> LinguisticGlyph.ONE;
            case 3 -> LinguisticGlyph.TWO;
            case 4 -> LinguisticGlyph.THREE;
            case 5 -> LinguisticGlyph.FOUR;
            case 6 -> LinguisticGlyph.FIVE;
            case 7 -> LinguisticGlyph.SIX;
            case 8 -> LinguisticGlyph.SEVEN;
            case 9 -> LinguisticGlyph.EIGHT;
            case 10 -> LinguisticGlyph.NINE;
            case 11 -> LinguisticGlyph.A;
            case 12 -> LinguisticGlyph.B;
            case 13 -> LinguisticGlyph.C;
            case 14 -> LinguisticGlyph.D;
            case 15 -> LinguisticGlyph.E;
            case 16 -> LinguisticGlyph.F;
            case 17 -> LinguisticGlyph.G;
            case 18 -> LinguisticGlyph.H;
            case 19 -> LinguisticGlyph.I;
            case 20 -> LinguisticGlyph.J;
            case 21 -> LinguisticGlyph.K;
            case 22 -> LinguisticGlyph.L;
            case 23 -> LinguisticGlyph.M;
            case 24 -> LinguisticGlyph.N;
            case 25 -> LinguisticGlyph.O;
            case 26 -> LinguisticGlyph.P;
            case 27 -> LinguisticGlyph.Q;
            case 28 -> LinguisticGlyph.R;
            case 29 -> LinguisticGlyph.S;
            case 30 -> LinguisticGlyph.T;
            case 31 -> LinguisticGlyph.U;
            case 32 -> LinguisticGlyph.V;
            case 33 -> LinguisticGlyph.W;
            case 34 -> LinguisticGlyph.X;
            case 35 -> LinguisticGlyph.Y;
            case 36 -> LinguisticGlyph.Z;
            default -> LinguisticGlyph.BLANK;
        };
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        if (this.displayRecipes) {
            int i = this.leftPos + 52;
            int j = this.topPos + 14;
            int k = this.startIndex + 12;
            for (int l = this.startIndex; l < k; ++l) {
                int m = l - this.startIndex;
                double d = mouseX - (double) (i + m % 4 * 16);
                double e = mouseY - (double) (j + m / 4 * 18);
                if (!(d >= 0.0) || !(e >= 0.0) || !(d < 16.0) || !(e < 18.0) || !this.menu.clickMenuButton(this.minecraft.player, l))
                    continue;
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0f));
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, l);
                return true;
            }
            i = this.leftPos + 119;
            j = this.topPos + 9;
            if (mouseX >= (double) i && mouseX < (double) (i + 12) && mouseY >= (double) j && mouseY < (double) (j + 54)) {
                this.scrolling = true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.scrolling && this.isScrollBarActive()) {
            int i = this.topPos + 14;
            int j = i + 54;
            this.scrollOffs = ((float) mouseY - (float) i - 7.5f) / ((float) (j - i) - 15.0f);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0f, 1.0f);
            this.startIndex = (int) ((double) (this.scrollOffs * (float) this.getOffscreenRows()) + 0.5) * 4;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double pScrollX, double pScrollY) {
        if (this.isScrollBarActive()) {
            int i = this.getOffscreenRows();
            this.scrollOffs = (float) ((double) this.scrollOffs - pScrollY / (double) i);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0f, 1.0f);
            this.startIndex = (int) ((double) (this.scrollOffs * (float) i) + 0.5) * 4;
        }
        return true;
    }

    private boolean isScrollBarActive() {
        return this.displayRecipes && this.menu.getNumRecipes() > 12;
    }

    protected int getOffscreenRows() {
        return (this.menu.getNumRecipes() + 4 - 1) / 4 - 3;
    }

    /**
     * Called every time this screen's container is changed (is marked as dirty).
     */
    private void containerChanged() {
        this.displayRecipes = this.menu.hasInputItem();
        if (!this.displayRecipes) {
            this.scrollOffs = 0.0f;
            this.startIndex = 0;
        }
    }
}

