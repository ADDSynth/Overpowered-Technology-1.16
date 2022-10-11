package addsynth.overpoweredmod.machines.energy_extractor;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.game.RegistryUtil;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.lib.blocks.MachineBlock;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.reference.Names;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public final class CrystalEnergyExtractorBlock extends MachineBlock {

  public CrystalEnergyExtractorBlock(){
    super(MaterialColor.COLOR_BLACK);
    RegistryUtil.register_block(this, Names.CRYSTAL_ENERGY_EXTRACTOR, CreativeTabs.creative_tab);
  }

  @Override
  public final void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new TranslationTextComponent("gui.addsynth_energy.tooltip.energy_source"));
  }

  @Override
  @Nullable
  public final TileEntity createTileEntity(BlockState state, IBlockReader world){
    return new TileCrystalEnergyExtractor();
  }

  /** @deprecated Call via {@link BlockState#use(World, PlayerEntity, Hand, BlockRayTraceResult)} whenever possible.
    * Implementing/overriding is fine.
  */
  @Override
  public final ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
    if(world.isClientSide == false){
      final TileCrystalEnergyExtractor tile = MinecraftUtility.getTileEntity(pos, world, TileCrystalEnergyExtractor.class);
      if(tile != null){
        NetworkHooks.openGui((ServerPlayerEntity)player, tile, pos);
      }
    }
    return ActionResultType.SUCCESS;
  }

}
