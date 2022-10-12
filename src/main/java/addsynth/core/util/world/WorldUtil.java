package addsynth.core.util.world;

import addsynth.core.util.server.ServerUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IWorldInfo;

public final class WorldUtil {

  /** Ever since Minecraft 1.13, there are now two other types of Air, Cave Air and Void Air.
   *  There may also be other Air blocks that are defined by mods.
   * @param world
   * @param position
   */
  @SuppressWarnings("deprecation")
  public static final boolean isAir(final World world, final BlockPos position){
    return world.getBlockState(position).isAir();
  }

  public static final int getTopMostFreeSpace(final World world, final BlockPos position){
    return world.getChunk(position).getHeight(Heightmap.Type.WORLD_SURFACE, position.getX(), position.getZ()) + 1;
  }
  
  public static final int getTopMostFreeSpace(final World world, final int x_pos, final int z_pos){
    return world.getChunk(x_pos >> 4, z_pos >> 4).getHeight(Heightmap.Type.WORLD_SURFACE, x_pos, z_pos) + 1;
  }

  public static final boolean validYLevel(final int y_level){
    return y_level >= WorldConstants.min_y_level && y_level < WorldConstants.world_height;
  }

  public static final void delete_block(final World world, final BlockPos position){
    world.setBlock(position, Blocks.AIR.defaultBlockState(), 3);
  }

  /** Spawns an ItemStack using the vanilla method. */
  public static final void spawnItemStack(IWorld world, BlockPos pos, ItemStack stack){
    InventoryHelper.dropItemStack((World)world, pos.getX(), pos.getY(), pos.getZ(), stack);
  }

  /** Spawns an ItemStack using the vanilla method. */
  public static final void spawnItemStack(World world, BlockPos pos, ItemStack stack){
    InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
  }

  /** Spawns an ItemStack using the vanilla method. */
  public static final void spawnItemStack(IWorld world, double x, double y, double z, ItemStack stack){
    InventoryHelper.dropItemStack((World)world, x, y, z, stack);
  }

  /** Spawns an ItemStack using the vanilla method. */
  public static final void spawnItemStack(World world, double x, double y, double z, ItemStack stack){
    InventoryHelper.dropItemStack(world, x, y, z, stack);
  }

  /** Spawns an ItemStack. The final boolean parameter determines if you want to use
   *  vanilla's method of randomly jumping items when they are spawned.
   */
  public static final void spawnItemStack(IWorld world, BlockPos pos, ItemStack stack, boolean vanilla_jump_randomly){
    spawnItemStack((World)world, pos.getX(), pos.getY(), pos.getZ(), stack, vanilla_jump_randomly);
  }

  /** Spawns an ItemStack. The final boolean parameter determines if you want to use
   *  vanilla's method of randomly jumping items when they are spawned.
   */
  public static final void spawnItemStack(World world, BlockPos pos, ItemStack stack, boolean vanilla_jump_randomly){
    spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack, vanilla_jump_randomly);
  }

  /** Spawns an ItemStack. The final boolean parameter determines if you want to use
   *  vanilla's method of randomly jumping items when they are spawned.
   */
  public static final void spawnItemStack(IWorld world, double x, double y, double z, ItemStack stack, boolean vanilla_jump_randomly){
    spawnItemStack((World)world, x, y, z, stack, vanilla_jump_randomly);
  }

  /** Spawns an ItemStack. The final boolean parameter determines if you want to use
   *  vanilla's method of randomly jumping items when they are spawned.
   */
  public static final void spawnItemStack(World world, double x, double y, double z, ItemStack stack, boolean vanilla_jump_randomly){
    if(vanilla_jump_randomly){
      InventoryHelper.dropItemStack(world, x, y, z, stack);
    }
    else{
      if(stack.isEmpty() == false){
        final ItemEntity entity = new ItemEntity(world, x + 0.5, y, z + 0.5, stack);
        entity.setDeltaMovement(0.0, 0.0, 0.0);
        world.addFreshEntity(entity);
      }
    }
  }

  /** I don't get it. The /time command sets the time for all worlds, but you can also
   *  set the world's time individually.
   *  DO NOT set time as a negative value!
   * @see addsynth.core.util.time.WorldTime
   * @see net.minecraft.command.impl.TimeCommand
   */
  @Deprecated
  public static final void set_time(final World world, final int world_time){
    set_time(world, (long)world_time);
  }
  
  /** I don't get it. The /time command sets the time for all worlds, but you can also
   *  set the world's time individually.
   *  DO NOT set time as a negative value!
   * @see addsynth.core.util.time.WorldTime
   * @see net.minecraft.command.impl.TimeCommand
   */
  @Deprecated
  public static final void set_time(final MinecraftServer server, final int world_time){
    set_time(server, (long)world_time);
  }
  
  /** I don't get it. The /time command sets the time for all worlds, but you can also
   *  set the world's time individually.
   *  DO NOT set time as a negative value!
   * @see addsynth.core.util.time.WorldTime
   * @see net.minecraft.command.impl.TimeCommand
   */
  public static final void set_time(final World world, final long world_time){
    @SuppressWarnings("resource")
    final MinecraftServer server = world.getServer();
    if(server != null){
      for(ServerWorld w : server.getAllLevels()){
        w.setDayTime(world_time >= 0 ? world_time : 0);
      }
    }
    // else{
      // they let me do this? What's going to happen?
      // world.setDayTime(world_time);
    // }
  }

  /** I don't get it. The /time command sets the time for all worlds, but you can also
   *  set the world's time individually.
   *  DO NOT set time as a negative value!
   * @see addsynth.core.util.time.WorldTime
   * @see net.minecraft.command.impl.TimeCommand
   */
  public static final void set_time(final MinecraftServer server, final long world_time){
    for(ServerWorld world : server.getAllLevels()){
      world.setDayTime(world_time >= 0 ? world_time : 0);
    }
  }

  /** @deprecated Look at what this function does. Bypass this and use the vanilla method instead. */
  @Deprecated
  public static final BlockPos getSpawnPosition(final World world){
    final IWorldInfo world_data = world.getLevelData();
    return new BlockPos(world_data.getXSpawn(), world_data.getYSpawn(), world_data.getZSpawn());
  }

  @SuppressWarnings("resource")
  @Deprecated
  public static final BlockPos getSpawnPosition(){
    final MinecraftServer server = ServerUtils.getServer();
    if(server != null){
      final World world = server.getLevel(World.OVERWORLD);
      if(world != null){
        return getSpawnPosition(world);
      }
    }
    return BlockPos.ZERO;
  }

  
  public static final void spawnLightningBolt(final World world, final BlockPos position){
    final LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(world);
    if(lightningboltentity != null){
      lightningboltentity.moveTo(Vector3d.atBottomCenterOf(position));
      world.addFreshEntity(lightningboltentity);
    }
  }

}
