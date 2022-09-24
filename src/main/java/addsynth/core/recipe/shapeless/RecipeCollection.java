package addsynth.core.recipe.shapeless;

import java.util.ArrayList;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import addsynth.core.Debug;
import addsynth.core.recipe.RecipeUtil;
import addsynth.material.MaterialsUtil;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeCollection <T extends AbstractRecipe> {

  public final IRecipeType<T> type;
  public final ShapelessRecipeSerializer<T> serializer;

  public final ArrayList<T> recipes = new ArrayList<T>();
  private Item[] filter;
  private boolean update = true;

  public RecipeCollection(IRecipeType<T> type, ShapelessRecipeSerializer<T> serializer){
    this.type = type;
    this.serializer = serializer;
  }

  /** Adds the recipe to this collection, so that it may be used in the other functions. */
  public final void addRecipe(final T recipe){
    if(recipes.contains(recipe) == false){
      recipes.add(recipe);
      Debug.log_recipe(recipe);
    }
  }

  /** This ensures the input filter gets rebuilt whenever Tags or Recipes are reloaded. */
  public final void registerResponders(){
    ADDSynthCore.log.info(type.getClass().getSimpleName()+" input filter was rebuilt.");
    // rebuild filter on recipe reload.
    RecipeUtil.registerResponder(() -> {update = true;});
    // rebuild filter on tag reload.
    MaterialsUtil.registerResponder(() -> {update = true;});
  }

  /** This builds the ingredient filter. */
  public final void build_filter(){
    if(recipes.size() == 0){
      ADDSynthCore.log.error("No recipes of type "+type.getClass().getSimpleName()+" exist!");
      filter = new Item[0];
      return;
    }
    final ArrayList<Item> item_list = new ArrayList<>();
    for(final T recipe : recipes){
      for(final Ingredient ingredient : recipe.getIngredients()){
        for(final ItemStack stack : ingredient.getItems()){
          item_list.add(stack.getItem());
        }
      }
    }
    filter = item_list.toArray(new Item[item_list.size()]);
  }

  public final Item[] getFilter(){
    if(update || filter == null){
      build_filter();
      update = false;
    }
    return filter;
  }

  /** Returns whether the input items matches a recipe in this collection. */
  public final boolean match(final ItemStack[] input, final World world){
    final Inventory inventory = new Inventory(input);
    for(final T recipe : recipes){
      if(recipe.matches(inventory, world)){
        return true;
      }
    }
    return false;
  }

  /** Returns the recipe output, or null if there is no matching recipe. */
  @Nonnull
  public final ItemStack getResult(final ItemStack input, final World world){
    return getResult(new ItemStack[] {input}, world);
  }

  /** Returns the recipe output, or ItemStack.EMPTY if there is no matching recipe. */
  @Nonnull
  public final ItemStack getResult(final ItemStack[] input, final World world){
    final Inventory inventory = new Inventory(input); // OPTIMIZE this by skipping converting the ItemStacks into an inventory, and using RecipeItemHelper directly. And remove world argument.
    for(final T recipe : recipes){
      if(recipe.matches(inventory, world)){
        return recipe.getResultItem().copy();
      }
    }
    return ItemStack.EMPTY;
  }
  
  /** Finds the recipe with an output that matches the passed in ItemStack.
   *  Warning: Finds the FIRST recipe with a matching output. There may be
   *  more than one recipe that makes that item. Returns null if no recipe was found.
   */
  @Nullable
  public final T find_recipe(final ItemStack output){
    T recipe = null;
    for(T r : recipes){
      if(r.getResultItem().sameItem(output)){
        recipe = r;
        break;
      }
    }
    return recipe;
  }

  @Nullable
  public final T find_recipe(final ResourceLocation registry_key){
    final Item output = ForgeRegistries.ITEMS.getValue(registry_key);
    if(output == null){
      return null;
    }
    T recipe = null;
    for(T r : recipes){
      if(r.getResultItem().getItem() == output){
        recipe = r;
        break;
      }
    }
    return recipe;
  }

}
