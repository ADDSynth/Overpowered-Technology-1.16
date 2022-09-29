package addsynth.material;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = ADDSynthCore.MOD_ID, bus = Bus.FORGE)
public final class MaterialsUtil {

  // FUTURE: the responders field, registerResponder method, and dispatchEvent method that you see here is
  //         exactly the same as that in the RecipeUtil class. Once we update to Java 9 it is likely that
  //         you can move this common code to an interface and use private access methods, new in Java 9.
  private static final ArrayList<Runnable> responders = new ArrayList<>();

  public static final void registerResponder(final Runnable executable){
    if(responders.contains(executable)){
      ADDSynthCore.log.warn("That function is already registered as an event responder.");
      // Thread.dumpStack();
    }
    else{
      responders.add(executable);
    }
  }

  private static final void dispatchEvent(){
    for(final Runnable responder : responders){
      responder.run();
    }
  }

  @SubscribeEvent
  public static final void reload_tags_event(final TagsUpdatedEvent event){
    // FIX Tag update events sent to the client are now run on Client thread, cannot get the server in this case. GAIN A BETTER UNDERSTANDING OF THE TagsUpdatedEvent and the RecipesUpdatedEvent!
    ADDSynthCore.log.info("Tags were Reloaded. Sending update events...");

    final ITagCollectionSupplier tag_manager = event.getTagManager();
    // final NetworkTagCollection<Block> blocks = tag_manager.getBlocks();
    final ITagCollection<Item>  items  = tag_manager.getItems();
    dispatchEvent();

    ADDSynthCore.log.info("Done responding to Tag reload.");
  }

// =======================================================================================

  public static final Collection<Item> getOres(){
    return Tags.Items.ORES.getValues();
  }

  @Nullable
  public static final ITag<Item> getTag(final ResourceLocation tag_id){
    return ItemTags.getAllTags().getTag(tag_id);
  }

  @Nullable
  public static final Collection<Item> getItemCollection(final ResourceLocation tag_id){
    final ITag<Item> item_tag = getTag(tag_id);
    return item_tag != null ? item_tag.getValues() : null;
  }

  public static final Ingredient getTagIngredient(final ResourceLocation tag_id){
    final ITag<Item> item_tag = getTag(tag_id);
    return item_tag != null ? Ingredient.of(item_tag) : Ingredient.EMPTY;
  }

// =======================================================================================

  public static final boolean match(final Item item, final ResourceLocation item_tag){
    final Collection<Item> list = getItemCollection(item_tag);
    if(list != null){
      for(final Item check_item : list){
        if(item == check_item){
          return true;
        }
      }
    }
    return false;
  }

  public static final Item[] getFilter(final ResourceLocation ... tag_list){
    final ArrayList<Item> final_list = new ArrayList<>(100);
    Collection<Item> collection;
    for(final ResourceLocation tag : tag_list){
      collection = getItemCollection(tag);
      if(collection != null){
        final_list.addAll(collection);
        continue;
      }
      ADDSynthCore.log.error(new NullPointerException(
        MaterialsUtil.class.getName()+".getFilter() has detected a null Item Collection! Maybe one of the functions in MaterialsUtil "+
        "that retrieves all the Items in an Item Tag didn't return anything because there ARE no Items registered to that Item Tag!"
      ));
    }
    return final_list.toArray(new Item[final_list.size()]);
  }

}
