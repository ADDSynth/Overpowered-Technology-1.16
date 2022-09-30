package addsynth.core.util.game.tileentity;

import addsynth.core.game.tiles.TileAbstractBase;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityUtil {

  /** Call this in the {@link Block#setPlacedBy} method to set the TileEntity's
   *  {@link TileAbstractBase#owner owner} field. This function automatically handles everything. */
  public static final void setOwner(final World world, final LivingEntity player, final BlockPos position){
    if(player instanceof PlayerEntity){
      final TileEntity tile = world.getBlockEntity(position);
      if(tile instanceof TileAbstractBase){
        ((TileAbstractBase)tile).setOwner((PlayerEntity)player);
      }
    }
  }

}
