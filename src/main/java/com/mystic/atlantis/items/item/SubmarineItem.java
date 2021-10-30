package com.mystic.atlantis.items.item;

import java.util.List;
import java.util.function.Predicate;

import com.mystic.atlantis.entities.AtlantisEntities;
import com.mystic.atlantis.entities.SubmarineEntity;

import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class SubmarineItem extends Item {
    private static final Predicate<Entity> RIDERS;
    public SubmarineItem(Settings settings) {
        super(settings);
    }
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(itemStack);
        } else if (!(world instanceof ServerWorld)) {
            return TypedActionResult.success(itemStack);
        } else {
            BlockHitResult blockHitResult = (BlockHitResult)hitResult;
            BlockPos blockPos = blockHitResult.getBlockPos();
            if (!(world.getBlockState(blockPos).getBlock() instanceof FluidBlock)) {
                return TypedActionResult.pass(itemStack);
            } else if (world.canPlayerModifyAt(user, blockPos) && user.canPlaceOn(blockPos, blockHitResult.getSide(), itemStack)) {
                SubmarineEntity boatEntity = new SubmarineEntity(AtlantisEntities.SUBMARINE, world);
                boatEntity.setPosition(hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z);
                boatEntity.setYaw(user.getYaw());
                world.spawnEntity(boatEntity);
                world.emitGameEvent(user, GameEvent.ENTITY_PLACE, new BlockPos(hitResult.getPos()));
                if (!user.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                user.incrementStat(Stats.USED.getOrCreateStat(this));
                world.emitGameEvent(GameEvent.ENTITY_PLACE, user);
                return TypedActionResult.consume(itemStack);
            } else {
                return TypedActionResult.fail(itemStack);
            }
        }
    }

    public TypedActionResult<ItemStack> usee(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (hitResult.getType() == net.minecraft.util.hit.HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        } else {
            Vec3d vec3d = user.getRotationVec(1.0F);
            List<Entity> list = world.getOtherEntities(user, user.getBoundingBox().stretch(vec3d.multiply(5.0D)).expand(1.0D), RIDERS);
            if (!list.isEmpty()) {
                Vec3d vec3d2 = user.getEyePos();

                for (Entity entity : list) {
                    Box box = entity.getBoundingBox().expand(entity.getTargetingMargin());
                    if (box.contains(vec3d2)) {
                        return TypedActionResult.pass(itemStack);
                    }
                }
            }

            //if (hitResult.getType() == HitResult.Type.BLOCK) {
                SubmarineEntity boatEntity = new SubmarineEntity(AtlantisEntities.SUBMARINE, world);
                boatEntity.setPosition(hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z);
                boatEntity.setYaw(user.getYaw());
//                if (!world.isSpaceEmpty(boatEntity, boatEntity.getBoundingBox())) {
//                    return TypedActionResult.fail(itemStack);
//                } else
                {
                    if (!world.isClient) {
                        world.spawnEntity(boatEntity);
                        world.emitGameEvent(user, GameEvent.ENTITY_PLACE, new BlockPos(hitResult.getPos()));
                        if (!user.getAbilities().creativeMode) {
                            itemStack.decrement(1);
                        }
                    }

                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    return TypedActionResult.success(itemStack, world.isClient());
                }
            //} else {
                //return TypedActionResult.pass(itemStack);
            //}
        }
    }
    static {
        RIDERS = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::collides);
    }
}