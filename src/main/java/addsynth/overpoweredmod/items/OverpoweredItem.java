package addsynth.overpoweredmod.items;

import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class OverpoweredItem extends Item {

  public OverpoweredItem(final String name){
    super(new Item.Properties().tab(CreativeTabs.creative_tab));
    OverpoweredTechnology.registry.register_item(this, name);
  }

  public OverpoweredItem(final String name, final ItemGroup tab){
    super(new Item.Properties().tab(tab));
    OverpoweredTechnology.registry.register_item(this, name);
  }

  public OverpoweredItem(final String name, final Item.Properties properties){
    super(properties);
    OverpoweredTechnology.registry.register_item(this, name);
  }

}
