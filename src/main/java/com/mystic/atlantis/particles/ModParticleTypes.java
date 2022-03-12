package com.mystic.atlantis.particles;

import com.mystic.atlantis.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticleTypes {

    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Reference.MODID);
    public static final RegistryObject<SimpleParticleType> PUSH_BUBBLESTREAM = PARTICLES.register("push_bubblestream", () -> new SimpleParticleType(false));

    @SubscribeEvent
    public static void init(ParticleFactoryRegisterEvent bus) {
        Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(Reference.MODID, "push_bubblestream"), PUSH_BUBBLESTREAM.get());
        Minecraft.getInstance().particleEngine.register(PUSH_BUBBLESTREAM.get(), PushBubbleStreamParticle.Factory::new);
    }
}
