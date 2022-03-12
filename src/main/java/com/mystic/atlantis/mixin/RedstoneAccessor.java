package com.mystic.atlantis.mixin;

import net.minecraft.world.level.block.RedStoneWireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RedStoneWireBlock.class)
public interface RedstoneAccessor {
    @Accessor("wiresGivePower")
    boolean getWiresGivePower();

    @Accessor("wiresGivePower")
    void setWiresGivePower(boolean wiresGivePower);
}
