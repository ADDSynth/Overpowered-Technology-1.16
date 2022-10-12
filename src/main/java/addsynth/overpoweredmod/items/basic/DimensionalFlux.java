package addsynth.overpoweredmod.items.basic;

import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.items.OverpoweredItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public final class DimensionalFlux extends OverpoweredItem {

  public DimensionalFlux(){
    super(Names.DIMENSIONAL_FLUX);
  }
  
  @Override
  public ITextComponent getName(ItemStack stack){
    return ((IFormattableTextComponent)super.getName(stack)).withStyle(TextFormatting.LIGHT_PURPLE);
  }

}
