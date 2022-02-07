package com.mystic.atlantis.blocks.blockentities.plants;

import com.mystic.atlantis.blocks.blockentities.registry.TileRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.mystic.atlantis.blocks.plants.UnderwaterFlower.WATERLOGGED;

public class TuberUpBlock extends PlantBlock implements BlockEntityProvider, Waterloggable {
    public TuberUpBlock() {
        super(Settings.of(Material.PLANT)
                        .nonOpaque()
                        .noCollision()
                        .requiresTool()
                        .sounds(BlockSoundGroup.GRASS)
                        .strength(0.2f, 0.3f)
                        .ticksRandomly());
        this.getDefaultState().with(WATERLOGGED, Boolean.TRUE);
    }


    public boolean canBlockStay(WorldView worldReader, BlockPos pos, BlockState state) {
        return canPlaceBlockAt(worldReader, pos);
    }

    public boolean blockTypes(BlockState blockState){
        return blockState.getBlock() == Blocks.GRAVEL || blockState.getBlock() == Blocks.SANDSTONE || blockState.getBlock() == Blocks.GRASS || blockState.getBlock() == Blocks.DIRT || blockState.getBlock() == Blocks.SAND;
    }

    public boolean canPlaceBlockAt(WorldView worldReader, BlockPos pos) {
        BlockState state = worldReader.getBlockState(pos.down());

        if (worldReader.getBlockState(pos.up()).getMaterial() != Material.WATER)
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
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    protected boolean canPlantOnTop(BlockState state, BlockView worldIn, BlockPos pos) {
        return !state.getCollisionShape(worldIn, pos).getFace(Direction.UP).isEmpty() || state.isSideSolidFullSquare(worldIn, pos, Direction.UP);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        if(OnlyWater(worldIn, pos, state)){
            return this.canPlantOnTop(worldIn.getBlockState(blockpos), worldIn, blockpos);
        }else{
            return false;
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        {
            BlockState blockstate = context.getWorld().getBlockState(context.getBlockPos());
            if (blockstate.isOf(this)) {
                return blockstate;
            } else {
                FluidState fluidstate = context.getWorld().getFluidState(context.getBlockPos());
                boolean flag = fluidstate.getFluid() == Fluids.WATER;
                return super.getPlacementState(context).with(WATERLOGGED, Boolean.valueOf(flag));
            }
        }
    }

    public boolean OnlyWater(WorldView worldReader, BlockPos pos, BlockState state) {
        return !worldReader.getBlockState(pos).isIn(getAir()) || !this.canBlockStay(worldReader, pos, state);
    }

    public Tag<Block> getAir(){
        Tag<Block> air = new Tag<Block>() {
            @Override
            public boolean contains(Block element) {
                return true;
            }

            @Override
            public List<Block> values() {
                List<Block> air2 = new ArrayList<Block>();
                air2.add(Blocks.AIR);
                return air2;
            }
        };
        return air;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return TileRegistry.TUBER_UP_TILE.instantiate(pos, state);
    }
}
