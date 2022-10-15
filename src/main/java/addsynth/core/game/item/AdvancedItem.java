package addsynth.core.game.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

// UNUSED: AdvancedItem(ChatFormatting style)
public class AdvancedItem extends Item {

  private final TextFormatting style;

  public AdvancedItem(final ResourceLocation name, final TextFormatting style){
    super(new Item.Properties());
    this.style = style;
    setRegistryName(name);
  }
  
  public AdvancedItem(final ResourceLocation name, final TextFormatting style, final ItemGroup tab){
    super(new Item.Properties().tab(tab));
    this.style = style;
    setRegistryName(name);
  }
  
  public AdvancedItem(final ResourceLocation name, final TextFormatting style, final Item.Properties properties){
    super(properties);
    this.style = style;
    setRegistryName(name);
  }
  
  @Override
  public ITextComponent getName(ItemStack stack){
    return ((IFormattableTextComponent)super.getName(stack)).withStyle(style);
  }

}
