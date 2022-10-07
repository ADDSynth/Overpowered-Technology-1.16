package addsynth.overpoweredmod.machines.fusion.control;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.game.RegistryUtil;
import addsynth.energy.lib.blocks.MachineBlock;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.Names;
import addsynth.overpoweredmod.machines.data_cable.DataCable;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;

public final class FusionControlUnit extends MachineBlock {

  public FusionControlUnit(){
    super(MaterialColor.WOOL);
    RegistryUtil.register_block(this, Names.FUSION_CONTROL_UNIT, CreativeTabs.creative_tab);
    DataCable.addAttachableBlock(this);
  }

  @Override
  public boolean hasTileEntity(BlockState state){
    return false;
  }

  @Override
  public final void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new TranslationTextComponent("gui.overpowered.tooltip.fusion_machine"));
  }

}
