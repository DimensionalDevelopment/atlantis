package com.mystic.atlantis.blocks.blockentities.plants;

import com.mystic.atlantis.blocks.blockentities.registry.TileRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.mystic.atlantis.blocks.plants.UnderwaterFlower.WATERLOGGED;

public class BurntDeepBlock extends BushBlock implements EntityBlock, SimpleWaterloggedBlock {
    public BurntDeepBlock() {
        super(Properties.of(Material.PLANT)
                        .noOcclusion()
                        .noCollission()
                        .sound(SoundType.GRASS)
                        .strength(0.2f, 0.3f)
                        .randomTicks());
        this.defaultBlockState().setValue(WATERLOGGED, Boolean.TRUE);
    }


    public boolean canBlockStay(LevelReader worldReader, BlockPos pos, BlockState state) {
        return canPlaceBlockAt(worldReader, pos);
    }

    public boolean blockTypes(BlockState blockState){
        return blockState.getBlock() == Blocks.GRAVEL || blockState.getBlock() == Blocks.SANDSTONE || blockState.getBlock() == Blocks.GRASS || blockState.getBlock() == Blocks.DIRT || blockState.getBlock() == Blocks.SAND;
    }

    public boolean canPlaceBlockAt(LevelReader worldReader, BlockPos pos) {
        BlockState state = worldReader.getBlockState(pos.below());

        if (worldReader.getBlockState(pos.above()).getMaterial() != Material.WATER)
        {
            return true;
        }
        if(blockTypes(state)) {
            return false;
        }

        return true;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return !state.getCollisionShape(worldIn, pos).getFaceShape(Direction.UP).isEmpty() || state.isFaceSturdy(worldIn, pos, Direction.UP);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        if(OnlyWater(worldIn, pos, state)){
            return this.mayPlaceOn(worldIn.getBlockState(blockpos), worldIn, blockpos);
        }else{
            return false;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        {
            BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
            if (blockstate.is(this)) {
                return blockstate;
            } else {
                FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
                boolean flag = fluidstate.getType() == Fluids.WATER;
                return super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(flag));
            }
        }
    }

    public boolean OnlyWater(LevelReader worldReader, BlockPos pos, BlockState state) {
        return !worldReader.getBlockState(pos).is(getAir()) || !this.canBlockStay(worldReader, pos, state);
    }

    public Tag<Block> getAir(){
        Tag<Block> air = new Tag<Block>() {
            @Override
            public boolean contains(Block element) {
                return true;
            }

            @Override
            public List<Block> getValues() {
                List<Block> air2 = new ArrayList<Block>();
                air2.add(Blocks.AIR);
                return air2;
            }
        };
        return air;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag options) {
        super.appendHoverText(stack, world, tooltip, options);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TileRegistry.BURNT_DEEP_TILE.get().create(pos, state);
    }
}
