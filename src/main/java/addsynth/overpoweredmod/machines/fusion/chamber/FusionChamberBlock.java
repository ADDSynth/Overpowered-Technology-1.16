package addsynth.overpoweredmod.machines.fusion.chamber;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.game.RegistryUtil;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.lib.blocks.MachineBlock;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.Names;
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
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public final class FusionChamberBlock extends MachineBlock {

  public FusionChamberBlock(){
    super(MaterialColor.SNOW);
    RegistryUtil.register_block(this, Names.FUSION_CHAMBER, CreativeTabs.creative_tab);
  }

  @Override
  public final void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new TranslationTextComponent("gui.overpowered.tooltip.fusion_machine"));
  }

  @Override
  @Nullable
  public final TileEntity createTileEntity(BlockState state, final IBlockReader world){
    return new TileFusionChamber();
  }

  @Override
  @SuppressWarnings("deprecation")
  public final ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
    if(world.isClientSide == false){
      final TileFusionChamber tile = MinecraftUtility.getTileEntity(pos, world, TileFusionChamber.class);
      if(tile == null){ return ActionResultType.PASS; }
      if(tile.is_on()){ return ActionResultType.PASS; }
      NetworkHooks.openGui((ServerPlayerEntity)player, tile, pos);
    }
    return ActionResultType.SUCCESS;
  }

  @Override
  public final void playerWillDestroy(final World worldIn, final BlockPos pos, final BlockState state, final PlayerEntity player){
    super.playerWillDestroy(worldIn, pos, state, player);
    check_container(worldIn, pos);
  }

  @Override
  public final void wasExploded(final World world, final BlockPos pos, final Explosion explosion){
    check_container(world, pos);
  }

  private static final void check_container(final World world, final BlockPos position){
    if(world.isClientSide == false){
      final TileFusionChamber tile = MinecraftUtility.getTileEntity(position, world, TileFusionChamber.class);
      if(tile != null){
        if(tile.is_on()){
          tile.explode();
        }
      }
    }
  }

}
