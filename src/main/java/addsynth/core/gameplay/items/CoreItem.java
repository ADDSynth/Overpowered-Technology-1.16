package addsynth.core.gameplay.items;

import addsynth.core.ADDSynthCore;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/** This should only be used to construct base items for {@link ADDSynthCore}! */
public class CoreItem extends Item {

  public CoreItem(final ResourceLocation name){
    super(new Item.Properties().tab(ADDSynthCore.creative_tab));
    setRegistryName(name);
  }

  public CoreItem(final Item.Properties properties, final ResourceLocation name){
    super(properties.tab(ADDSynthCore.creative_tab));
    setRegistryName(name);
  }

}
