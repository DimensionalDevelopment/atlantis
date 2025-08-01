package com.mystic.atlantis.event;

import com.mystic.atlantis.Atlantis;
import com.mystic.atlantis.config.AtlantisConfig;
import com.mystic.atlantis.datagen.EnchantmentInit;
import com.mystic.atlantis.dimension.AtlantisDimensions;
import com.mystic.atlantis.init.EffectsInit;
import com.mystic.atlantis.init.ItemInit;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.enchanting.EnchantmentLevelSetEvent;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.*;
import java.util.stream.Stream;

@EventBusSubscriber
public class CommonEvents {

    public static boolean hasEnchantment(Level level, ItemStack itemStack, ResourceKey<Enchantment> enchantment) {
        return level.registryAccess().holder(enchantment).filter(holder -> itemStack.getEnchantments().getLevel(holder) > 0).isPresent();
    }

    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        if (event.getEntity() instanceof ItemEntity item) {
            if (item.getItem().getItem() == ItemInit.SEASALT.get()) {
                Level world = item.level();
                ItemEntity item2 = new ItemEntity(world, item.getX(), item.getY(), item.getZ(), new ItemStack(ItemInit.SODIUM_NUGGET.get(), item.getItem().getCount()));
                if (!world.isClientSide) {
                    world.addFreshEntity(item2);
                    if (item2.isOnFire()) {
                        item2.clearFire();
                    }
                }
            }
        }
    }

    public static final String NOT_FIRST_SPAWN_NBT = "atlantis.not_first_spawn";
    public static ResourceKey<Level> previousDimension;

    @SubscribeEvent
    public static void onPostDamageEffects(LivingDamageEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;

        Level level = player.level();
        RandomSource random = player.getRandom();
        Entity attacker = event.getSource().getEntity();

        // --- SPIKES EFFECT ---
        if (player.hasEffect(EffectsInit.SPIKES) && attacker instanceof LivingEntity livingAttacker) {
            livingAttacker.hurt(player.damageSources().thorns(player), getDamage(3, (Random) random));
        }

        // --- TIDECALL Enchantment retaliation ---
        ItemStack weapon = player.getMainHandItem();
        if (hasEnchantment(level, weapon, EnchantmentInit.TIDE_CALL) && weapon.getItem().toString().contains("trident")) {
            if (attacker instanceof LivingEntity target) {
                target.hurt(player.damageSources().trident(player, target), getDamage(3, (Random) random));
            }
        }
    }

    private static boolean isAquaticEnchantment(Level level, Holder<Enchantment> enchantment) {
        return enchantment.equals(EnchantmentInit.getEnchantmentHolder(level, EnchantmentInit.GILL_BREATH))
                || enchantment.equals(EnchantmentInit.getEnchantmentHolder(level, EnchantmentInit.ABYSSAL_ARMOR))
                || enchantment.equals(EnchantmentInit.getEnchantmentHolder(level, EnchantmentInit.REEL_BIND))
                || enchantment.equals(EnchantmentInit.getEnchantmentHolder(level, EnchantmentInit.CURRENT_GLIDE))
                || enchantment.equals(EnchantmentInit.getEnchantmentHolder(level, EnchantmentInit.TIDE_CALL))
                || enchantment.equals(EnchantmentInit.getEnchantmentHolder(level, EnchantmentInit.ABYSS_WALKER))
                || enchantment.equals(EnchantmentInit.getEnchantmentHolder(level, EnchantmentInit.DEPTH_PULSE));
    }


    @SubscribeEvent
    public static void onFishCaught(ItemFishedEvent event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (level.isClientSide) return;

        ItemStack rod = player.getMainHandItem();
        if (!rod.is(Items.FISHING_ROD)) {
            rod = player.getOffhandItem();
            if (!rod.is(Items.FISHING_ROD)) {
                return; // Not holding a fishing rod in either hand
            }
        }

        if (!CommonEvents.hasEnchantment(level, rod, EnchantmentInit.REEL_BIND)) return;

        int enchantLevel = EnchantmentHelper.getTagEnchantmentLevel(EnchantmentInit.getEnchantmentHolder(level, EnchantmentInit.REEL_BIND), rod);
        RandomSource random = level.getRandom();

        // 🎁 Bonus loot
        if (random.nextFloat() < 0.10f * enchantLevel) {
            ResourceKey<LootTable> lootKey = ResourceKey.create(Registries.LOOT_TABLE, Atlantis.id("gameplay/reel_bind_bonus"));
            LootTable table = Objects.requireNonNull(level.getServer()).reloadableRegistries().getLootTable(lootKey);

            LootParams params = new LootParams.Builder((ServerLevel) level)
                    .withParameter(LootContextParams.ORIGIN, player.position())
                    .withParameter(LootContextParams.THIS_ENTITY, player)
                    .create(LootContextParamSets.GIFT);

            List<ItemStack> loot = table.getRandomItems(params);

            for (ItemStack stack : loot) {
                level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY(), player.getZ(), stack));
            }
        }

        // 🌟 Bonus XP
        int bonusXp = 5 * enchantLevel;
        level.addFreshEntity(new net.minecraft.world.entity.ExperienceOrb(level, player.getX(), player.getY(), player.getZ(), bonusXp));
    }

    @SubscribeEvent
    public static void onEnchantmentLevelSet(EnchantmentLevelSetEvent event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        if (level.isClientSide) return;

        // Count fish in a 9x9x9 area
        int fishCount = level.getEntitiesOfClass(AbstractFish.class, new AABB(pos).inflate(4)).size();
        if (fishCount < 8) return; // Require at least 8 live fish nearby

        // Get all registered enchantments
        Stream<Holder<Enchantment>> allEnchantments = level.registryAccess()
                .registryOrThrow(Registries.ENCHANTMENT)
                .holders()
                .filter(holder -> event.getItem().isPrimaryItemFor(holder))
                .map(holder -> Holder.direct(holder.value()));

        // Use method to get all valid enchantments for this item and level
        List<EnchantmentInstance> possible = EnchantmentHelper.getAvailableEnchantmentResults(
                event.getEnchantLevel(), event.getItem(), allEnchantments
        );

        // Apply fish-tiered enchantment logic
        Optional<EnchantmentInstance> aquatic;

        if (fishCount >= 24) {
            // Legendary tier — prefer highest-level aquatic enchantment
            aquatic = possible.stream()
                    .filter(e -> isAquaticEnchantment(level, e.enchantment))
                    .max(Comparator.comparingInt(e -> e.level));
        } else if (fishCount >= 16) {
            // Rare tier — prefer any aquatic enchantment
            aquatic = possible.stream()
                    .filter(e -> isAquaticEnchantment(level, e.enchantment))
                    .findFirst();
        } else {
            // Common tier — small chance
            aquatic = possible.stream()
                    .filter(e -> isAquaticEnchantment(level, e.enchantment))
                    .findAny();
        }

        // Apply the enchantment level (cannot set specific enchantment directly)
        aquatic.ifPresent(e -> {
            event.setEnchantLevel(e.level);
        });
    }


    public static Map<ResourceLocation, Integer> map;

    @SubscribeEvent
    public static void onPlayerLoginEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (AtlantisConfig.CONFIG.startInAtlantis.get()) {
            if (event.getEntity().getServer() != null) {
                ServerLevel atlantisLevel = event.getEntity().getServer().getLevel(AtlantisDimensions.ATLANTIS_WORLD);
                CompoundTag tag = event.getEntity().getPersistentData();
                CompoundTag persistedTag = tag.getCompound(Player.PERSISTED_NBT_TAG);
                if (AtlantisDimensions.ATLANTIS_WORLD != null) {
                    boolean isFirstTimeSpawning = !persistedTag.getBoolean(NOT_FIRST_SPAWN_NBT);
                    if (isFirstTimeSpawning) {
                        if (atlantisLevel != null) {
                            persistedTag.putBoolean(NOT_FIRST_SPAWN_NBT, true);
                            tag.put(Player.PERSISTED_NBT_TAG, persistedTag);
                            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                                sendPlayerToDimension(serverPlayer, atlantisLevel, new Vec3(atlantisLevel.getLevel().getLevelData().getSpawnPos().getX(), 100, atlantisLevel.getLevel().getLevelData().getSpawnPos().getZ()));
                            }
                        }
                    }
                }
            }
        }
    }

    private static void sendPlayerToDimension(ServerPlayer serverPlayer, ServerLevel targetWorld, Vec3 targetVec) {
        targetWorld.getChunk(new BlockPos((int) targetVec.x, (int) targetVec.y, (int) targetVec.z));
        serverPlayer.teleportTo(targetWorld, targetVec.x(), targetVec.y(), targetVec.z(), serverPlayer.getYRot(), serverPlayer.getXRot());
    }

    @SubscribeEvent
    public static void onDeathEvent(LivingDeathEvent event) {
        previousDimension = event.getEntity().level().dimension();
    }

    @SubscribeEvent
    public static void onLivingHurtEvent(EntityInvulnerabilityCheckEvent event) {
        if (event.getEntity() instanceof Player player) {
            for (ItemStack stack : player.getArmorSlots()) {
                if (hasEnchantment(player.level(), stack, EnchantmentInit.LIGHTNING_PROTECTION)) {
                    if (event.getSource().is(DamageTypes.LIGHTNING_BOLT)) {
                        event.setInvulnerable(true);
                        return;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onTickFishBasedEnchantments(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player)) return;

        Level level = player.level();
        if (level.isClientSide()) return;

        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legs = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack feet = player.getItemBySlot(EquipmentSlot.FEET);

        // GILL_BREATH: Replenish air while underwater
        if (CommonEvents.hasEnchantment(level, helmet, EnchantmentInit.GILL_BREATH) && player.isUnderWater()) {
            player.setAirSupply(Math.min(player.getMaxAirSupply(), player.getAirSupply() + 1));
        }

        // CURRENT_GLIDE: Grant Dolphin’s Grace while swimming
        if (CommonEvents.hasEnchantment(level, feet, EnchantmentInit.CURRENT_GLIDE) && player.isInWater()) {
            player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 40, 0, false, false, true));
        }

        // ABYSS_WALKER: Cancel mining fatigue (slowness) underwater
        if (CommonEvents.hasEnchantment(level, legs, EnchantmentInit.ABYSS_WALKER) && player.isInWater()) {
            player.removeEffect(MobEffects.DIG_SLOWDOWN);
        }

        // ABYSSAL_ARMOR: Resistance while submerged
        if (CommonEvents.hasEnchantment(level, chest, EnchantmentInit.ABYSSAL_ARMOR) && player.isInWater()) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 0, false, false, true));
        }
    }


    private static int getDamage(int i, Random random) {
        return i > 10 ? i - 10 : 1 + random.nextInt(4);
    }

    @SubscribeEvent
    public static void onSwimExitDetection(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player)) return;
        Level level = player.level();
        if (level.isClientSide) return;

        CompoundTag data = player.getPersistentData();

        boolean wasSwimming = data.getBoolean("atlantis_was_swimming");
        boolean isSwimmingNow = player.isSwimming() && player.isInWater();

        // Detect transition from swimming to not
        if (wasSwimming && !isSwimmingNow) {
            onSwimExit(player, level);
        }

        // Update for next tick
        data.putBoolean("atlantis_was_swimming", isSwimmingNow);
    }


    private static void onSwimExit(Player player, Level level) {
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        if (!CommonEvents.hasEnchantment(level, chest, EnchantmentInit.DEPTH_PULSE)) return;

        Vec3 pos = player.position();

        level.getEntities(player, player.getBoundingBox().inflate(4), e -> e instanceof LivingEntity && e != player)
                .forEach(entity -> {
                    Vec3 push = entity.position().subtract(pos).normalize().scale(0.6);
                    entity.push(push.x, 0.5, push.z);
                    entity.hurt(player.damageSources().explosion(player, entity), 1);
                });

        if (level instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.BUBBLE, pos.x, pos.y, pos.z, 20, 1, 1, 1, 0.2);
        }

        level.playSound(null, player.blockPosition(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 1f, 1f);
    }
}
