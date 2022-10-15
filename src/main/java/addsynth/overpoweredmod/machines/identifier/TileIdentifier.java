package addsynth.overpoweredmod.machines.identifier;

import javax.annotation.Nullable;
import addsynth.core.game.item.ItemUtil;
import addsynth.core.util.game.data.AdvancementUtil;
import addsynth.core.util.java.ArrayUtil;
import addsynth.core.util.player.PlayerUtil;
import addsynth.energy.lib.tiles.machines.TileStandardWorkMachine;
import addsynth.overpoweredmod.assets.CustomAdvancements;
import addsynth.overpoweredmod.assets.CustomStats;
import addsynth.overpoweredmod.compatability.CompatabilityManager;
import addsynth.overpoweredmod.compatability.curios.RingEffects;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import addsynth.overpoweredmod.items.UnidentifiedItem;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public final class TileIdentifier extends TileStandardWorkMachine implements INamedContainerProvider {

  private static final Item[] unidentified_armor = ArrayUtil.combine_arrays(
    OverpoweredItems.unidentified_armor[0],
    OverpoweredItems.unidentified_armor[1],
    OverpoweredItems.unidentified_armor[2],
    OverpoweredItems.unidentified_armor[3],
    OverpoweredItems.unidentified_armor[4]
  );
  private static final Item[] rings = {
    OverpoweredItems.ring_0, OverpoweredItems.ring_1, OverpoweredItems.ring_2, OverpoweredItems.ring_3
  };
  public static final Item[] input_filter = CompatabilityManager.are_rings_enabled() ?
    ArrayUtil.combine_arrays(unidentified_armor, rings) : unidentified_armor;

  public TileIdentifier(){
    super(Tiles.IDENTIFIER, 1, input_filter, 1, MachineValues.identifier);
  }

  @Override
  protected final boolean can_work(){
    final ItemStack input = inventory.getInputInventory().getStackInSlot(0);
    final ItemStack output = inventory.getOutputInventory().getStackInSlot(0);
    return input.isEmpty() == false && output.isEmpty();
  }

  @Override
  protected final void perform_work(){
    final ItemStack input = inventory.getWorkingInventory().getStackInSlot(0);
    if(input.isEmpty() == false){ // safety feature? couldn't hurt I guess. But getItem() returns AIR for Empty Itemstacks.
      if(input.getItem() instanceof UnidentifiedItem){
      
        final UnidentifiedItem item = (UnidentifiedItem)(input.getItem());
        if(item.ring_id >= 0){
          // Identify Ring
          ItemStack stack = null;
          switch(item.ring_id){
            case 0: stack = new ItemStack(OverpoweredItems.magic_ring_0); break;
            case 1: stack = new ItemStack(OverpoweredItems.magic_ring_1); break;
            case 2: stack = new ItemStack(OverpoweredItems.magic_ring_2); break;
            case 3: stack = new ItemStack(OverpoweredItems.magic_ring_3); break;
          }
          if(stack != null){
            RingEffects.set_ring_effects(stack);
            inventory.getOutputInventory().setStackInSlot(0, stack);
          }
        }
        else{
          // Identify Armor
          final ItemStack stack = new ItemStack(ItemUtil.get_armor(item.armor_material, item.equipment_type), 1);
          ArmorEffects.enchant(stack);
          inventory.getOutputInventory().setStackInSlot(0, stack);
        }
        
        // Award Advancement to Player
        final ServerPlayerEntity player = PlayerUtil.getPlayer(level, last_used_by);
        if(player != null){
          AdvancementUtil.grantAdvancement(player, CustomAdvancements.IDENTIFY_SOMETHING);
          player.awardStat(CustomStats.ITEMS_IDENTIFIED);
        }
      }
      inventory.getWorkingInventory().setEmpty();
    }
  }

  @Override
  public void load(final BlockState blockstate, final CompoundNBT nbt){
    super.load(blockstate, nbt);
    loadPlayerData(nbt);
  }

  @Override
  public CompoundNBT save(final CompoundNBT nbt){
    super.save(nbt);
    savePlayerData(nbt);
    return nbt;
  }

  @Override
  @Nullable
  public Container createMenu(int id, PlayerInventory player_inventory, PlayerEntity player){
    setPlayerAccessed(player);
    return new ContainerIdentifier(id, player_inventory, this);
  }

  @Override
  public ITextComponent getDisplayName(){
    return new TranslationTextComponent(getBlockState().getBlock().getDescriptionId());
  }

}
