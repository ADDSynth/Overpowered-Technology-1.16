package addsynth.overpoweredmod.items;

import addsynth.core.compat.Compatibility;
import addsynth.core.util.game.MessageUtil;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = OverpoweredTechnology.MOD_ID, bus = Bus.FORGE)
public final class DimensionalAnchor extends OverpoweredItem {

  public DimensionalAnchor(){
    super(Names.DIMENSIONAL_ANCHOR, new Item.Properties().tab(CreativeTabs.creative_tab).stacksTo(1));
  }

  public static final boolean player_has_dimensional_anchor(final PlayerEntity player){
    if(Compatibility.CURIOS.loaded){
      if(CuriosApi.getCuriosHelper().findFirstCurio(player, OverpoweredItems.dimensional_anchor).isPresent()){
        return true;
      }
    }
    return player.inventory.contains(new ItemStack(OverpoweredItems.dimensional_anchor));
  }

  /* Known dimensions:
  Vanilla: Overworld, The Nether, The End
  Overpowered Technology: The Unknown Dimension
  Galacticraft: Moon, Mars, Asteroids, Venus
  Applied Energistics: Inside a Spatial Storage Drive
  */

  @SubscribeEvent
  public static final void playerChangingDimension(final EntityTravelToDimensionEvent event){
    if(event.getEntity() instanceof ServerPlayerEntity){
      final ServerPlayerEntity player = (ServerPlayerEntity)event.getEntity();
      if(player_has_dimensional_anchor(player)){
        // TODO: should probably check for Galacticraft planets here, and allow the Player to travel to them,
        //       since player travels to that dimension via a Rocket ship.
        event.setCanceled(true);
        @SuppressWarnings("resource")
        final MinecraftServer server = player.getLevel().getServer();
        MessageUtil.send_to_player(server, player, "gui.overpowered.anchored_in_this_dimension");
      }
    }
  }

}
