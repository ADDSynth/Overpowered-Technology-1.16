package addsynth.overpoweredmod.items.basic;

import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.items.OverpoweredItem;
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
