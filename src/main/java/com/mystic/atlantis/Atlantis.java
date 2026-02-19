package com.mystic.atlantis;

import com.mystic.atlantis.blocks.base.ExtendedBlockEntity;
import com.mystic.atlantis.blocks.aquatic_power.SodiumPrimedBombBlock;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.datagen.WaterAttachedToLeavesDecorator;
import com.mystic.atlantis.dimension.AtlantisDimensionalEffect;
import com.mystic.atlantis.feature.AtlantisFeature;
import com.mystic.atlantis.datagen.Providers;
import com.mystic.atlantis.init.*;
import com.mystic.atlantis.particles.ModParticleTypes;
import com.mystic.atlantis.screen.LinguisticScreen;
import com.mystic.atlantis.screen.WritingScreen;
import com.mystic.atlantis.structures.AtlantisStructures;
import com.mystic.atlantis.util.Reference;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.GeckoLib;

@Mod(Reference.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Atlantis {
    public static final Logger LOGGER = LogManager.getLogger(Reference.MODID);

    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECO_TYPES = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, "atlantis");

    public static final RegistryObject<TreeDecoratorType<WaterAttachedToLeavesDecorator>> WATER_ATTACH_TO_LEAVES = TREE_DECO_TYPES.register("water_attached_to_leaves", () -> new TreeDecoratorType<>(WaterAttachedToLeavesDecorator.CODEC));

    public Atlantis() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::onCommonSet);
        bus.addListener(this::onClientSet);

        ModParticleTypes.PARTICLES.register(bus);
        onInitialize(bus);
        TREE_DECO_TYPES.register(bus);
        AtlantisFeature.init(bus);
        AtlantisStructures.DEFERRED_REGISTRY_STRUCTURE.register(bus);
        Providers.init(bus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AtlantisConfig.CONFIG_SPEC, "atlantis-common.toml");
    }

    public static void registerDispenserBehavior() {
        DispenserBlock.registerBehavior(BlockInit.SODIUM_BOMB.get(), new DefaultDispenseItemBehavior() {
            protected @NotNull ItemStack execute(@NotNull BlockSource p_123425_, @NotNull ItemStack p_123426_) {
                Level level = p_123425_.getLevel();
                BlockPos blockpos = p_123425_.getPos().relative(p_123425_.getBlockState().getValue(DispenserBlock.FACING));
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
        return new ResourceLocation("atlantis", id);
    }

    public void onInitialize(IEventBus bus) {
        GeckoLib.initialize();
        BlockInit.init(bus);
        ItemInit.init(bus);
        PaintingVariantsInit.init(bus);
        AtlantisModifierInit.init(bus);
        BlockEntityInit.init(bus);
        FluidTypesInit.init(bus);
        FluidInit.init(bus);
        AtlantisGroupInit.init(bus);
        AtlantisEntityInit.init(bus);
        AtlantisSoundEventInit.init(bus);
        EffectsInit.init(bus);
        EnchantmentInit.init(bus);
        MenuTypeInit.init(bus);
        RecipesInit.init(bus);
        POITypesInit.init(bus);
    }

    private void onClientSet(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(MenuTypeInit.LINGUISTIC.get(), LinguisticScreen::new);
            MenuScreens.register(MenuTypeInit.WRITING.get(), WritingScreen::new);
        });
    }

    private void onCommonSet(FMLCommonSetupEvent event) {
        //ToolInit.init();
        TagsInit.init();

        ((ExtendedBlockEntity) BlockEntityType.SIGN).addAdditionalValidBlock(BlockInit.NYMPH_SIGN.get(), BlockInit.NYMPH_WALL_SIGN.get());
        ((ExtendedBlockEntity) BlockEntityType.SIGN).addAdditionalValidBlock(BlockInit.PALM_SIGN.get(), BlockInit.PALM_WALL_SIGN.get());

        registerDispenserBehavior();
    }
}
