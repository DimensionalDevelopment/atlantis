package com.mystic.atlantis.blocks.plants;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class Algae extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty UP = PipeBlock.UP;
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = PipeBlock.PROPERTY_BY_DIRECTION.entrySet().stream().filter((facingProperty) -> {
        return facingProperty.getKey() != Direction.DOWN;
    }).collect(Util.toMap());
    private static final VoxelShape UP_AABB = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_AABB = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    private static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);

    private final Map<BlockState, VoxelShape> stateToShapeMap;

    private static final Property<Boolean> WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public Algae(Properties properties) {
        super(properties
                .randomTicks()
                .strength(0.2F, 0.4F)
                .sound(SoundType.GRASS)
                .requiresCorrectToolForDrops()
                .noCollission()
                .noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(UP, Boolean.FALSE).setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(WATERLOGGED, Boolean.TRUE));
        this.stateToShapeMap = ImmutableMap.copyOf(this.stateDefinition.getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), Algae::getShapeForState)));
    }

    private static VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelshape = Shapes.empty();
        if (state.getValue(UP)) {
            voxelshape = UP_AABB;
        }

        if (state.getValue(NORTH)) {
            voxelshape = Shapes.or(voxelshape, SOUTH_AABB);
        }

        if (state.getValue(SOUTH)) {
            voxelshape = Shapes.or(voxelshape, NORTH_AABB);
        }

        if (state.getValue(EAST)) {
            voxelshape = Shapes.or(voxelshape, WEST_AABB);
        }

        if (state.getValue(WEST)) {
            voxelshape = Shapes.or(voxelshape, EAST_AABB);
        }

        return voxelshape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return this.stateToShapeMap.get(state);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if(OnlyWater(worldIn, pos, state)) {
            return this.getBlocksAttachedTo(this.func_196545_h(state, worldIn, pos));
        }else{
            return false;
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

    public boolean canBlockStay(LevelReader worldReader, BlockPos pos, BlockState state) {
        return canPlaceBlockAt(worldReader, pos);
    }

    public boolean canPlaceBlockAt(LevelReader worldReader, BlockPos pos) {
        return worldReader.getBlockState(pos.above()).getMaterial() != Material.WATER;
    }

    private boolean getBlocksAttachedTo(BlockState state) {
        return this.countBlocksAlgaeIsAttachedTo(state) > 0;
    }

    private int countBlocksAlgaeIsAttachedTo(BlockState state) {
        int i = 0;

        for(BooleanProperty booleanproperty : FACING_TO_PROPERTY_MAP.values()) {
            if (state.getValue(booleanproperty)) {
                ++i;
            }
        }

        return i;
    }

    private boolean hasAttachment(BlockGetter blockReader, BlockPos pos, Direction direction) {
        if (direction == Direction.DOWN) {
            return false;
        } else {
            BlockPos blockpos = pos.relative(direction);
            if (canAttachTo(blockReader, blockpos, direction)) {
                return true;
            } else if (direction.getAxis() == Direction.Axis.Y) {
                return false;
            } else {
                BooleanProperty booleanproperty = FACING_TO_PROPERTY_MAP.get(direction);
                BlockState blockstate = blockReader.getBlockState(pos.above());
                return blockstate.is(this) && blockstate.getValue(booleanproperty);
            }
        }
    }

    public static boolean canAttachTo(BlockGetter blockReader, BlockPos worldIn, Direction neighborPos) {
        BlockState blockstate = blockReader.getBlockState(worldIn);
        return Block.isFaceFull(blockstate.getCollisionShape(blockReader, worldIn), neighborPos.getOpposite());
    }

    private BlockState func_196545_h(BlockState state, BlockGetter blockReader, BlockPos pos) {
        BlockPos blockpos = pos.above();
        if (state.getValue(UP)) {
            state = state.setValue(UP, canAttachTo(blockReader, blockpos, Direction.DOWN));
        }

        BlockState blockstate = null;

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BooleanProperty booleanproperty = getPropertyFor(direction);
            if (state.getValue(booleanproperty)) {
                boolean flag = this.hasAttachment(blockReader, pos, direction);
                if (!flag) {
                    if (blockstate == null) {
                        blockstate = blockReader.getBlockState(blockpos);
                    }

                    flag = blockstate.is(this) && blockstate.getValue(booleanproperty);
                }

                state = state.setValue(booleanproperty, flag);
            }
        }

        return state;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.DOWN) {
            return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        } else {
            BlockState blockstate = this.func_196545_h(stateIn, worldIn, currentPos);
            return !this.getBlocksAttachedTo(blockstate) ? Blocks.WATER.defaultBlockState() : blockstate;
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (worldIn.random.nextInt(4) == 0 && worldIn.hasChunkAt(pos)) { // Forge: check area to prevent loading unloaded chunks
            Direction direction = Direction.getRandom(random);
            BlockPos blockpos = pos.above();
            if (direction.getAxis().isHorizontal() && !state.getValue(getPropertyFor(direction))) {
                if (this.hasAlgaeBelow(worldIn, pos)) {
                    BlockPos blockpos4 = pos.relative(direction);
                    BlockState blockstate4 = worldIn.getBlockState(blockpos4);
                    if (isWater(blockstate4, blockpos4)) {
                        Direction direction3 = direction.getClockWise();
                        Direction direction4 = direction.getCounterClockWise();
                        boolean flag = state.getValue(getPropertyFor(direction3));
                        boolean flag1 = state.getValue(getPropertyFor(direction4));
                        BlockPos blockpos2 = blockpos4.relative(direction3);
                        BlockPos blockpos3 = blockpos4.relative(direction4);
                        if (flag && canAttachTo(worldIn, blockpos2, direction3)) {
                            worldIn.setBlock(blockpos4, this.defaultBlockState().setValue(getPropertyFor(direction3), Boolean.TRUE), 2);
                        } else if (flag1 && canAttachTo(worldIn, blockpos3, direction4)) {
                            worldIn.setBlock(blockpos4, this.defaultBlockState().setValue(getPropertyFor(direction4), Boolean.TRUE), 2);
                        } else {
                            Direction direction1 = direction.getOpposite();
                            if (flag && isWaterBlock(worldIn, blockpos2) && canAttachTo(worldIn, pos.relative(direction3), direction1)) {
                                worldIn.setBlock(blockpos2, this.defaultBlockState().setValue(getPropertyFor(direction1), Boolean.TRUE), 2);
                            } else if (flag1 && isWaterBlock(worldIn, blockpos3) && canAttachTo(worldIn, pos.relative(direction4), direction1)) {
                                worldIn.setBlock(blockpos3, this.defaultBlockState().setValue(getPropertyFor(direction1), Boolean.TRUE), 2);
                            } else if ((double)worldIn.random.nextFloat() < 0.05D && canAttachTo(worldIn, blockpos4.above(), Direction.UP)) {
                                worldIn.setBlock(blockpos4, this.defaultBlockState().setValue(UP, Boolean.TRUE), 2);
                            }
                        }
                    } else if (canAttachTo(worldIn, blockpos4, direction)) {
                        worldIn.setBlock(pos, state.setValue(getPropertyFor(direction), Boolean.TRUE), 2);
                    }

                }
            } else {
                if (direction == Direction.UP && pos.getY() < 255) {
                    if (this.hasAttachment(worldIn, pos, direction)) {
                        worldIn.setBlock(pos, state.setValue(UP, Boolean.TRUE), 2);
                        return;
                    }

                    if (isWaterBlock(worldIn, blockpos)) {
                        if (!this.hasAlgaeBelow(worldIn, pos)) {
                            return;
                        }

                        BlockState blockstate3 = state;

                        for(Direction direction2 : Direction.Plane.HORIZONTAL) {
                            if (random.nextBoolean() || !canAttachTo(worldIn, blockpos.relative(direction2), Direction.UP)) {
                                blockstate3 = blockstate3.setValue(getPropertyFor(direction2), Boolean.FALSE);
                            }
                        }

                        if (this.isFacingCardinal(blockstate3)) {
                            worldIn.setBlock(blockpos, blockstate3, 2);
                        }

                        return;
                    }
                }

                if (pos.getY() > 0) {
                    BlockPos blockpos1 = pos.below();
                    BlockState blockstate = worldIn.getBlockState(blockpos1);
                    boolean isWater = isWater(blockstate, blockpos1);
                    if (isWater || blockstate.is(this)) {
                        BlockState blockstate1 = isWater ? this.defaultBlockState() : blockstate;
                        BlockState blockstate2 = this.func_196544_a(state, blockstate1, random);
                        if (blockstate1 != blockstate2 && this.isFacingCardinal(blockstate2)) {
                            worldIn.setBlock(blockpos1, blockstate2, 2);
                        }
                    }
                }

            }
        }
    }

    public static boolean isWaterBlock(Level worldIn, BlockPos pos)
    {
        return isWater(worldIn.getBlockState(pos), pos);
    }

    public static boolean isWater(BlockState state, BlockPos pos)
    {
        return state.getMaterial() == Material.WATER;
    }

    private BlockState func_196544_a(BlockState state, BlockState state2, Random rand) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (rand.nextBoolean()) {
                BooleanProperty booleanproperty = getPropertyFor(direction);
                if (state.getValue(booleanproperty)) {
                    state2 = state2.setValue(booleanproperty, Boolean.TRUE);
                }
            }
        }

        return state2;
    }

    private boolean isFacingCardinal(BlockState state) {
        return state.getValue(NORTH) || state.getValue(EAST) || state.getValue(SOUTH) || state.getValue(WEST);
    }

    private boolean hasAlgaeBelow(BlockGetter blockReader, BlockPos pos) {
        int i = 4;
        Iterable<BlockPos> iterable = BlockPos.betweenClosed(pos.getX() - 4, pos.getY() - 1, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 1, pos.getZ() + 4);
        int j = 5;

        for(BlockPos blockpos : iterable) {
            if (blockReader.getBlockState(blockpos).is(this)) {
                --j;
                if (j <= 0) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        BlockState blockstate = useContext.getLevel().getBlockState(useContext.getClickedPos());
        if (blockstate.is(this)) {
            return this.countBlocksAlgaeIsAttachedTo(blockstate) < FACING_TO_PROPERTY_MAP.size();
        } else {
            return super.canBeReplaced(state, useContext);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        boolean flag = blockstate.is(this);
        BlockState blockstate1 = flag ? blockstate : this.defaultBlockState();

        for(Direction direction : context.getNearestLookingDirections()) {
            if (direction != Direction.DOWN) {
                BooleanProperty booleanproperty = getPropertyFor(direction);
                boolean flag1 = flag && blockstate.getValue(booleanproperty);
                if (!flag1 && this.hasAttachment(context.getLevel(), context.getClickedPos(), direction)) {
                    return blockstate1.setValue(booleanproperty, Boolean.TRUE);
                }
            }
        }

        return flag ? blockstate1 : null;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 255;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        switch(rot) {
            case CLOCKWISE_180:
                return state.setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(EAST)).setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST)).setValue(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH));
            default:
                return state;
        }
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        switch(mirrorIn) {
            case LEFT_RIGHT:
                return state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
            case FRONT_BACK:
                return state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
            default:
                return super.mirror(state, mirrorIn);
        }
    }

    public static BooleanProperty getPropertyFor(Direction side) {
        return FACING_TO_PROPERTY_MAP.get(side);
    }
}
