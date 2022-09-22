package addsynth.overpoweredmod.machines.portal.rift;

import addsynth.core.game.tiles.TileBase;
import addsynth.core.util.time.TimeConstants;
import addsynth.overpoweredmod.config.Values;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.world.World;

public final class TilePortal extends TileBase implements ITickableTileEntity {

  private int count = 0;
  private final int life = Values.portal_spawn_time.get() * TimeConstants.ticks_per_second;

  public TilePortal(){
    super(Tiles.PORTAL_BLOCK);
  }

  @Override
  public final void tick(){
    if(onServerSide()){
      count += 1;
      if(count >= life){
        final World level = this.level;
        if(level != null){
          level.removeBlock(worldPosition, false);
        }
      }
    }
  }

}
