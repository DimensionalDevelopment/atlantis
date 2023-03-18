package com.mystic.atlantis.entities.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class LatchOntoGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final LivingEntity parentEntity;

    public LatchOntoGoal(Mob livingEntity, Class<T> target, boolean unknown) {
        super(livingEntity, target, unknown);
        this.parentEntity = livingEntity;
    }

    @Override
    public boolean canUse() {
        return parentEntity instanceof PathfinderMob pathfinderMob && !pathfinderMob.isPassenger() && pathfinderMob.getTarget() != null;
    }

    @Override
    public void tick() {
        if(parentEntity instanceof PathfinderMob pathfinderMob) {
            if (!pathfinderMob.isPassenger() && pathfinderMob.getTarget() != null) {
                pathfinderMob.startRiding(pathfinderMob.getTarget());
            }
        }
    }
}
