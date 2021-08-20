package addsynth.energy.lib.blocks;

import addsynth.core.inventory.IInventoryUser;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** <p>This is your typical machine-type block with a silver appearance and metallic properties.
 *  <p>THIS DOES NOT REGISTER YOUR BLOCKS! Mods that extend from this class must register
 *     their individual blocks using that mod's {@link addsynth.core.game.RegistryUtil} instance.
 * @author ADDSynth
 */
public abstract class MachineBlock extends Block {

  /** Specify your own Block Properties. Required if block is transparent! */
  public MachineBlock(final Block.Properties properties){
    super(properties);
  }

  /** Standard constructor. SoundType = Metal, and standard block hardness. */
  public MachineBlock(final MaterialColor color){
    super(Block.Properties.of(Material.METAL, color).sound(SoundType.METAL).strength(3.5f, 6.0f));
  }

  @Override
  public boolean hasTileEntity(BlockState state){
    return true;
  }

  @Override
  @SuppressWarnings("deprecation")
   public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving){
    final TileEntity tile = world.getBlockEntity(pos);
    if(tile != null){
      if(tile instanceof IInventoryUser){
        ((IInventoryUser)tile).drop_inventory();
      }
    }
    super.onRemove(state, world, pos, newState, isMoving);
  }
  
}
