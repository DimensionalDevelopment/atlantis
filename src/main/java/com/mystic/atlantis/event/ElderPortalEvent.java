package com.mystic.atlantis.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.mystic.atlantis.init.BlockInit;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;

public class ElderPortalEvent implements ServerEntityCombatEvents.AfterKilledOtherEntity {
    @Override
    public void afterKilledOtherEntity(ServerWorld serverWorld, Entity entity, LivingEntity livingEntity) {
        if (livingEntity instanceof ElderGuardianEntity && livingEntity.world.getNonSpectatingEntities(ElderGuardianEntity.class, livingEntity.getVisibilityBoundingBox().stretch(100, 100, 100)).stream().noneMatch(e -> e.isDead() && entity != livingEntity)) {
            World world = livingEntity.world;
            BlockPos pos = livingEntity.getBlockPos();
            world.getBlockState(pos).getBlock();
            world.setBlockState(pos, BlockInit.ATLANTIS_PORTAL.getDefaultState());
        }
    }
}
