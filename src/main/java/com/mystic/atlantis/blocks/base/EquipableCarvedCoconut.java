package com.mystic.atlantis.blocks.base;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class EquipableCarvedCoconut extends CarvedCoconut implements Equipable {
   public EquipableCarvedCoconut(BlockBehaviour.Properties p_289677_) {
      super(p_289677_);
   }

   public EquipmentSlot getEquipmentSlot() {
      return EquipmentSlot.HEAD;
   }
}