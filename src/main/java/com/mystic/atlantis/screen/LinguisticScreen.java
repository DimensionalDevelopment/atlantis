package com.mystic.atlantis.screen;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.init.GlyphBlock;
import com.mystic.atlantis.inventory.LinguisticMenu;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;

public class LinguisticScreen extends AbstractContainerScreen<LinguisticMenu> {
	private static final ResourceLocation BG_LOCATION = Atlantis.id("textures/gui/container/linguistic.png");

	private static final Map<DyeColor, Color> colorMap = Stream.of(DyeColor.values()).collect(Collectors.toMap(a -> a, a -> new Color(a.getTextureDiffuseColors())));

	private static final Color defaultColor = new Color(140f / 255f, 174f / 255f, 210f / 255f);

	public LinguisticScreen(LinguisticMenu arg, Inventory arg2, Component arg3) {
		super(arg, arg2, arg3);
		this.imageWidth = 184;
		this.imageHeight = 174;

		this.titleLabelX = 10;
		this.titleLabelY = 8;
		this.inventoryLabelX = 12;
		this.inventoryLabelY = imageHeight - 98;
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
		Slot slot4 = this.menu.getResultSlot();

		if (!slot.hasItem()) {
			this.blit(arg, k + slot.x, l + slot.y, this.imageWidth, 0, 16, 16);
		}

		if (!slot2.hasItem()) {
			this.blit(arg, k + slot2.x, l + slot2.y, this.imageWidth + 16, 0, 16, 16);
		}

		if (!slot3.hasItem()) {
			this.blit(arg, k + slot3.x, l + slot3.y, this.imageWidth + 32, 0, 16, 16);
		}

		if (slot4.hasItem()) {
			if(slot4.getItem().getItem() instanceof BlockItem item && item.getBlock() instanceof GlyphBlock glyphBlock) {
				arg.pushPose();
				arg.translate(k + 74, l + 29, 0);

				RenderSystem.setShaderColor(1, 1, 1, 1);

				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderTexture(0, glyphBlock.getGlyph().getTexture());
				Color color = colorMap.get(glyphBlock.getDyeColor());

				Objects.requireNonNullElse(color, defaultColor).setup();

				blit(arg, 0, 0, 34, 34, 0,0, 16,16,16,16);

				RenderSystem.setShaderColor(1, 1, 1, 1);

				arg.popPose();
			}
		}
	}

	private static record Color(float r, float g, float b) {
		public Color(float[] color) {
			this(color[0], color[1], color[2]);
		}

		public void setup() {
			RenderSystem.setShaderColor(r, g, b, 1.0f);
		}
	}
}
