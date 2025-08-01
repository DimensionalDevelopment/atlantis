package com.mystic.atlantis.mixin;

import com.mystic.atlantis.datagen.EnchantmentInit;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(EnchantmentMenu.class)
public class EnchantmentMenuMixin {

    @Shadow @Final private ContainerLevelAccess access;
    @Shadow @Final private RandomSource random;

    @Inject(method = "getEnchantmentList", at = @At("HEAD"), cancellable = true)
    private void atlantis$injectAquaticEnchantments(
            RegistryAccess registryAccess,
            ItemStack stack,
            int slot,
            int cost,
            CallbackInfoReturnable<List<EnchantmentInstance>> cir
    ) {
        if (!stack.isEnchantable()) return;

        AtomicInteger fishCount = new AtomicInteger();

        Optional<List<EnchantmentInstance>> result = access.evaluate((level, pos) -> {
            fishCount.set(level.getEntitiesOfClass(AbstractFish.class, new AABB(pos).inflate(4)).size());

            if (fishCount.get() < 8) {
                // VANILLA behavior fallback
                Optional<HolderSet.Named<Enchantment>> vanillaTag =
                        registryAccess.registryOrThrow(net.minecraft.core.registries.Registries.ENCHANTMENT)
                                .getTag(EnchantmentTags.IN_ENCHANTING_TABLE);

                if (vanillaTag.isPresent()) {
                    random.setSeed(slot + random.nextInt()); // same seed logic as vanilla
                    List<EnchantmentInstance> result2 = EnchantmentHelper.selectEnchantment(
                            random, stack, cost, vanillaTag.get().stream());

                    // Remove 1 if it's a book with multiple
                    if (stack.is(Items.BOOK) && result2.size() > 1) {
                        result2.remove(random.nextInt(result2.size()));
                    }

                    return result2;
                } else {
                    return Collections.emptyList();
                }
            } else {
                // Custom fish-based enchantments
                int enchantLevel = fishCount.get() >= 24 ? 3 : fishCount.get() >= 16 ? 2 : 1;

                List<EnchantmentInstance> aquatic = new ArrayList<>();
                atlantis$addIfApplicable(aquatic, stack, level, EnchantmentInit.DEPTH_PULSE, enchantLevel);
                atlantis$addIfApplicable(aquatic, stack, level, EnchantmentInit.TIDE_CALL, enchantLevel);
                atlantis$addIfApplicable(aquatic, stack, level, EnchantmentInit.REEL_BIND, enchantLevel);
                atlantis$addIfApplicable(aquatic, stack, level, EnchantmentInit.CURRENT_GLIDE, enchantLevel);
                atlantis$addIfApplicable(aquatic, stack, level, EnchantmentInit.ABYSSAL_ARMOR, enchantLevel);
                atlantis$addIfApplicable(aquatic, stack, level, EnchantmentInit.GILL_BREATH, enchantLevel);
                atlantis$addIfApplicable(aquatic, stack, level, EnchantmentInit.ABYSS_WALKER, enchantLevel);
                return aquatic;
            }
        });

        result.ifPresent(list -> {
            if (fishCount.get() >= 8) {
                if (!list.isEmpty()) {
                    EnchantmentInstance chosen = list.get(random.nextInt(list.size())); // Or shuffle
                    cir.setReturnValue(List.of(chosen));
                } else {
                    cir.setReturnValue(Collections.emptyList());
                }
            }
        });
    }

    @Unique
    private void atlantis$addIfApplicable(List<EnchantmentInstance> list, ItemStack stack, Level level,
                                          ResourceKey<Enchantment> enchant, int levelVal) {
        var holder = EnchantmentInit.getEnchantmentHolder(level, enchant);
        if (stack.isPrimaryItemFor(holder)) {
            list.add(new EnchantmentInstance(holder, levelVal));
        }
    }
}
