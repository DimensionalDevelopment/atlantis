package com.mystic.atlantis.config;

import com.mystic.atlantis.util.Reference;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

@Config(name = Reference.MODID)
@Config.Gui.Background("atlantis:textures/block/chiseled_golden_aquamarine.png")
public final class AtlantisConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public General general = new General();

    private AtlantisConfig() {
    }

    @Config(name = "general")
    public static final class General implements ConfigData {
        @SuppressWarnings("unused")
        @ConfigEntry.Gui.NoTooltip
        public static boolean islandsOn = false;
        @ConfigEntry.Gui.NoTooltip
        public static boolean volcanoesOn = false;
        @ConfigEntry.Gui.Excluded
        public int configVersion = 0;
        @ConfigEntry.Gui.Tooltip(count = 1)
        public static int minCrabSpawnHeight = 75;
        @ConfigEntry.Gui.Tooltip(count = 1)
        public static int maxCrabSpawnHeight = 85;
        @ConfigEntry.Gui.Tooltip(count = 1)
        public static double calciteAcceleration = 0.1d;
        @ConfigEntry.Gui.Tooltip(count = 1)
        public static double calciteThreshold = 1.8d;
        public General() {
        }
    }
}
