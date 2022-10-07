package addsynth.material.blocks;

import addsynth.core.game.RegistryUtil;
import addsynth.material.ADDSynthMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

public final class GemBlock extends Block {
	
  public GemBlock(final String name, final MaterialColor color){
    super(Block.Properties.of(Material.STONE, color).strength(5.0f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(2).sound(SoundType.STONE));
    RegistryUtil.register_block(this, new ResourceLocation(ADDSynthMaterials.MOD_ID, name), ADDSynthMaterials.creative_tab);
  }

}
