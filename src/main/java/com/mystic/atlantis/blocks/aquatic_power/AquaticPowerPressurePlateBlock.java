package com.mystic.atlantis.blocks.aquatic_power;

import com.mystic.atlantis.blocks.plants.Seabloom;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;

import java.util.Iterator;
import java.util.List;

public class AquaticPowerPressurePlateBlock extends PressurePlateBlock implements SimpleWaterloggedBlock {
    private static final Property<Boolean> WATERLOGGED = Seabloom.WATERLOGGED;
    private static final BooleanProperty POWERED;
    private final PressurePlateBlock.Sensitivity sensitivity;

    public AquaticPowerPressurePlateBlock(PressurePlateBlock.Sensitivity sensitivity, Properties settings) {
        super(sensitivity, settings, BlockSetType.OAK);
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
