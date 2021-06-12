package com.mystic.atlantis.event;

import com.mystic.atlantis.util.Reference;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AtlantisSoundEvents {

    //MUSIC DISC SOUNDS
    public static final SoundEvent PANBEE = register("panbee");

    private static SoundEvent register(String name) {
        Identifier id = new Identifier(Reference.MODID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
}
