package addsynth.core.game.tiles;

import addsynth.core.block_network.BlockNetwork;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/** This DOES NOT SAVE any data to the world. It only syncs data from the
 *  server to the client. If you're sending a large amount of data, or are
 *  sending every tick, then it's actually recommended you create your own
 *  network message and send using {@link SimpleChannel#send }.
 * @author ADDSynth
 */
public abstract class TileBaseNoData extends TileAbstractBase {

  public TileBaseNoData(final TileEntityType type){
    super(type);
  }

  @Override
  public final SUpdateTileEntityPacket getUpdatePacket(){
    final CompoundNBT nbtTag = new CompoundNBT();
    save(nbtTag);
    return new SUpdateTileEntityPacket(this.worldPosition, -1, nbtTag);
  }

  @Override
  public final void onDataPacket(final NetworkManager net, final SUpdateTileEntityPacket pkt){
    load(getBlockState(), pkt.getTag());
  }

  /** <p>Helper method to send TileEntity changes to the client.</p>
   *  <p>This should only be called on the server side and should be called when any data changes.
   *     For complex TileEntities that likely has data that changes every tick, we actually recommend
   *     setting a boolean variable to <code>true</code> when any data changes, then check that
   *     boolean variable at the end of the <code>tick()</code> method and call update_data().</p>
   *  <p>For TileEntities which are a part of a {@link BlockNetwork} it is required to override
   *     this so that you instead update the BlockNetwork which then updates each TileEntity manually.</p>
   */
  @SuppressWarnings("null")
  @Override
  public void update_data(){
    if(level != null){
      final BlockState blockstate = getBlockState();
      level.sendBlockUpdated(worldPosition, blockstate, blockstate, Constants.BlockFlags.DEFAULT);
    }
  }

}
