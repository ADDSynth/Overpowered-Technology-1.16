package addsynth.overpoweredmod.machines.plasma_generator;

import javax.annotation.Nullable;
import addsynth.core.game.inventory.IOutputInventory;
import addsynth.core.game.inventory.OutputInventory;
import addsynth.energy.lib.tiles.machines.TilePassiveMachine;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TilePlasmaGenerator extends TilePassiveMachine implements IOutputInventory, INamedContainerProvider {

  private final OutputInventory output_inventory;

  public TilePlasmaGenerator(){
    super(Tiles.PLASMA_GENERATOR, MachineValues.plasma_generator);
    output_inventory = OutputInventory.create(this, 1);
  }

  @Override
  protected final void perform_work(){
    output_inventory.insertItem(0, new ItemStack(OverpoweredItems.plasma), false);
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerPlasmaGenerator(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getDescriptionId());
  }

  @Override
  public void onInventoryChanged(){
    // no need to react to inventory change
  }

  @Override
  public void drop_inventory(){
    output_inventory.drop_in_world(level, worldPosition);
  }

  @Override
  public OutputInventory getOutputInventory(){
    return output_inventory;
  }

}
