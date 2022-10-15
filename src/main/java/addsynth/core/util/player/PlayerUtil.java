package addsynth.core.util.player;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import addsynth.core.game.item.ItemUtil;
import addsynth.core.util.math.block.BlockMath;
import addsynth.core.util.server.ServerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class PlayerUtil {

  public static final void add_to_player_inventory(final PlayerEntity player, final ItemStack stack){
    if(player.inventory.add(stack) == false){
      player.drop(stack, false);
    }
  }

  public static final void allPlayersInWorld(final World world, final Consumer<ServerPlayerEntity> action){
    @SuppressWarnings("resource")
    final MinecraftServer server = world.getServer();
    if(server != null){
      allPlayersInWorld(server, world, action);
    }
  }

  public static final void allPlayersInWorld(final MinecraftServer server, final World world, final Consumer<ServerPlayerEntity> action){
    for(ServerPlayerEntity player : server.getPlayerList().getPlayers()){
      if(player.level.dimensionType() == world.dimensionType()){
        action.accept(player);
      }
    }
  }

  public static final void allPlayersWithinHorizontalDistance(
  final World world, final BlockPos position, final double distance, final Consumer<ServerPlayerEntity> action){
    @SuppressWarnings("resource")
    final MinecraftServer server = world.getServer();
    if(server != null){
      allPlayersWithinHorizontalDistance(server, world, position, distance, action);
    }
  }

  public static final void allPlayersWithinHorizontalDistance(
  final MinecraftServer server, final World world, final BlockPos position, final double distance, final Consumer<ServerPlayerEntity> action){
    for(ServerPlayerEntity player : server.getPlayerList().getPlayers()){
      if(player.level.dimensionType() == world.dimensionType()){
        if(BlockMath.isWithinHorizontal(position, player.getX(), player.getZ(), distance)){
          action.accept(player);
        }
      }
    }
  }

  public static final boolean isPlayerHoldingItem(final PlayerEntity player, final Item item){
    final ItemStack stack = player.getMainHandItem();
    if(ItemUtil.itemStackExists(stack)){
      return stack.getItem() == item;
    }
    return false;
  }

  /** Gets the {@link ServerPlayerEntity} using the player's name.
   *  Get the Player's name by calling {@link PlayerEntity#getName()}.
   *  This must be called on the server side, otherwise it will return null.
   *  Also returns null if the player isn't on the server at the moment.
   * @param world
   * @param player_name
   */
  @Nullable
  @SuppressWarnings({ "resource", "null"})
  public static final ServerPlayerEntity getPlayer(World world, String player_name){
    if(world == null){
      return getPlayer(player_name);
    }
    if(world.isClientSide == false){
      return world.getServer().getPlayerList().getPlayerByName(player_name);
    }
    return null;
  }

  /** Gets the {@link ServerPlayerEntity} using the player's name.
   *  Get the Player's name by calling {@link PlayerEntity#getName()}.
   *  This must be called on the server side, otherwise it will return null.
   *  Also returns null if the player isn't on the server at the moment.
   * @param player_name
   */
  @Nullable
  @Deprecated
  @SuppressWarnings("resource")
  public static final ServerPlayerEntity getPlayer(String player_name){
    final MinecraftServer server = ServerUtils.getServer();
    if(server == null){
      ADDSynthCore.log.error(new NullPointerException("Cannot call "+PlayerUtil.class.getSimpleName()+".getPlayer() on the Client side!"));
      return null;
    }
    return server.getPlayerList().getPlayerByName(player_name);
  }

}
