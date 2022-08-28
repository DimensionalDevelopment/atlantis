package com.mystic.atlantis.init;

import com.mystic.atlantis.effects.SpikesEffect;
import com.mystic.atlantis.util.Reference;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class EffectsInit {

    public static void init(IEventBus bus) {
        MOB_EFFECTS.register(bus);
    }

    private static DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Reference.MODID);

    private static RegistryObject<MobEffect> registerEffects(String name, Supplier<MobEffect> mobEffect) {
        var reg = MOB_EFFECTS.register(name, mobEffect);
        return reg;
    }

    public static final RegistryObject<MobEffect> SPIKES = registerEffects("spikes", () -> new SpikesEffect(MobEffectCategory.BENEFICIAL, 0xff0000));
}
