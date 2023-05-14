package com.mystic.atlantis.items;

import java.util.List;
import java.util.function.Predicate;

import com.mystic.atlantis.entities.blockbenchentities.AtlanteanBoatEntity;
import com.mystic.atlantis.init.AtlantisEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class AtlanteanBoatItem extends Item {
    private static final Predicate<Entity> RIDERS;
    public AtlanteanBoatItem(Properties settings) {
        super(settings);
    }
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        HitResult hitResult = getPlayerPOVHitResult(world, user, ClipContext.Fluid.SOURCE_ONLY);
        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemStack);
        } else if (!(world instanceof ServerLevel)) {
            return InteractionResultHolder.success(itemStack);
        } else {
            BlockHitResult blockHitResult = (BlockHitResult)hitResult;
            BlockPos blockPos = blockHitResult.getBlockPos();
            if (!(world.getBlockState(blockPos).getBlock() instanceof LiquidBlock)) {
                return InteractionResultHolder.pass(itemStack);
            } else if (world.mayInteract(user, blockPos) && user.mayUseItemAt(blockPos, blockHitResult.getDirection(), itemStack)) {
                AtlanteanBoatEntity boatEntity = new AtlanteanBoatEntity(AtlantisEntityInit.ATLANTEAN_BOAT.get(), world);
                boatEntity.setPos(hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
                boatEntity.setYRot(user.getYRot());
                world.addFreshEntity(boatEntity);
                world.gameEvent(user, GameEvent.ENTITY_PLACE, new BlockPos(hitResult.getLocation()));
                if (!user.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }

                user.awardStat(Stats.ITEM_USED.get(this));
                world.gameEvent(user, GameEvent.ENTITY_PLACE, blockPos);
                return InteractionResultHolder.consume(itemStack);
            } else {
                return InteractionResultHolder.fail(itemStack);
            }
        }
    }

    public InteractionResultHolder<ItemStack> usee(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        HitResult hitResult = getPlayerPOVHitResult(world, user, ClipContext.Fluid.SOURCE_ONLY);
        if (hitResult.getType() == net.minecraft.world.phys.HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        } else {
            Vec3 vec3d = user.getViewVector(1.0F);
            List<Entity> list = world.getEntities(user, user.getBoundingBox().expandTowards(vec3d.scale(5.0D)).inflate(1.0D), RIDERS);
            if (!list.isEmpty()) {
                Vec3 vec3d2 = user.getEyePosition();

                for (Entity entity : list) {
                    AABB box = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (box.contains(vec3d2)) {
                        return InteractionResultHolder.pass(itemStack);
                    }
                }
            }

            //if (hitResult.getType() == HitResult.Type.BLOCK) {
            AtlanteanBoatEntity boatEntity = new AtlanteanBoatEntity(AtlantisEntityInit.ATLANTEAN_BOAT.get(), world);
            boatEntity.setPos(hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
            boatEntity.setYRot(user.getYRot());
//                if (!world.isSpaceEmpty(boatEntity, boatEntity.getBoundingBox())) {
//                    return TypedActionResult.fail(itemStack);
//                } else
            {
                if (!world.isClientSide) {
                    world.addFreshEntity(boatEntity);
                    world.gameEvent(user, GameEvent.ENTITY_PLACE, new BlockPos(hitResult.getLocation()));
                    if (!user.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                }

                user.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
            }
            //} else {
            //return TypedActionResult.pass(itemStack);
            //}
        }
    }
    static {
        RIDERS = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    }
}
