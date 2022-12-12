package addsynth.material.blocks;

import javax.annotation.Nullable;
import addsynth.core.game.RegistryUtil;
import addsynth.core.util.math.random.RandomUtil;
import addsynth.material.ADDSynthMaterials;
import addsynth.material.MiningStrength;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class OreBlock extends Block {

  private final int min_experience;
  private final int max_experience;

  /**
   * Use this constructor if this Ore Block should be mined and smelted in a Furnace. The Furnace gives experience to the player.
   * @param name
   * @param strength
   */
  public OreBlock(final String name, final MiningStrength strength){
    this(name, strength, 0, 0);
  }

  /**
   * Use this constructor if this Ore Block drops an item, such as Coal, Diamond, Lapis, Redstone, or Quartz.
   * @param name
   * @param strength
   * @param min_experience
   * @param max_experience
   */
  public OreBlock(final String name, final MiningStrength strength, int min_experience, int max_experience){
    super(Block.Properties.of(Material.STONE).strength(3.0f, 6.0f).harvestTool(ToolType.PICKAXE).harvestLevel(strength.ordinal()).requiresCorrectToolForDrops());
    // https://minecraft.gamepedia.com/Breaking#Blocks_by_hardness
    RegistryUtil.register_block(this, new ResourceLocation(ADDSynthMaterials.MOD_ID, name), ADDSynthMaterials.creative_tab);
    this.min_experience = min_experience;
    this.max_experience = max_experience;
  }

  @Override
  public final int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch){
    return silktouch == 0 ? RandomUtil.RandomRange(min_experience, max_experience) : 0;
  }

  @Override
  @Nullable
  public final ToolType getHarvestTool(final BlockState state){
    return ToolType.PICKAXE;
  }

}
