package com.mystic.atlantis.config;

import com.mystic.atlantis.util.Reference;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = Reference.MODID)
@Config.Gui.Background("atlantis:textures/block/chiseled_golden_aquamarine")
public final class AtlantisConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public General general = new General();

    private AtlantisConfig() {
    }

    @Config(name = "general")
    public static final class General implements ConfigData {
        @SuppressWarnings("unused")
        @ConfigEntry.Gui.Excluded
        public int configVersion = 0;
        @ConfigEntry.Gui.Tooltip(count = 2)
        public static int minCrabSpawnHeight = 75;
        @ConfigEntry.Gui.Tooltip(count = 2)
        public static int maxCrabSpawnHeight = 85;

        public General() {
        }
    }
}
