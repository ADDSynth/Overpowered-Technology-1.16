package addsynth.overpoweredmod.game;

import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.machines.laser.LaserJobs;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

public final class OverpoweredSavedData extends WorldSavedData {

  // TODO: World Save Data. This is great! I can use this to save the signal data so players HAVE to find it each world!

  private static OverpoweredSavedData overpowered_savedata;
  
  public OverpoweredSavedData(String name){
    super(name);
  }

  /** This should be called in the {@link FMLServerStartedEvent}. */
  public static final void load(final MinecraftServer server){
    @SuppressWarnings("resource")
    final ServerWorld overworld = server.overworld();
    if(overworld != null){
      overpowered_savedata = overworld.getDataStorage().computeIfAbsent(() -> new OverpoweredSavedData(OverpoweredTechnology.MOD_ID), OverpoweredTechnology.MOD_ID);
    }
  }

  /** And this should only be called on the server side! That should be obvious by now! */
  public static final void dataChanged(){
    if(overpowered_savedata != null){
      overpowered_savedata.setDirty(true);
    }
  }

  @Override
  public final void load(CompoundNBT nbt){
    LaserJobs.load(nbt);
  }

  @Override
  public final CompoundNBT save(CompoundNBT nbt){
    LaserJobs.save(nbt);
    return nbt;
  }

}
