package addsynth.core.util.constants;

import javax.annotation.Nonnegative;
import addsynth.core.ADDSynthCore;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public final class DirectionConstant {

  public static final int DOWN  = Direction.DOWN.get3DDataValue();
  public static final int UP    = Direction.UP.get3DDataValue();
  public static final int NORTH = Direction.NORTH.get3DDataValue();
  public static final int SOUTH = Direction.SOUTH.get3DDataValue();
  public static final int WEST  = Direction.WEST.get3DDataValue();
  public static final int EAST  = Direction.EAST.get3DDataValue();

  public static final int getOppositeDirection(@Nonnegative final int direction){
    return Direction.from3DDataValue(direction).getOpposite().get3DDataValue(); // OPTIMIZE: Update ealier versions if possible
  }

  public static final Direction getDirection(final BlockPos current_position, final BlockPos adjacent_position){
    if(current_position.equals(adjacent_position)){
      ADDSynthCore.log.error(DirectionConstant.class.getName()+".getDirection(BlockPos, BlockPos) cannot determine direction because both BlockPositions are equal!");
    }
    final int x = MathHelper.clamp(adjacent_position.getX() - current_position.getX(), -1, 1);
    final int y = MathHelper.clamp(adjacent_position.getY() - current_position.getY(), -1, 1);
    final int z = MathHelper.clamp(adjacent_position.getZ() - current_position.getZ(), -1, 1);
    return Direction.fromNormal(x, y, z);
  }

}
