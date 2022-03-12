package com.mystic.atlantis.particles;

import com.mystic.atlantis.util.Reference;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;

public class ModParticleTypes {
    public static SimpleParticleType PUSH_BUBBLESTREAM = FabricParticleTypes.simple(false);

    public static void init() {
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(Reference.MODID, "push_bubblestream"), PUSH_BUBBLESTREAM);
        ParticleFactoryRegistry.getInstance().register(PUSH_BUBBLESTREAM, PushBubbleStreamParticle.Factory::new);
    }
}
