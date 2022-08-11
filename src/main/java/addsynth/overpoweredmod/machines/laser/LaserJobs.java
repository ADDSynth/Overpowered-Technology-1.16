package addsynth.overpoweredmod.machines.laser;

// quite a lot of imports for such a small class file.
import java.util.ArrayList;
import java.util.Collection;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.OverpoweredSavedData;
import addsynth.overpoweredmod.game.core.Laser;
import addsynth.overpoweredmod.machines.laser.beam.LaserBeam;
import addsynth.overpoweredmod.machines.laser.cannon.LaserCannon;
import addsynth.overpoweredmod.machines.laser.machine.LaserNetwork;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

@EventBusSubscriber(modid = OverpoweredTechnology.MOD_ID, bus = Bus.FORGE)
public final class LaserJobs {

  private static final int beam_size = 10;
  private static final ArrayList<LaserJob> laser_jobs = new ArrayList<>(1);

  private final static class LaserBeamData {
    private final int color;
    private boolean move_forward = true;
    private final Direction direction;
    private BlockPos.Mutable position;
    private BlockPos.Mutable end_position;
    private boolean delete = false;
    
    private LaserBeamData(BlockPos position, int color, Direction direction){
      this.color = color;
      // passed in position is the position of the laser.
      // We must increment the position towards the direction the laser is facing.
      final int x = position.getX() + direction.getStepX();
      final int y = position.getY() + direction.getStepY();
      final int z = position.getZ() + direction.getStepZ();
      this.position = new BlockPos.Mutable(x, y, z);
       end_position = new BlockPos.Mutable(x, y, z);
      this.direction = direction;
    }
    
    private LaserBeamData(CompoundNBT tag){
      color        = Math.max(tag.getInt("color"), 0) % Laser.values().length;
      move_forward = tag.getBoolean("move_forward");
      direction    = Direction.from3DDataValue(tag.getInt("direction"));
      position     = new BlockPos.Mutable(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
      end_position = new BlockPos.Mutable(tag.getInt("tail_x"), tag.getInt("tail_y"), tag.getInt("tail_z"));
    }
    
    private final void tick(final World world, final int life){
      Block block;
      // if we can move forward
      if(move_forward){
        // check if next block can be destroyed
        block = world.getBlockState(position).getBlock();
        if(block == Blocks.BEDROCK){
          // if we can't destroy next block, stop moving forward
          move_forward = false;
        }
        else{
          // destroy block
          world.destroyBlock(position, true);
          // put a laser beam there
          world.setBlock(position, Laser.beams[color].defaultBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        }
        // increment position
        position.move(direction);
      }
      
      // erase end laser beam, if it exists
      if(delete == false){
        if(life >= beam_size){
          block = world.getBlockState(end_position).getBlock();
          if(block instanceof LaserBeam){
            world.removeBlock(end_position, false);
            // NOTE: Error exists: If you shoot 2 laser beams into each other, it won't look right
            //       when one deletes the other. But this is minor and can be fixed later.
          }
          // increment position
          end_position.move(direction);
          // mark as delete if end position has reached the start position
          delete = end_position.equals(position);
        }
      }
    }
    
    private final CompoundNBT save(){
      final CompoundNBT tag = new CompoundNBT();
      tag.putInt("color", color);
      tag.putInt("direction", direction.get3DDataValue());
      tag.putBoolean("move_forward", move_forward);
      // tag.putLong("position", position.asLong());
      // tag.putLong("end_position", end_position.asLong());
      tag.putInt("x", position.getX());
      tag.putInt("y", position.getY());
      tag.putInt("z", position.getZ());
      tag.putInt("tail_x", end_position.getX());
      tag.putInt("tail_y", end_position.getY());
      tag.putInt("tail_z", end_position.getZ());
      return tag;
    }
  }
  
  private final static class LaserJob {
    private final int distance;
    private final ArrayList<LaserBeamData> beams;
    private int life = 0;
    private ResourceLocation dimension;
    
    private LaserJob(final World world, final Collection<BlockPos> positions, final int distance){
      this.distance = distance;
      dimension = world.dimension().location();
      
      beams = new ArrayList<LaserBeamData>(positions.size());
      BlockState state;
      LaserCannon block;
      for(final BlockPos position : positions){
        state = world.getBlockState(position);
        if(state.getBlock() instanceof LaserCannon){
          block = (LaserCannon)state.getBlock();
          beams.add(new LaserBeamData(position, block.color, state.getValue(LaserCannon.FACING)));
        }
      }
    }

    private LaserJob(final CompoundNBT tag){
      dimension = new ResourceLocation(tag.getString("dimension"));
      distance = MathHelper.clamp(tag.getInt("distance"), 1, LaserNetwork.max_laser_distance);
      life = Math.max(tag.getInt("life"), 0);
      final ListNBT beam_list = tag.getList("beams", Constants.NBT.TAG_COMPOUND);
      int i;
      final int length = beam_list.size();
      beams = new ArrayList<LaserBeamData>(length);
      for(i = 0; i < length; i++){
        beams.add(new LaserBeamData(beam_list.getCompound(i)));
      }
    }
    
    private final void tick(final World world){
      // tick all beams
      for(LaserBeamData beam : beams){
        beam.tick(world, life);
      }

      // increment life
      life++;
      
      // if we've reached end-of-life, stop incrementing begin position
      if(life == distance){
        for(LaserBeamData beam : beams){
          beam.move_forward = false;
        }
      }
      
      // delete any laser beams who's end position has reached the start position
      beams.removeIf((LaserBeamData b) -> b.delete);
    }

    private final CompoundNBT save(){
      final CompoundNBT tag = new CompoundNBT();
      tag.putString("dimension", dimension.toString());
      tag.putInt("distance", distance);
      tag.putInt("life", life);
      final ListNBT beam_list = new ListNBT();
      for(LaserBeamData beam : beams){
        beam_list.add(beam.save());
      }
      tag.put("beams", beam_list);
      return tag;
    }

    private final boolean shouldDelete(){
      return (beams == null ? true : beams.isEmpty()) || life >= distance + beam_size;
    }
  }

  public static final void addNew(final World world, final Collection<BlockPos> lasers, final int distance){
    laser_jobs.add(new LaserJob(world, lasers, distance));
    OverpoweredSavedData.dataChanged();
  }

  @SubscribeEvent
  public static final void tick(final ServerTickEvent event){
    if(event.phase == Phase.START){
      @SuppressWarnings("resource")
      final MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      if(server != null){
        server.getProfiler().push(OverpoweredTechnology.MOD_NAME + " Lasers");
        if(laser_jobs.size() > 0){
          for(ServerWorld world : server.getAllLevels()){
            for(LaserJob job : laser_jobs){
              if(world.dimension().location().equals(job.dimension)){
                job.tick(world);
              }
            }
          }
          laser_jobs.removeIf((LaserJob job) -> job.shouldDelete());
          OverpoweredSavedData.dataChanged();
        }
        server.getProfiler().pop();
      }
    }
  }

  public static final void load(CompoundNBT nbt){
    final ListNBT laser_tag = nbt.getList("Laser Jobs", Constants.NBT.TAG_COMPOUND);
    int i;
    for(i = 0; i < laser_tag.size(); i++){
      laser_jobs.add(new LaserJob(laser_tag.getCompound(i)));
    }
  }
  
  public static final void save(CompoundNBT nbt){
    final ListNBT laser_tag = new ListNBT();
    for(LaserJob job : laser_jobs){
      laser_tag.add(job.save());
    }
    nbt.put("Laser Jobs", laser_tag);
  }

}
