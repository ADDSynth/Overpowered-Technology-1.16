package addsynth.overpoweredmod.compatability.curios;

import java.util.List;
import java.util.Random;
import addsynth.core.game.item.ItemUtil;
import addsynth.core.game.item.constants.ItemValue;
import addsynth.core.util.math.random.Weight;
import addsynth.core.util.time.TimeConstants;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.config.Values;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public enum RingEffects {

  NONE           ( 0, null, null, false),
  SPEED          ( 1, "Speed",           Effects.MOVEMENT_SPEED,   true),
  STRENGTH       ( 2, "Strength",        Effects.DAMAGE_BOOST,     true),
  HASTE          ( 3, "Haste",           Effects.DIG_SPEED,        true),
  LUCK           ( 4, "Luck",            Effects.LUCK,             true),
  EXTRA_HEALTH   ( 5, "Extra Health",    Effects.HEALTH_BOOST,     true),
  JUMP           ( 6, "Jump Boost",      Effects.JUMP,             true),
  FIRE_IMMUNITY  ( 7, "Fire Immunity",   Effects.FIRE_RESISTANCE, false),
  WATER_BREATHING( 8, "Water Breathing", Effects.WATER_BREATHING, false),
  NIGHT_VISION   ( 9, "Night Vision",    Effects.NIGHT_VISION,    false),
  INVISIBILITY   (10, "Invisibility",    Effects.INVISIBILITY,    false);

  public final int id;
  private final String translation_key;
  public final Effect effect;
  public final boolean has_levels;

  private RingEffects(int id, String translation_key, Effect mob_effect, boolean has_levels){
    this.id = id; // could also use this.ordinal()
    this.translation_key = translation_key;
    this.effect = mob_effect;
    this.has_levels = has_levels;
  }

  @Deprecated
  public static final void addEffectsToEntity(final ItemStack stack, final LivingEntity livingEntity){
    final int effect_id = get_ring_effect(stack);
    if(effect_id > 0){
      final RingEffects ring_effect = RingEffects.values()[effect_id];
      final boolean ring_particles = Config.rings_have_particle_effects.get();
      if(ring_effect.has_levels){
        final int level = get_ring_effect_level(stack) - 1;
        livingEntity.addEffect(new EffectInstance(ring_effect.effect, Integer.MAX_VALUE, level, false, ring_particles));
      }
      else{
        livingEntity.addEffect(new EffectInstance(ring_effect.effect, Integer.MAX_VALUE, 0, false, ring_particles));
      }
    }
  }

  /** This must be run every tick the ring is worn. This checks if the Entity has the effect.
   *  If it does, then this does nothing. Otherwise it adds the effect with the maximum duration.
   *  At 20 ticks per second, this comes to 3.4 years. It's very unlikely, but just in case
   *  someone has held onto the ring for that long, we check if the effect goes away, and apply
   *  the effect again.
   * @param stack
   * @param livingEntity
   */
  public static final void checkEntityHasEffect(final ItemStack stack, final LivingEntity livingEntity){
    final int effect_id = get_ring_effect(stack);
    if(effect_id > 0){
      final RingEffects ring_effect = RingEffects.values()[effect_id];
      final EffectInstance effect = livingEntity.getEffect(ring_effect.effect);
      if(effect != null ? effect.getDuration() < TimeConstants.ticks_per_second : true){
        // add effect
        final boolean ring_particles = Config.rings_have_particle_effects.get();
        if(ring_effect.has_levels){
          final int level = get_ring_effect_level(stack) - 1;
          livingEntity.addEffect(new EffectInstance(ring_effect.effect, Integer.MAX_VALUE, level, false, ring_particles));
        }
        else{
          livingEntity.addEffect(new EffectInstance(ring_effect.effect, Integer.MAX_VALUE, 0, false, ring_particles));
        }
      }
    }
  }

  public static final void removeEffectsFromEntity(final ItemStack stack, final LivingEntity livingEntity){
    final int effect_id = get_ring_effect(stack);
    if(effect_id > 0){
      final RingEffects ring_effect = RingEffects.values()[effect_id];
      livingEntity.removeEffect(ring_effect.effect);
    }
  }

  public static final void assignToolTip(final ItemStack stack, final List<ITextComponent> tooltip){
    final int effect_id = get_ring_effect(stack);
    if(effect_id > 0){
      final RingEffects ring_effect = RingEffects.values()[effect_id];
      if(ring_effect.has_levels){
        final int level = get_ring_effect_level(stack);
        tooltip.add(new StringTextComponent(ring_effect.translation_key + " " + level).withStyle(TextFormatting.GRAY));
      }
      else{
        tooltip.add(new StringTextComponent(ring_effect.translation_key).withStyle(TextFormatting.GRAY));
      }
    }
  }

  public static final void set_ring_effects(final ItemStack stack){
    final Random random = new Random();
    switch(Weight.getWeightedValue(random,  Values.common_ring_weight.get(), Values.good_ring_weight.get(),
             Values.rare_ring_weight.get(), Values.unique_ring_weight.get())){
    case 0: common_ring_effects(random, stack, ItemValue.COMMON); break;
    case 1:   good_ring_effects(random, stack, ItemValue.GOOD); break;
    case 2:   rare_ring_effects(random, stack, ItemValue.RARE); break;
    case 3: unique_ring_effects(random, stack, ItemValue.EPIC); break;
    }
  }

  private static final void common_ring_effects(final Random random, final ItemStack stack, final ItemValue value){
    switch(random.nextInt(4)){
    case 0: write_nbt(stack, SPEED,        1, value); break;
    case 1: write_nbt(stack, HASTE,        1, value); break;
    case 2: write_nbt(stack, LUCK,         1, value); break;
    case 3: write_nbt(stack, EXTRA_HEALTH, 1, value); break;
    }
  }

  private static final void good_ring_effects(final Random random, final ItemStack stack, final ItemValue value){
    switch(random.nextInt(6)){
    case 0: write_nbt(stack, SPEED,        2, value); break;
    case 1: write_nbt(stack, HASTE,        2, value); break;
    case 2: write_nbt(stack, LUCK,         2, value); break;
    case 3: write_nbt(stack, EXTRA_HEALTH, 2, value); break;
    case 4: write_nbt(stack, STRENGTH,     1, value); break;
    case 5: write_nbt(stack, JUMP,         1, value); break;
    }
  }
  
  private static final void rare_ring_effects(final Random random, final ItemStack stack, final ItemValue value){
    switch(random.nextInt(6)){
    case 0: write_nbt(stack, SPEED,        3, value); break;
    case 1: write_nbt(stack, HASTE,        3, value); break;
    case 2: write_nbt(stack, LUCK,         3, value); break;
    case 3: write_nbt(stack, EXTRA_HEALTH, 3, value); break;
    case 4: write_nbt(stack, STRENGTH,     2, value); break;
    case 5: write_nbt(stack, JUMP,         2, value); break;
    }
  }
  
  private static final void unique_ring_effects(final Random random, final ItemStack stack, final ItemValue value){
    switch(random.nextInt(8)){
    case 0: write_nbt(stack, SPEED,           4, value); break;
    case 1: write_nbt(stack, EXTRA_HEALTH,    4, value); break;
    case 2: write_nbt(stack, STRENGTH,        3, value); break;
    case 3: write_nbt(stack, JUMP,            3, value); break;
    case 4: write_nbt(stack, FIRE_IMMUNITY,   1, value); break;
    case 5: write_nbt(stack, WATER_BREATHING, 1, value); break;
    case 6: write_nbt(stack, NIGHT_VISION,    1, value); break;
    case 7: write_nbt(stack, INVISIBILITY,    1, value); break;
    }
  }

  /**
   * This method is very similar to the addEnchantment() method in the ItemStack class.
   */
  private static final void write_nbt(final ItemStack stack, final RingEffects effect, final int level, final ItemValue value){
    final CompoundNBT nbt = ItemUtil.getItemStackNBT(stack); // get or create new compound
    nbt.putByte("RingEffect", (byte)effect.id); // add to compound
    nbt.putByte("RingEffectLevel", (byte)level);
    nbt.putByte("Value", (byte)value.value);
    stack.setTag(nbt); // return compound to ItemStack
  }

  public static final byte get_ring_effect(final ItemStack stack){
    final CompoundNBT tag = stack.getTag();
    if(tag != null){
      return tag.getByte("RingEffect");
    }
    return 0;
  }
  
  public static final byte get_ring_effect_level(final ItemStack stack){
    final CompoundNBT tag = stack.getTag();
    if(tag != null){
      return tag.getByte("RingEffectLevel");
    }
    return 0;
  }
  
  public static final byte get_ring_rarity(final ItemStack stack){
    final CompoundNBT tag = stack.getTag();
    if(tag != null){
      return tag.getByte("Value");
    }
    return 0;
  }

}
