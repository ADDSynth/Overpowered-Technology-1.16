package addsynth.energy.gameplay.machines.circuit_fabricator;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.ADDSynthEnergy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public final class ChangeCircuitFabricatorRecipe {

  private final BlockPos position;
  private final String recipe_output;

  public ChangeCircuitFabricatorRecipe(final BlockPos position, final String recipe_output){
    this.position = position;
    this.recipe_output = recipe_output;
  }

  public static final void encode(final ChangeCircuitFabricatorRecipe message, final PacketBuffer buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeUtf(message.recipe_output);
  }

  public static final ChangeCircuitFabricatorRecipe decode(final PacketBuffer buf){
    return new ChangeCircuitFabricatorRecipe(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()), buf.readUtf());
  }

  public static void handle(final ChangeCircuitFabricatorRecipe message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayerEntity player = context.getSender();
    if(player != null){
      @SuppressWarnings("resource")
      final ServerWorld world = player.getLevel();
      context.enqueueWork(() -> {
        if(world.isAreaLoaded(message.position, 0)){
          final TileCircuitFabricator tile = MinecraftUtility.getTileEntity(message.position, world, TileCircuitFabricator.class);
          if(tile != null){
            tile.change_recipe(message.recipe_output);
            tile.ejectInvalidItems(player); // must stay here because we have access to the player?
          }
          else{
            ADDSynthEnergy.log.warn(new NullPointerException("No TileEntity exists at location: "+message.position+"."));
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
