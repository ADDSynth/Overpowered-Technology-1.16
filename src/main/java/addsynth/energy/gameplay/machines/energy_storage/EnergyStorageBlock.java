package addsynth.energy.gameplay.machines.energy_storage;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.game.RegistryUtil;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.lib.blocks.MachineBlock;
import addsynth.energy.registers.Names;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public final class EnergyStorageBlock extends MachineBlock {

  public EnergyStorageBlock(){
    super(Block.Properties.of(Material.METAL, MaterialColor.SNOW).noOcclusion().strength(3.5f, 6.0f));
    RegistryUtil.register_block(this, Names.ENERGY_STORAGE, ADDSynthEnergy.creative_tab);
  }

  @Override
  public final void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
    tooltip.add(new TranslationTextComponent("gui.addsynth_energy.tooltip.battery"));
  }

  @Override
  @Nullable
  public final TileEntity createTileEntity(BlockState state, IBlockReader world){
    return new TileEnergyStorage();
  }

  @Override
  @SuppressWarnings("deprecation")
  public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos){
    return 1.0f;
  }

  @Override
  @SuppressWarnings("deprecation")
  public final ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
    if(world.isClientSide == false){
      final TileEnergyStorage tile = MinecraftUtility.getTileEntity(pos, world, TileEnergyStorage.class);
      if(tile != null){
        NetworkHooks.openGui((ServerPlayerEntity)player, tile, pos);
      }
    }
    return ActionResultType.SUCCESS;
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side){
    return adjacentBlockState.getBlock() == this ? true : super.skipRendering(state, adjacentBlockState, side);
  }

}
