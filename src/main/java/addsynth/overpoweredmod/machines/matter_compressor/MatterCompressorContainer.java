package addsynth.overpoweredmod.machines.matter_compressor;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.container.slots.OutputSlot;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public final class MatterCompressorContainer extends TileEntityContainer<TileMatterCompressor> {

  public MatterCompressorContainer(final int id, final PlayerInventory player_inventory, final TileMatterCompressor tile){
    super(Containers.MATTER_COMPRESSOR, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public MatterCompressorContainer(final int id, final PlayerInventory player_inventory, final PacketBuffer data){
    super(Containers.MATTER_COMPRESSOR, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final PlayerInventory player_inventory){
    make_player_inventory(player_inventory, 11, 100);
    addSlot(new InputSlot(tile, 0, TileMatterCompressor.slot_data[0].getFilter(), 1, 83, 24));
    addSlot(new InputSlot(tile, 1, 59, 50));
    addSlot(new OutputSlot(tile, 0, 107, 50));
  }

}
