package com.mystic.atlantis.blocks.power.atlanteanstone;

import java.util.Iterator;
import java.util.List;

import com.mystic.atlantis.blocks.plants.UnderwaterFlower;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;

public class AtlanteanPressurePlateBlock extends PressurePlateBlock implements SimpleWaterloggedBlock {
    private static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;
    private static final BooleanProperty POWERED;
    private final PressurePlateBlock.Sensitivity sensitivity;

    public AtlanteanPressurePlateBlock(PressurePlateBlock.Sensitivity sensitivity, Properties settings) {
        super(sensitivity, settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false).setValue(WATERLOGGED, false));
        this.sensitivity = sensitivity;
    }

    @Override
    protected int getSignalForState(BlockState targetState) {
        return targetState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    protected BlockState setSignalForState(BlockState targetState, int power) {
        return targetState.setValue(POWERED, power > 0);
    }

    @Override
    protected void playOnSound(LevelAccessor accessor, BlockPos targetPos) {
        if (this.material != Material.WOOD && this.material != Material.NETHER_WOOD) {
            accessor.playSound(null, targetPos, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundSource.BLOCKS, 0.3F, 0.6F);
        } else {
            accessor.playSound(null, targetPos, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON, SoundSource.BLOCKS, 0.3F, 0.8F);
        }

    }

    @Override
    protected void playOffSound(LevelAccessor accessor, BlockPos targetPos) {
        if (this.material != Material.WOOD && this.material != Material.NETHER_WOOD) {
            accessor.playSound(null, targetPos, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundSource.BLOCKS, 0.3F, 0.5F);
        } else {
            accessor.playSound(null, targetPos, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundSource.BLOCKS, 0.3F, 0.7F);
        }

    }

    @Override
    protected int getSignalStrength(Level level, BlockPos targetPos) {
        AABB touchableAABB = TOUCH_AABB.move(targetPos);
        List<? extends Entity> touchingEntities;
        
        switch(this.sensitivity) {
            case EVERYTHING:
                touchingEntities = level.getEntities(null, touchableAABB);
                break;
            case MOBS:
                touchingEntities = level.getEntitiesOfClass(LivingEntity.class, touchableAABB);
                break;
            default:
                return 0;
        }

        if (!touchingEntities.isEmpty()) {
            Iterator<? extends Entity> touchingEntitiesIterator = touchingEntities.iterator();

            while(touchingEntitiesIterator.hasNext()) {
                Entity nextEntity = (Entity)touchingEntitiesIterator.next();
                if (!nextEntity.isIgnoringBlockTriggers()) {
                    return 15;
                }
            }
        }
        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, WATERLOGGED);
    }

    static {
        POWERED = BlockStateProperties.POWERED;
    }
}
