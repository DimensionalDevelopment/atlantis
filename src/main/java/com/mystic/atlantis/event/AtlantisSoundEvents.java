package com.mystic.atlantis.event;

import com.mystic.atlantis.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class AtlantisSoundEvents {
//TODO: FIx
    //MUSIC DISC SOUNDS
    public static final SoundEvent PANBEE = register("panbee");

    private static SoundEvent register(String name) {
        ResourceLocation id = new ResourceLocation(Reference.MODID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
}
