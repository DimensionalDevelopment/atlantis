package com.mystic.atlantis.entities;

import com.mystic.atlantis.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AtlantisEntities {
    private static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Reference.MODID);

    public static final EntityType<CrabEntity> CRAB = register("atlantean_crab", EntityType.Builder.create(CrabEntity::new, SpawnGroup.WATER_CREATURE).setDimensions(1.2f, 0.3f));

    public static final EntityType<JellyfishEntity> JELLYFISH = register("atlantean_jellyfish", EntityType.Builder.create(JellyfishEntity::new, SpawnGroup.WATER_AMBIENT).setDimensions(0.4f, 0.8f));

    public static final EntityType<Jellyfish2Entity> JELLYFISH2 = register("atlantean_jellyfish_2", EntityType.Builder.create(Jellyfish2Entity::new, SpawnGroup.WATER_AMBIENT).setDimensions(0.4f, 0.8f));

    public static final EntityType<ShrimpEntity> SHRIMP = register("atlantean_shrimp", EntityType.Builder.create(ShrimpEntity::new, SpawnGroup.WATER_AMBIENT).setDimensions(0.5f, 0.5f));

    public static final EntityType<SubmarineEntity> SUBMARINE = register("atlantean_submarine", EntityType.Builder.create(SubmarineEntity::new, SpawnGroup.MISC).setDimensions(1.6F, 1.6F).maxTrackingRange(1));

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        EntityType<T> type = builder.build("atlantis:" + name);
        ENTITIES.register(name, () -> type);
        return type;
    }

    private static void onAttributeModify(EntityAttributeCreationEvent event) {
        event.put(AtlantisEntities.CRAB, CrabEntity.createCrabAttributes().build());
        event.put(AtlantisEntities.JELLYFISH, JellyfishEntity.createJellyfishAttributes().build());
        event.put(AtlantisEntities.JELLYFISH2, Jellyfish2Entity.createJellyfishAttributes().build());
        event.put(AtlantisEntities.SHRIMP, ShrimpEntity.createShrimpAttributes().build());
    }

    public static void initialize(IEventBus bus) {
        ENTITIES.register(bus);
        MinecraftForge.EVENT_BUS.addListener(AtlantisEntities::onAttributeModify);

        SpawnRestriction.register(AtlantisEntities.CRAB, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrabEntity::canSpawn);
    }
}
