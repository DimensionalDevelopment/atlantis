package com.mystic.atlantis.entities.goal;

import java.util.EnumSet;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class JellyFishFloatGoal extends Goal {

    private final Mob mob;
    private int tickDelay;

    public JellyFishFloatGoal(Mob arg) {
        this.mob = arg;
        this.tickDelay = this.mob.getRandom().nextInt(61);
        this.setFlags(EnumSet.of(Goal.Flag.JUMP));
        arg.getNavigation().setCanFloat(true);
    }

    @Override
    public boolean canUse() {
        return this.mob.isInWater() && this.mob.getFluidHeight(FluidTags.WATER) > this.mob.getFluidJumpThreshold() || this.mob.isInLava();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (--this.tickDelay <= 0) {
            this.tickDelay = this.mob.getRandom().nextInt(61);
            for (int i = 0; i < 3; i++) {
                this.mob.getJumpControl().jump();
            }
        }
    }
}
