package addsynth.core.gameplay.blocks;

import addsynth.core.ADDSynthCore;
import addsynth.core.game.RegistryUtil;
import addsynth.core.gameplay.reference.Names;
import addsynth.core.util.constants.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public final class CautionBlock extends Block {

  public CautionBlock(){
    super(Block.Properties.of(Material.STONE, MaterialColor.COLOR_YELLOW).sound(SoundType.STONE).strength(2.0f, Constants.block_resistance));
    RegistryUtil.register_block(this, Names.CAUTION_BLOCK, ADDSynthCore.creative_tab);
  }

}
