package com.mystic.atlantis.init;

import com.mystic.atlantis.blocks.aquatic_power.SodiumPrimedBombBlock;
import com.mystic.atlantis.entities.*;
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
    public static final RegistryObject<EntityType<NymphBoatEntity>> NYMPH_BOAT = register("nymph_boat", EntityType.Builder.of(NymphBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).clientTrackingRange(10));
    public static final RegistryObject<EntityType<PalmBoatEntity>> PALM_BOAT = register("palm_boat", EntityType.Builder.of(PalmBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).clientTrackingRange(10));

    //Geckolib Creatures
    public static final RegistryObject<EntityType<RubyclawCrabEntity>> RUBYCLAW_CRAB = register("rubyclaw_crab", EntityType.Builder.of(RubyclawCrabEntity::new, MobCategory.WATER_CREATURE).sized(1.2f, 0.3f));
    public static final RegistryObject<EntityType<CoconutCrabEntity>> COCONUT_CRAB = register("coconut_crab",EntityType.Builder.of(CoconutCrabEntity::new, MobCategory.CREATURE).sized(1.2f, 0.3f));
    public static final RegistryObject<EntityType<AquaielJellyfishEntity>> AQUAIEL_JELLYFISH = register("aquaiel_jellyfish", EntityType.Builder.of(AquaielJellyfishEntity::new, MobCategory.WATER_AMBIENT).sized(0.4f, 0.8f));
    public static final RegistryObject<EntityType<GlittertailShrimpEntity>> GLITTERTAIL_SHRIMP = register("glittertail_shrimp", EntityType.Builder.of(GlittertailShrimpEntity::new, MobCategory.WATER_AMBIENT).sized(0.5f, 0.5f));
    public static final RegistryObject<EntityType<LeviathanEntity>> LEVIATHAN = register("leviathan", EntityType.Builder.of(LeviathanEntity::new, MobCategory.WATER_CREATURE).sized(1.5f, 0.7f));
    public static final RegistryObject<EntityType<ThalassianSeahorseEntity>> THALASSIAN_SEAHORSE = register("thalassian_seahorse", EntityType.Builder.of(ThalassianSeahorseEntity::new, MobCategory.CREATURE).sized(.4f, 1.5f));
    public static final RegistryObject<EntityType<StarfishEntity>> STARFISH = register("starfish", EntityType.Builder.of(StarfishEntity::new, MobCategory.CREATURE).sized(1.5f, 0.7f));
    public static final RegistryObject<EntityType<ZombieStarfishEntity>> ZOMBIE_STARFISH = register("zombie_starfish", EntityType.Builder.of(ZombieStarfishEntity::new, MobCategory.MONSTER).sized(1.5f, 0.7f));

    //Explosives
    public static final RegistryObject<EntityType<SodiumPrimedBombBlock>> SODIUM_BOMB = register("sodium_bomb", EntityType.Builder.<SodiumPrimedBombBlock>of(SodiumPrimedBombBlock::new, MobCategory.MISC).fireImmune().sized(0.98f, 0.98f).clientTrackingRange(10).updateInterval(10));

    //Submarines
    public static final RegistryObject<EntityType<SubmarineEntity>> SUBMARINE = register("submarine", EntityType.Builder.of(SubmarineEntity::new, MobCategory.MISC).sized(1.6F, 1.6F).clientTrackingRange(1));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return ENTITIES.register(name, ()->builder.build("atlantis:" + name));
    }

    @SubscribeEvent
    public static void onAttributeModify(EntityAttributeCreationEvent event) {
        event.put(AtlantisEntityInit.COCONUT_CRAB.get(), CoconutCrabEntity.createCoconutCrabAttributes().build());
        event.put(AtlantisEntityInit.RUBYCLAW_CRAB.get(), RubyclawCrabEntity.createCrabAttributes().build());
        event.put(AtlantisEntityInit.AQUAIEL_JELLYFISH.get(), AquaielJellyfishEntity.createJellyfishAttributes().build());
        event.put(AtlantisEntityInit.GLITTERTAIL_SHRIMP.get(), GlittertailShrimpEntity.createShrimpAttributes().build());
        event.put(AtlantisEntityInit.LEVIATHAN.get(), LeviathanEntity.createLeviathanAttributes().build());
        event.put(AtlantisEntityInit.THALASSIAN_SEAHORSE.get(), ThalassianSeahorseEntity.createSeahorseAttributes().build());
        event.put(AtlantisEntityInit.STARFISH.get(), StarfishEntity.createStarfishAttributes().build());
        event.put(AtlantisEntityInit.ZOMBIE_STARFISH.get(), ZombieStarfishEntity.createStarfishAttributes().build());
    }

    public static void init(IEventBus bus) {
        ENTITIES.register(bus);
    }
}
