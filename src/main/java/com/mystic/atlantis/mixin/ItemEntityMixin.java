package com.mystic.atlantis.mixin;

import com.mystic.atlantis.TagsInit;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends EntityMixin {
    @Shadow public abstract ItemStack getItem();

    @Inject(method = "setUnderwaterMovement", at = @At("HEAD"), cancellable = true)
    public void checkOrichaclum(CallbackInfo callbackInfo) {
        if(getItem().is(TagsInit.Item.CAN_ITEM_SINK)) {
            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(vec3.x * (double) 0.99f, vec3.y - (double) (vec3.y < (double) 0.06f ? 5.0E-4f : 0.0f), vec3.z * (double) 0.99f);
            callbackInfo.cancel();
        }
    }

}
