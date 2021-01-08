package com.mystic.atlantis.event;

import com.mystic.atlantis.init.BlockInit;
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ElderPortalEvent
{
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {

        BlockPos BiomePos = event.getEntity().getBlockPos();

        if (event.getEntity() instanceof ElderGuardianEntity && event.getEntity().world.getNonSpectatingEntities(ElderGuardianEntity.class, event.getEntity().getVisibilityBoundingBox().stretch(100, 100, 100)).stream().filter(entity -> !entity.dead && entity != event.getEntity()).count() == 0) {
            World world = event.getEntity().world;
            BlockPos pos = event.getEntity().getBlockPos();
            world.getBlockState(pos).getBlock();
            world.setBlockState(pos, BlockInit.ATLANTIS_PORTAL.get().getDefaultState());
        }

    }
}
