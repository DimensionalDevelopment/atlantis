package com.mystic.atlantis;

import com.mystic.atlantis.blocks.aquatic_power.SodiumPrimedBombBlock;
import com.mystic.atlantis.blocks.base.ExtendedBlockEntity;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.datagen.Providers;
import com.mystic.atlantis.datagen.WaterAttachedToLeavesDecorator;
import com.mystic.atlantis.dimension.AtlantisDimensions;
import com.mystic.atlantis.feature.AtlantisFeature;
import com.mystic.atlantis.init.*;
import com.mystic.atlantis.items.armor.BasicArmorMaterial;
import com.mystic.atlantis.particles.ModParticleTypes;
import com.mystic.atlantis.screen.LinguisticScreen;
import com.mystic.atlantis.screen.WritingScreen;
import com.mystic.atlantis.structures.AtlantisStructures;
import com.mystic.atlantis.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

@Mod(Reference.MODID)
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class Atlantis {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECO_TYPES = DeferredRegister.create(BuiltInRegistries.TREE_DECORATOR_TYPE, "atlantis");

    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<WaterAttachedToLeavesDecorator>> WATER_ATTACH_TO_LEAVES = TREE_DECO_TYPES.register("water_attached_to_leaves", () -> new TreeDecoratorType<>(WaterAttachedToLeavesDecorator.CODEC));

    public Atlantis(ModContainer container) {
        IEventBus bus = container.getEventBus();
        container.registerConfig(ModConfig.Type.COMMON, AtlantisConfig.CONFIG_SPEC);
        ModParticleTypes.PARTICLES.register(bus);
        onInitialize(bus);
        TREE_DECO_TYPES.register(bus);
        AtlantisFeature.init(bus);
        AtlantisStructures.DEFERRED_REGISTRY_STRUCTURE.register(bus);
        Providers.init(bus);
    }

    public static void registerDispenserBehavior() {
        DispenserBlock.registerBehavior(BlockInit.SODIUM_BOMB.get(), new DefaultDispenseItemBehavior() {
            protected @NotNull ItemStack execute(@NotNull BlockSource p_123425_, @NotNull ItemStack p_123426_) {
                Level level = p_123425_.level();
                BlockPos blockpos = p_123425_.pos().relative(p_123425_.state().getValue(DispenserBlock.FACING));
                SodiumPrimedBombBlock primedtnt = new SodiumPrimedBombBlock(level, (double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D, (LivingEntity)null);
                level.addFreshEntity(primedtnt);
                level.playSound(null, primedtnt.getX(), primedtnt.getY(), primedtnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.ENTITY_PLACE, blockpos);
                p_123426_.shrink(1);
                return p_123426_;
            }
        });
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, id(name));
    }

    public static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath("atlantis", id);
    }

    public void onInitialize(IEventBus bus) {
        BlockInit.init(bus);
        ItemInit.init(bus);
        BasicArmorMaterial.init(bus);
        AtlantisModifierInit.init(bus);
        BlockEntityInit.init(bus);
        FluidTypesInit.init(bus);
        FluidInit.init(bus);
        AtlantisGroupInit.init(bus);
        AtlantisEntityInit.init(bus);
        AtlantisSoundEventInit.init(bus);
        EffectsInit.init(bus);
        MenuTypeInit.init(bus);
        RecipesInit.init(bus);
        POITypesInit.init(bus);
        AtlantisDimensions.init(bus);
    }

    @SubscribeEvent
    public static void onClientSet(RegisterMenuScreensEvent event) {
        event.register(MenuTypeInit.LINGUISTIC.get(), LinguisticScreen::new);
        event.register(MenuTypeInit.WRITING.get(), WritingScreen::new);
    }

    @SubscribeEvent
    public static void onCommonSet(FMLCommonSetupEvent event) {
        ToolInit.init();
        TagsInit.init();

        ((ExtendedBlockEntity) BlockEntityType.SIGN).addAdditionalValidBlock(BlockInit.NYMPH_SIGN.get(), BlockInit.NYMPH_WALL_SIGN.get());
        ((ExtendedBlockEntity) BlockEntityType.SIGN).addAdditionalValidBlock(BlockInit.PALM_SIGN.get(), BlockInit.PALM_WALL_SIGN.get());

        registerDispenserBehavior();
    }
}
