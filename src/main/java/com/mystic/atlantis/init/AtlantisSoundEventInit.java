package com.mystic.atlantis.init;

import com.mystic.atlantis.util.Reference;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AtlantisSoundEventInit {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Reference.MODID);

    public static final RegistryObject<SoundEvent> PANBEE = registerSound("panbee");
    public static final RegistryObject<SoundEvent> COLUMN = registerSound("column_cavitation");

    private static RegistryObject<SoundEvent> registerSound(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Reference.MODID, name)));
    }
    
    public static void init(IEventBus bus) {
    	SOUNDS.register(bus);;
    }
}
