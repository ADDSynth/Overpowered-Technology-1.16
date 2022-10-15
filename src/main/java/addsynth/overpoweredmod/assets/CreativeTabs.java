package addsynth.overpoweredmod.assets;

import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public final class CreativeTabs {

  public static final ItemGroup creative_tab = new ItemGroup("overpowered")
  {
    @Override
    public final ItemStack makeIcon(){
      return new ItemStack(OverpoweredBlocks.inverter, 1);
    }
  };

}
