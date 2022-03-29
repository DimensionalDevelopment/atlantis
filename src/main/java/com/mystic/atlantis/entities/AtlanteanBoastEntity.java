package com.mystic.atlantis.entities;

import com.mystic.atlantis.init.BlockInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class AtlanteanBoastEntity extends Boat {
    public AtlanteanBoastEntity(Level level) {
        super(level);
    }

    @Override
    public Item getDropItem() {
        return BlockInit.ATLANTEAN_BOAT.get();
    }
}
