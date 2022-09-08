package addsynth.energy.lib.energy_network.tiles;

import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.core.game.tiles.TileBase;
import addsynth.energy.gameplay.machines.energy_wire.EnergyWire;
import addsynth.energy.lib.energy_network.EnergyNetwork;
import addsynth.energy.lib.tiles.energy.TileEnergyBattery;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

/** TileEntities that want to be part of the Energy Network, but NOT UPDATE IT,
 * are derived from this. This is mainly used by {@link TileEnergyBattery}s.
 * This is also used to determine which blocks the {@link EnergyWire} can
 * connect to.
 * @author ADDSynth
 */
public abstract class AbstractEnergyNetworkTile extends TileBase
  implements IBlockNetworkUser<EnergyNetwork>, ITickableTileEntity {

  protected EnergyNetwork network;

  public AbstractEnergyNetworkTile(final TileEntityType type){
    super(type);
  }

}
