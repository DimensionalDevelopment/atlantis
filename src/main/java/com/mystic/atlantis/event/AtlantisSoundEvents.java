package com.mystic.atlantis.event;

import com.mystic.atlantis.util.Reference;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AtlantisSoundEvents {
    public static DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Reference.MODID);

    public static final RegistryObject<SoundEvent> PANBEE = registerSound("panbee");

    public static final RegistryObject<SoundEvent> COLUMN = registerSound("column_cavitation");

    private static RegistryObject<SoundEvent> registerSound(String name) {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(Reference.MODID, name)));
    }
}
