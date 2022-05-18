package com.mystic.atlantis.screen;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mystic.atlantis.inventory.LinguisticMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LinguisticScreen extends AbstractContainerScreen<LinguisticMenu> {
	private static final ResourceLocation BG_LOCATION = new ResourceLocation("textures/gui/container/loom.png");
	private static final int BASE_PATTERN_INDEX = 1;
	private static final int PATTERN_COLUMNS = 4;
	private static final int PATTERN_ROWS = 4;
	private static final int TOTAL_PATTERN_ROWS = (BannerPattern.COUNT - BannerPattern.PATTERN_ITEM_COUNT - 1 + 4 - 1) / 4;
	private static final int SCROLLER_WIDTH = 12;
	private static final int SCROLLER_HEIGHT = 15;
	private static final int PATTERN_IMAGE_SIZE = 14;
	private static final int SCROLLER_FULL_HEIGHT = 56;
	private static final int PATTERNS_X = 60;
	private static final int PATTERNS_Y = 13;

	public LinguisticScreen(LinguisticMenu arg, Inventory arg2, Component arg3) {
		super(arg, arg2, arg3);
		this.titleLabelY -= 2;
	}

	@Override
	protected void init() {
		super.init();
	}

	@Override
	public void render(PoseStack arg, int i, int j, float f) {
		super.render(arg, i, j, f);
		this.renderTooltip(arg, i, j);
	}

	@Override
	protected void renderBg(PoseStack arg, float f, int i, int j) {
		this.renderBackground(arg);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, BG_LOCATION);
		int k = this.leftPos;
		int l = this.topPos;
		this.blit(arg, k, l, 0, 0, this.imageWidth, this.imageHeight);
		Slot slot = this.menu.getBlankSlot();
		Slot slot2 = this.menu.getDyeSlot();
		Slot slot3 = this.menu.getSymbolSlot();

		if (!slot.hasItem()) {
			this.blit(arg, k + slot.x, l + slot.y, this.imageWidth, 0, 16, 16);
		}

		if (!slot2.hasItem()) {
			this.blit(arg, k + slot2.x, l + slot2.y, this.imageWidth + 16, 0, 16, 16);
		}

		if (!slot3.hasItem()) {
			this.blit(arg, k + slot3.x, l + slot3.y, this.imageWidth + 32, 0, 16, 16);
		}
	}
}
