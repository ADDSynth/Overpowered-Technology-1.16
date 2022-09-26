package addsynth.overpoweredmod.machines.magic_infuser.recipes;

import addsynth.core.recipe.shapeless.AbstractRecipe;
import addsynth.overpoweredmod.game.core.Machines;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public final class MagicInfuserRecipe extends AbstractRecipe {

  public MagicInfuserRecipe(ResourceLocation id, String group, Enchantment enchantment, NonNullList<Ingredient> input){
    this(id, group, getEnchantedBook(enchantment), input);
  }

  public MagicInfuserRecipe(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> input){
    super(id, group, output, input);
    MagicInfuserRecipes.addRecipe(this);
  }

  public static final ItemStack getEnchantedBook(final Enchantment enchantment){
    return EnchantedBookItem.createForEnchantment(new EnchantmentData(enchantment, 1));
  }

  @Override
  public ItemStack getToastSymbol(){
    return new ItemStack(Machines.magic_infuser, 1);
  }

  @Override
  public IRecipeSerializer<MagicInfuserRecipe> getSerializer(){
    return MagicInfuserRecipes.serializer;
  }

  @Override
  public IRecipeType<?> getType(){
    return MagicInfuserRecipes.recipe_type;
  }

}
