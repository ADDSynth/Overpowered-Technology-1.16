package addsynth.energy.gameplay.machines.energy_diagnostics;

import javax.annotation.Nullable;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.client.GuiProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public final class EnergyDiagnosticsBlock extends Block {

  public EnergyDiagnosticsBlock(final String name){
    super(Properties.of(Material.METAL, MaterialColor.WOOL));
    ADDSynthEnergy.registry.register_block(this, name, new Item.Properties().tab(ADDSynthEnergy.creative_tab));
  }

  @Override
  public boolean hasTileEntity(BlockState state){
    return true;
  }

  @Override
  @Nullable
  public TileEntity createTileEntity(BlockState state, IBlockReader world){
    return new TileEnergyDiagnostics();
  }

  @Deprecated
  @Override
  public ActionResultType use(BlockState state, World world, BlockPos position, PlayerEntity player, Hand hand, BlockRayTraceResult hit_result){
    if(world.isClientSide){
      final TileEnergyDiagnostics tile = MinecraftUtility.getTileEntity(position, world, TileEnergyDiagnostics.class);
      if(tile != null){
        GuiProvider.openEnergyDiagnostics(tile, getDescriptionId());
      }
    }
    return ActionResultType.SUCCESS;
  }

}
