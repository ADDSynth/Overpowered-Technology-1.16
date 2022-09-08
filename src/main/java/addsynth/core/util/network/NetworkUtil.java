package addsynth.core.util.network;

import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.PacketDistributor.TargetPoint;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public final class NetworkUtil {

  public static final void writeBlockPos(final PacketBuffer data, final BlockPos pos){
    data.writeInt(pos.getX());
    data.writeInt(pos.getY());
    data.writeInt(pos.getZ());
  }

  public static final BlockPos readBlockPos(final PacketBuffer data){
    return new BlockPos(data.readInt(), data.readInt(), data.readInt());
  }

  public static final void writeBlockPositions(final PacketBuffer data, final BlockPos[] positions){
    data.writeInt(positions.length);
    for(final BlockPos pos : positions){
      writeBlockPos(data, pos);
    }
  }

  public static final BlockPos[] readBlockPositions(final PacketBuffer data){
    int i;
    final int length = data.readInt();
    final BlockPos[] positions = new BlockPos[length];
    for(i = 0; i < length; i++){
      positions[i] = readBlockPos(data);
    }
    return positions;
  }

  public static final void writeString(final PacketBuffer data, final String string){
    data.writeUtf(string);
  }

  @Deprecated
  public static final String readString(final PacketBuffer data){ // OPTIMIZE: our own read/writeString functions are no longer needed for Minecraft 1.16
    return data.readUtf();
  }

  public static final void writeStringArray(final PacketBuffer data, final String[] string_array){
    data.writeInt(string_array.length);
    for(final String s : string_array){
      data.writeUtf(s);
    }
  }

  public static final String[] readStringArray(final PacketBuffer data){
    int i;
    final int length = data.readInt();
    final String[] strings = new String[length];
    for(i = 0; i < length; i++){
      strings[i] = data.readUtf();
    }
    return strings;
  }

  public static final void writeTextComponentArray(final PacketBuffer data, final ITextComponent[] text_component_array){
    data.writeInt(text_component_array.length);
    for(final ITextComponent t : text_component_array){
      data.writeUtf(t.getString());
    }
  }

  public static final ITextComponent[] readTextComponentArray(final PacketBuffer data){
    int i;
    final int length = data.readInt();
    final ITextComponent[] text_components = new ITextComponent[length];
    for(i = 0; i < length; i++){
      text_components[i] = new StringTextComponent(readString(data));
    }
    return text_components;
  }

  /** Sends the Network message to all clients in the world you specify.
   *  Must be called on the server side.
   * @param network
   * @param world
   * @param message
   */
  public static final void send_to_clients_in_world(final SimpleChannel network, final World world, final Object message){
    network.send(PacketDistributor.DIMENSION.with(() -> world.dimension()), message);
  }

  /** Sends a network message to only those players that are close to the TileEntity,
   *  with a default radius of 32 blocks.   */
  public static final void send_to_TileEntity(final SimpleChannel network, final TileEntity tile, final Object message){
    send_to_TileEntity(network, tile, 32, message);
  }

  /** Further restricts which clients to send the network message to by only sending
   *  the message to just the players that are close to the TileEntity. */
  public static final void send_to_TileEntity(final SimpleChannel network, final TileEntity tile, final double radius, final Object message){
    @SuppressWarnings("resource")
    final World world = tile.getLevel();
    if(world != null){
      final BlockPos pos = tile.getBlockPos();
      final Supplier<TargetPoint> tp_supplier = () -> {
        return new TargetPoint(pos.getX(), pos.getY(), pos.getZ(), radius, world.dimension());
      };
      network.send(PacketDistributor.NEAR.with(tp_supplier), message);
    }
  }

}
