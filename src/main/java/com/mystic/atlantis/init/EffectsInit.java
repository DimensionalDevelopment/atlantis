package com.mystic.atlantis.init;

import java.util.function.Supplier;

import com.mystic.atlantis.effects.SpikesEffect;
import com.mystic.atlantis.util.Reference;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectsInit {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Reference.MODID);

    public static final RegistryObject<SpikesEffect> SPIKES = registerEffects("spikes", () -> new SpikesEffect(MobEffectCategory.BENEFICIAL, 0xff0000));
    
    private static <M extends MobEffect> RegistryObject<M> registerEffects(String name, Supplier<M> mobEffect) {
        RegistryObject<M> reg = MOB_EFFECTS.register(name, mobEffect);
        return reg;
    }
    
    public static void init(IEventBus bus) {
        MOB_EFFECTS.register(bus);
    }

}
