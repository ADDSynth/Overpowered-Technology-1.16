package addsynth.overpoweredmod.items;

import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class OverpoweredItem extends Item {

  public OverpoweredItem(final ResourceLocation name){
    super(new Item.Properties().tab(CreativeTabs.creative_tab));
    setRegistryName(name);
  }

  public OverpoweredItem(final ResourceLocation name, final ItemGroup tab){
    super(new Item.Properties().tab(tab));
    setRegistryName(name);
  }

  public OverpoweredItem(final ResourceLocation name, final Item.Properties properties){
    super(properties);
    setRegistryName(name);
  }

}
