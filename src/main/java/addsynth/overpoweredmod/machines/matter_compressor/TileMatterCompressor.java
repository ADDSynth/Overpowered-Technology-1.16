package addsynth.overpoweredmod.machines.matter_compressor;

import addsynth.core.game.RegistryUtil;
import addsynth.core.game.inventory.SlotData;
import addsynth.core.game.tiles.TileMachine;
import addsynth.energy.lib.main.IEnergyConsumer;
import addsynth.energy.lib.main.Receiver;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileMatterCompressor extends TileMachine implements ITickableTileEntity, IEnergyConsumer, INamedContainerProvider {

  private boolean changed;
  private int matter;
  private final Receiver energy;

  public static final SlotData[] slot_data = {
    new SlotData(RegistryUtil.getItemBlock(OverpoweredBlocks.black_hole), 1),
    new SlotData()
  };

  public TileMatterCompressor(){
    super(Tiles.MATTER_COMPRESSOR, slot_data, 1);
    energy = new Receiver();
  }

  @Override
  public final void tick(){
    final ItemStack input = input_inventory.getStackInSlot(1);
    if(input.isEmpty() == false){
      final ItemStack unimatter = new ItemStack(OverpoweredItems.unimatter, 1);
      
      if(input.getItem() == OverpoweredItems.unimatter){
        if(output_inventory.can_add(0, unimatter)){
          output_inventory.add(0, unimatter);
          input_inventory.decrease(1);
          changed = true;
        }
      }
      else{
        if(input_inventory.getStackInSlot(0).getItem() == OverpoweredBlocks.black_hole.asItem()){
          matter += input.getCount();
          input_inventory.setStackInSlot(1, ItemStack.EMPTY);
          changed = true;
          final int max_matter = Config.max_matter.get();
          if(matter >= max_matter){
            if(output_inventory.can_add(0, unimatter)){
              output_inventory.add(0, unimatter);
              matter -= max_matter;
            }
          }
        }
      }
    }
    if(energy.tick()){
      changed = true;
    }
    if(changed){
      update_data();
      changed = false;
    }
  }

  @Override
  public final void onInventoryChanged(){
    changed = true;
  }

  /** Only used by the Gui. */
  public final String getMatter(){
    return Integer.toString(matter);
  }

  /** Only used by the Gui. */
  public final float getProgress(){
    return (float)matter / Config.max_matter.get();
  }

  @Override
  public final void load(final BlockState blockstate, final CompoundNBT nbt){
    super.load(blockstate, nbt);
    matter = nbt.getInt("Matter");
    energy.loadFromNBT(nbt);
  }

  @Override
  public final CompoundNBT save(final CompoundNBT nbt){
    super.save(nbt);
    nbt.putInt("Matter", matter);
    energy.saveToNBT(nbt);
    return nbt;
  }

  @Override
  public final Receiver getEnergy(){
    return energy;
  }

  @Override
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new MatterCompressorContainer(id, player_inventory, this);
  }
  
  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getDescriptionId());
  }

}
