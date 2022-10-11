package addsynth.overpoweredmod.machines.fusion.control;

import addsynth.overpoweredmod.game.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public final class FusionControlLaserBeam extends Block {

  public FusionControlLaserBeam(){
    super(Block.Properties.of(Material.FIRE).lightLevel((blockstate)->{return 15;}).noCollission());
    setRegistryName(Names.FUSION_CONTROL_LASER_BEAM);
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
    return VoxelShapes.empty();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side){
    return adjacentBlockState.getBlock() == this ? true : super.skipRendering(state, adjacentBlockState, side);
  }

}
