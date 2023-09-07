package com.mystic.atlantis.blocks.blockentities.renderers;

import com.mystic.atlantis.blocks.blockentities.models.GeneralPlantModel;
import com.mystic.atlantis.blocks.blockentities.plants.GeneralPlantBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GeneralPlantRenderer<T extends GeneralPlantBlockEntity<T>> extends GeoBlockRenderer<T> {
    public GeneralPlantRenderer(String name) {
        super(new GeneralPlantModel<>(name));
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
