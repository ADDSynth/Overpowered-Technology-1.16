package addsynth.energy.lib.energy_network.tiles;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.energy.lib.energy_network.EnergyNetwork;
import net.minecraft.tileentity.TileEntityType;

public abstract class BasicEnergyNetworkTile extends AbstractEnergyNetworkTile {

  public BasicEnergyNetworkTile(final TileEntityType type){
    super(type);
  }

  @Override
  public void tick(){
    if(onServerSide()){
      if(network == null){
        BlockNetworkUtil.create_or_join(level, this, EnergyNetwork::new);
      }
      network.tick(this);
    }
  }

  @Override
  public final void setRemoved(){
    super.setRemoved();
    BlockNetworkUtil.tileentity_was_removed(this, EnergyNetwork::new);
  }

  @Override
  @Nullable
  public final EnergyNetwork getBlockNetwork(){
    return network;
  }

  @Override
  public final void setBlockNetwork(final EnergyNetwork network){
    this.network = network;
  }

}
