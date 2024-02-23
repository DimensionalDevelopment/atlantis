package com.mystic.atlantis.init;

import com.mystic.atlantis.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AtlantisSoundEventInit {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, Reference.MODID);

    public static final Supplier<SoundEvent> PANBEE = registerSound("panbee");
    public static final Supplier<SoundEvent> COLUMN = registerSound("column_cavitation");

    private static Supplier<SoundEvent> registerSound(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Reference.MODID, name)));
    }
    
    public static void init(IEventBus bus) {
    	SOUNDS.register(bus);;
    }
}
