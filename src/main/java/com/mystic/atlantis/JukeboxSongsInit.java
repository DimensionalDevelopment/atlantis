package com.mystic.atlantis;

import com.mystic.atlantis.init.AtlantisSoundEventInit;
import net.minecraft.core.registries.Registries;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

public class JukeboxSongsInit {
    public static final ResourceKey<JukeboxSong> PANBEE = ResourceKey.create(Registries.JUKEBOX_SONG, Atlantis.id("panbee"));
    public static final ResourceKey<JukeboxSong> COLUMN = ResourceKey.create(Registries.JUKEBOX_SONG, Atlantis.id("column"));

    public JukeboxSongsInit(BootstrapContext<JukeboxSong> context) {
        context.register(PANBEE, new JukeboxSong(AtlantisSoundEventInit.PANBEE, Component.literal("by LudoCrypt"), 4040, 15));
        context.register(COLUMN, new JukeboxSong(AtlantisSoundEventInit.COLUMN, Component.literal("by Firel"), 4420, 15));
    }
}