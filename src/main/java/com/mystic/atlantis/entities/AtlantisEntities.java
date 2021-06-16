package com.mystic.atlantis.entities;

import com.mystic.atlantis.Main;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

public class AtlantisEntities
{
    public static final EntityType<CrabEntity> CRAB =
            Registry.register(Registry.ENTITY_TYPE, Main.id("atlantean_crab"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, CrabEntity::new)
                    .dimensions(EntityDimensions.fixed(1.2f, 0.3f))
                    .build());

    public static final EntityType<JellyfishEntity> JELLYFISH =
            Registry.register(Registry.ENTITY_TYPE, Main.id("atlantean_jellyfish"), FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, JellyfishEntity::new)
                    .dimensions(EntityDimensions.fixed(1.2f, 0.3f))
                    .build());

    public static void initialize() {
        FabricDefaultAttributeRegistry.register(AtlantisEntities.CRAB, CrabEntity.createCrabAttributes());
        SpawnRestriction.register(AtlantisEntities.CRAB, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrabEntity::canSpawn);

        FabricDefaultAttributeRegistry.register(AtlantisEntities.JELLYFISH, CrabEntity.createCrabAttributes());
        SpawnRestriction.register(AtlantisEntities.JELLYFISH, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, JellyfishEntity::canSpawn);
    }
}
