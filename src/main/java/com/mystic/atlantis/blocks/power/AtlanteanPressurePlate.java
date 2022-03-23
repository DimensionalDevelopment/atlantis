package com.mystic.atlantis.blocks.power;

import com.mystic.atlantis.blocks.plants.UnderwaterFlower;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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

import java.util.Iterator;
import java.util.List;

public class AtlanteanPressurePlate extends PressurePlateBlock implements SimpleWaterloggedBlock {
    private static final Property<Boolean> WATERLOGGED = UnderwaterFlower.WATERLOGGED;
    public static final BooleanProperty POWERED;
    private final PressurePlateBlock.Sensitivity sensitivity;

    public AtlanteanPressurePlate(PressurePlateBlock.Sensitivity arg, Properties arg2) {
        super(arg, arg2);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false).setValue(WATERLOGGED, false));
        this.sensitivity = arg;
    }

    @Override
    protected int getSignalForState(BlockState arg) {
        return arg.getValue(POWERED) ? 15 : 0;
    }

    @Override
    protected BlockState setSignalForState(BlockState arg, int i) {
        return arg.setValue(POWERED, i > 0);
    }

    @Override
    protected void playOnSound(LevelAccessor arg, BlockPos arg2) {
        if (this.material != Material.WOOD && this.material != Material.NETHER_WOOD) {
            arg.playSound(null, arg2, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundSource.BLOCKS, 0.3F, 0.6F);
        } else {
            arg.playSound(null, arg2, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON, SoundSource.BLOCKS, 0.3F, 0.8F);
        }

    }

    @Override
    protected void playOffSound(LevelAccessor arg, BlockPos arg2) {
        if (this.material != Material.WOOD && this.material != Material.NETHER_WOOD) {
            arg.playSound(null, arg2, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundSource.BLOCKS, 0.3F, 0.5F);
        } else {
            arg.playSound(null, arg2, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundSource.BLOCKS, 0.3F, 0.7F);
        }

    }

    @Override
    protected int getSignalStrength(Level arg, BlockPos arg2) {
        net.minecraft.world.phys.AABB aABB = TOUCH_AABB.move(arg2);
        List list;
        switch(this.sensitivity) {
            case EVERYTHING:
                list = arg.getEntities(null, aABB);
                break;
            case MOBS:
                list = arg.getEntitiesOfClass(LivingEntity.class, aABB);
                break;
            default:
                return 0;
        }

        if (!list.isEmpty()) {
            Iterator var5 = list.iterator();

            while(var5.hasNext()) {
                Entity entity = (Entity)var5.next();
                if (!entity.isIgnoringBlockTriggers()) {
                    return 15;
                }
            }
        }

        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> arg) {
        arg.add(POWERED, WATERLOGGED);
    }

    static {
        POWERED = BlockStateProperties.POWERED;
    }
}
