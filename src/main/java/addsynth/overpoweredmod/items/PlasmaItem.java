package addsynth.overpoweredmod.items;

import addsynth.overpoweredmod.game.Names;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public final class PlasmaItem extends OverpoweredItem {

  public PlasmaItem(){
    super(Names.PLASMA);
  }

  @Override
  public ITextComponent getName(ItemStack stack){
    return ((IFormattableTextComponent)super.getName(stack)).withStyle(TextFormatting.AQUA);
  }

}
