package com.mystic.atlantis.init;

import com.mystic.atlantis.effects.SpikesEffect;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EffectsInit {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Reference.MODID);

    public static final Supplier<SpikesEffect> SPIKES = registerEffects("spikes", () -> new SpikesEffect(MobEffectCategory.BENEFICIAL, 0xff0000));
    
    private static <M extends MobEffect> Supplier<M> registerEffects(String name, Supplier<M> mobEffect) {
        Supplier<M> reg = MOB_EFFECTS.register(name, mobEffect);
        return reg;
    }
    
    public static void init(IEventBus bus) {
        MOB_EFFECTS.register(bus);
    }

}
