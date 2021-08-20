package addsynth.core.gameplay.music_box;

import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.client.GuiProvider;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.core.util.player.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public final class MusicBox extends Block {

  public MusicBox(String name){
    super(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(0.8f));
    ADDSynthCore.registry.register_block(this, name, new Item.Properties().tab(ADDSynthCore.creative_tab));
  }

  @Override
  @SuppressWarnings("deprecation")
  public final int getSignal(BlockState state, IBlockReader world, BlockPos pos, Direction side){
    return 0;
  }

  @Override
  public boolean hasTileEntity(BlockState state){
    return true;
  }

  @Override
  @Nullable
  public final TileEntity createTileEntity(BlockState state, IBlockReader world){
    return new TileMusicBox();
  }

  @Override
  @SuppressWarnings({ "deprecation" })
  public final ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
    if(PlayerUtil.isPlayerHoldingItem(player, Core.music_sheet)){
      return ActionResultType.PASS; // let the music sheet item handle it.
    }
    if(world.isClientSide){
      final TileMusicBox tile = MinecraftUtility.getTileEntity(pos, world, TileMusicBox.class);
      if(tile != null){
        GuiProvider.openMusicBoxGui(tile, this);
      }
    }
    return ActionResultType.SUCCESS;
  }

}
