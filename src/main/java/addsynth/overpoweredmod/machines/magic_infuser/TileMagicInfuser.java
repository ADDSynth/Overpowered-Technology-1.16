package addsynth.overpoweredmod.machines.magic_infuser;

import javax.annotation.Nullable;
import addsynth.core.game.inventory.SlotData;
import addsynth.core.recipe.jobs.JobSystem;
import addsynth.energy.lib.tiles.machines.TileStandardWorkMachine;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.machines.Filters;
import addsynth.overpoweredmod.machines.magic_infuser.recipes.MagicInfuserRecipes;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.item.ItemStack;

public final class TileMagicInfuser extends TileStandardWorkMachine implements INamedContainerProvider {

  // TODO: Add an abstract method to IInputInventory so TileEntities that use an InputInventory MUST override it to provide the Item filter.
  private static final SlotData[] getSlotData(){
    return new SlotData[] {
      new SlotData(Items.BOOK),
      new SlotData(Filters.magic_infuser)
    };
  }

  public TileMagicInfuser(){
    super(Tiles.MAGIC_INFUSER, getSlotData(), 1, MachineValues.magic_infuser);
    inventory.setResponder(this);
  }

  @Override
  public final void onInventoryChanged(){
    changed = true;
  }

  @Override
  protected final boolean can_work(){
    return !inventory.getInputInventory().getStackInSlot(0).isEmpty() &&
           !inventory.getInputInventory().getStackInSlot(1).isEmpty() &&
            inventory.getOutputInventory().getStackInSlot(0).isEmpty();
  }

  @Override
  protected final void perform_work(){
    final ItemStack input = inventory.getWorkingInventory().getStackInSlot(1);
    final ItemStack enchant_book = MagicInfuserRecipes.getResult(input);
    inventory.getOutputInventory().setStackInSlot(0, enchant_book);
    inventory.getWorkingInventory().setEmpty();
    inventory.recheck();
  }

  @Override
  public final int getJobs(){
    return JobSystem.getMaxNumberOfJobs(inventory.getInputInventory().getItemStacks(), true);
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    return new ContainerMagicInfuser(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getDescriptionId());
  }

}
