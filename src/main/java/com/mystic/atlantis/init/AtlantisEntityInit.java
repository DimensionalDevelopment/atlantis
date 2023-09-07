package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.power.atlanteanstone.SodiumPrimedBombBlock;
import com.mystic.atlantis.entities.AtlanteanBoatEntity;
import com.mystic.atlantis.entities.CrabEntity;
import com.mystic.atlantis.entities.JellyfishEntity;
import com.mystic.atlantis.entities.LeviathanEntity;
import com.mystic.atlantis.entities.SeahorseEntity;
import com.mystic.atlantis.entities.ShrimpEntity;
import com.mystic.atlantis.entities.StarfishEntity;
import com.mystic.atlantis.entities.StarfishZomEntity;
import com.mystic.atlantis.entities.SubmarineEntity;
import com.mystic.atlantis.util.Reference;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Reference.MODID)
public class AtlantisEntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Reference.MODID);

    //Boats
    public static final RegistryObject<EntityType<AtlanteanBoatEntity>> ATLANTEAN_BOAT = register("atlantean_boat", EntityType.Builder.of(AtlanteanBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).clientTrackingRange(10));

    //Geckolib Creatures
    public static final RegistryObject<EntityType<CrabEntity>> CRAB = register("atlantean_crab", EntityType.Builder.of(CrabEntity::new, MobCategory.WATER_CREATURE).sized(1.2f, 0.3f));
    public static final RegistryObject<EntityType<JellyfishEntity>> JELLYFISH = register("atlantean_jellyfish", EntityType.Builder.of(JellyfishEntity::new, MobCategory.WATER_AMBIENT).sized(0.4f, 0.8f));
    public static final RegistryObject<EntityType<ShrimpEntity>> SHRIMP = register("atlantean_shrimp", EntityType.Builder.of(ShrimpEntity::new, MobCategory.WATER_AMBIENT).sized(0.5f, 0.5f));
    public static final RegistryObject<EntityType<LeviathanEntity>> LEVIATHAN = register("leviathan", EntityType.Builder.of(LeviathanEntity::new, MobCategory.WATER_CREATURE).sized(1.5f, 0.7f));
    public static final RegistryObject<EntityType<SeahorseEntity>> SEAHORSE = register("atlantean_seahorse", EntityType.Builder.of(SeahorseEntity::new, MobCategory.CREATURE).sized(.4f, 1.5f));
    public static final RegistryObject<EntityType<StarfishEntity>> STARFISH = register("atlantean_starfish", EntityType.Builder.of(StarfishEntity::new, MobCategory.CREATURE).sized(1.5f, 0.7f));
    public static final RegistryObject<EntityType<StarfishZomEntity>> STARFISH_ZOM = register("atlantean_starzomfish", EntityType.Builder.of(StarfishZomEntity::new, MobCategory.MONSTER).sized(1.5f, 0.7f));

    //Explosives
    public static final RegistryObject<EntityType<SodiumPrimedBombBlock>> BOMB = register("sodium_bomb", EntityType.Builder.<SodiumPrimedBombBlock>of(SodiumPrimedBombBlock::new, MobCategory.MISC).fireImmune().sized(0.98f, 0.98f).clientTrackingRange(10).updateInterval(10));

    //TODO readd coconut crab as Geckolib entity

    //Submarines
    public static final RegistryObject<EntityType<SubmarineEntity>> SUBMARINE = register("atlantean_submarine", EntityType.Builder.of(SubmarineEntity::new, MobCategory.MISC).sized(1.6F, 1.6F).clientTrackingRange(1));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return ENTITIES.register(name, ()->builder.build("atlantis:" + name));
    }

    @SubscribeEvent
    public static void onAttributeModify(EntityAttributeCreationEvent event) {
        event.put(AtlantisEntityInit.CRAB.get(), CrabEntity.createCrabAttributes().build());
        event.put(AtlantisEntityInit.JELLYFISH.get(), JellyfishEntity.createJellyfishAttributes().build());
        event.put(AtlantisEntityInit.SHRIMP.get(), ShrimpEntity.createShrimpAttributes().build());
        event.put(AtlantisEntityInit.LEVIATHAN.get(), LeviathanEntity.createLeviathanAttributes().build());
        event.put(AtlantisEntityInit.SEAHORSE.get(), SeahorseEntity.createSeahorseAttributes().build());
        event.put(AtlantisEntityInit.STARFISH.get(), StarfishEntity.createStarfishAttributes().build());
        event.put(AtlantisEntityInit.STARFISH_ZOM.get(), StarfishZomEntity.createStarfishAttributes().build());
    }

    public static void init(IEventBus bus) {
        ENTITIES.register(bus);
    }
}
