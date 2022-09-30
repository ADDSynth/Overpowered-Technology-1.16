package addsynth.core.game.tiles;

import addsynth.core.ADDSynthCore;
import addsynth.core.util.StringUtil;
import addsynth.core.util.game.MessageUtil;
import addsynth.core.util.game.tileentity.TileEntityUtil;
import addsynth.core.util.world.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

/** This is our abstract BlockEntity class which only contains a few
 *  basic stuff. Do not override this directly. Either create your own
 *  BlockEntity or override {@link TileBase} or {@link TileBaseNoData}.
 * @author ADDSynth
 */
public abstract class TileAbstractBase extends TileEntity {

  public TileAbstractBase(final TileEntityType type){
    super(type);
  }

  /** Player who placed the block. */
  protected String owner;
  /** The last player who right-clicked the block to use it. */
  protected String last_used_by;

  @SuppressWarnings("null")
  protected final boolean onServerSide(){
    return !level.isClientSide;
  }

  @SuppressWarnings("null")
  protected final boolean onClientSide(){
    return level.isClientSide;
  }

  public abstract void update_data();

  protected final void report_ticking_error(final Throwable e){
    ADDSynthCore.log.fatal(
      "Encountered an error while ticking TileEntity: "+getClass().getSimpleName()+", at position: "+worldPosition+". "+
      "Please report this to the developer.", e);

    WorldUtil.delete_block(level, worldPosition);

    final TranslationTextComponent message = new TranslationTextComponent("message.addsynthcore.tileentity_error",
      getClass().getSimpleName(), worldPosition.getX(), worldPosition.getY(), worldPosition.getZ());

    message.setStyle(Style.EMPTY.withColor(TextFormatting.RED));
    MessageUtil.send_to_all_players_in_world(level, message);
  }

  // ================================================================================

  /** You can set the owner by calling {@link TileEntityUtil#setOwner}. After a player
   *  has set down a block. This is called on the server and client.
   *  Also automatically sets the <code>last_used_by</code> field as well.
   *  However, if this is called on the server side, the TileEntity should be updated. */
  public final void setOwner(final PlayerEntity player){
    owner = player.getGameProfile().getName();
    last_used_by = owner;
    if(onServerSide()){
      update_data(); // What would happen if I called this on the client side?
    }
  }

  /** This should be called in your TileEntity's {@link INamedContainerProvider#createMenu}
   *  function, or in the {@link Block#use} function. This can be called on the server and
   *  client sides. Automatically sets the owner as well, if there is no owner defined.
   *  However, if this is called on the server side, the TileEntity should be updated. */
  public final void setPlayerAccessed(final PlayerEntity player){
    last_used_by = player.getGameProfile().getName();
    if(StringUtil.StringExists(owner) == false){
      owner = last_used_by;
    }
    if(onServerSide()){
      update_data();
    }
  }

  public final String getOwner(){
    return owner;
  }
  
  public final String getLastUsedBy(){
    return last_used_by;
  }

  protected final void loadPlayerData(final CompoundNBT nbt){
    // legacy
    if(nbt.contains("Player")){
      owner = nbt.getString("Player");
      last_used_by = owner;
      return;
    }
    
    owner = nbt.getString("Owner");
    last_used_by = nbt.getString("Last Used By");
  }

  protected final void savePlayerData(final CompoundNBT nbt){
    nbt.putString("Owner", owner != null ? owner : "");
    nbt.putString("Last Used By", last_used_by != null ? last_used_by : "");
  }

}
