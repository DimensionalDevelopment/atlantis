package com.mystic.atlantis.items.tools;

import com.mystic.atlantis.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = Reference.MODID, bus = EventBusSubscriber.Bus.GAME)
public class HammerAoEHandler {

    private static final ThreadLocal<Boolean> IN_AOE = ThreadLocal.withInitial(() -> false);

    @SubscribeEvent
    public static void onBreak(BlockEvent.BreakEvent e) {
        if (!(e.getPlayer() instanceof ServerPlayer sp)) return;
        if (Boolean.TRUE.equals(IN_AOE.get())) return;     // avoid recursion

        ItemStack held = sp.getMainHandItem();
        if (!(held.getItem() instanceof HammerItem hammer)) return;

        ServerLevel level = sp.serverLevel();
        BlockPos origin = e.getPos();

        Direction face = getHitFaceServer(sp, origin);
        if (face == null) {
            face = sp.getDirection().getOpposite();
        }

        try {
            IN_AOE.set(true);
            hammer.minePlaneAround(level, origin, face, sp, held);
        } finally {
            IN_AOE.set(false);
        }
    }

    private static Direction getHitFaceServer(ServerPlayer sp, BlockPos origin) {
        HitResult hr = sp.pick(6.0D, 0.0F, false);
        if (hr instanceof BlockHitResult bhr && hr.getType() == HitResult.Type.BLOCK) {
            if (bhr.getBlockPos().equals(origin)) {
                return bhr.getDirection();
            }
        }
        return null;
    }
}
