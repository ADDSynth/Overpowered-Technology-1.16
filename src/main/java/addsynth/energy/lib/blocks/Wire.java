package addsynth.energy.lib.blocks;

import javax.annotation.Nullable;
import addsynth.core.util.block.BlockShape;
import addsynth.core.util.constants.DirectionConstant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public abstract class Wire extends Block implements IWaterLoggable {

  // http://mcforge.readthedocs.io/en/latest/blocks/states/

  private static final BooleanProperty NORTH = BlockStateProperties.NORTH;
  private static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
  private static final BooleanProperty WEST  = BlockStateProperties.WEST;
  private static final BooleanProperty EAST  = BlockStateProperties.EAST;
  private static final BooleanProperty UP    = BlockStateProperties.UP;
  private static final BooleanProperty DOWN  = BlockStateProperties.DOWN;
  private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  private static final double default_min_wire_size =  5.0 / 16;
  private static final double default_max_wire_size = 11.0 / 16;

  protected final VoxelShape[] shapes;

  public Wire(final Block.Properties properties){
    super(properties);
    shapes = makeShapes();
    this.registerDefaultState(this.stateDefinition.any()
       .setValue(NORTH, false).setValue(SOUTH, false).setValue(WEST, false).setValue(EAST, false).setValue(UP, false).setValue(DOWN, false)
       .setValue(WATERLOGGED, false));
  }

  /** Override this method in extended classes to assign different size shapes.
   *  The base Wire class automatically calls this to assign the shapes array.
   */
  protected VoxelShape[] makeShapes(){
    return BlockShape.create_six_sided_binary_voxel_shapes(default_min_wire_size, default_max_wire_size);
  }

  @Override
  public boolean hasTileEntity(BlockState state){
    return true;
  }

  @Override
  @Nullable
  @SuppressWarnings("resource")
  public final BlockState getStateForPlacement(final BlockItemUseContext context){
    return getState(defaultBlockState(), context.getLevel(), context.getClickedPos());
  }

  protected abstract boolean[] get_valid_sides(IBlockReader world, BlockPos pos);

  private final BlockState getState(final BlockState state, final IWorld world, final BlockPos position){
    final boolean[] valid_sides = get_valid_sides(world, position);
    return state.setValue(DOWN,  valid_sides[DirectionConstant.DOWN ]).setValue(UP,    valid_sides[DirectionConstant.UP   ])
                .setValue(NORTH, valid_sides[DirectionConstant.NORTH]).setValue(SOUTH, valid_sides[DirectionConstant.SOUTH])
                .setValue(WEST,  valid_sides[DirectionConstant.WEST ]).setValue(EAST,  valid_sides[DirectionConstant.EAST ])
                .setValue(WATERLOGGED, world.getFluidState(position).getType() == Fluids.WATER);
  }

  @Override
  @SuppressWarnings("deprecation")
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return shapes[BlockShape.getIndex(state)];
  }

  @Override
  @SuppressWarnings("deprecation")
  public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return shapes[BlockShape.getIndex(state)];
  }

  @Override
  @SuppressWarnings("deprecation")
  public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos){
    if(state.getValue(WATERLOGGED)){
      world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
    }
    return getState(state, world, currentPos);
  }

  @Override
  public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos){
    return !state.getValue(WATERLOGGED);
  }

  @Override
  @SuppressWarnings("deprecation")
  public FluidState getFluidState(BlockState state){
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder){
    builder.add(NORTH, SOUTH, WEST, EAST, UP, DOWN, WATERLOGGED);
  }

}
