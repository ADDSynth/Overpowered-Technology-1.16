package addsynth.overpoweredmod.game.core;

import addsynth.core.game.items.ItemUtil;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.game.Names;
import addsynth.overpoweredmod.items.LensItem;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public enum Lens {

  WHITE  (0, Names.FOCUS_LENS,   TextFormatting.WHITE,        MaterialColor.SNOW),
  RED    (1, Names.RED_LENS,     TextFormatting.DARK_RED,     MaterialColor.COLOR_RED),
  ORANGE (2, Names.ORANGE_LENS,  TextFormatting.GOLD,         MaterialColor.COLOR_ORANGE),
  YELLOW (3, Names.YELLOW_LENS,  TextFormatting.YELLOW,       MaterialColor.GOLD),
  GREEN  (4, Names.GREEN_LENS,   TextFormatting.DARK_GREEN,   MaterialColor.EMERALD),
  CYAN   (5, Names.CYAN_LENS,    TextFormatting.AQUA,         MaterialColor.DIAMOND),
  BLUE   (6, Names.BLUE_LENS,    TextFormatting.BLUE,         MaterialColor.LAPIS),
  MAGENTA(7, Names.MAGENTA_LENS, TextFormatting.LIGHT_PURPLE, MaterialColor.COLOR_MAGENTA);

  static {
    Debug.log_setup_info("Begin loading Lens class...");
  }

  public final LensItem lens;
  public final MaterialColor color;

  private Lens(final int index, final ResourceLocation name, final TextFormatting format_code, final MaterialColor material){
    lens = new LensItem(index, name, format_code);
    this.color = material;
  }

  public static final Item focus_lens = WHITE.lens;
  public static final Item red        = RED.lens;
  public static final Item orange     = ORANGE.lens;
  public static final Item yellow     = YELLOW.lens;
  public static final Item green      = GREEN.lens;
  public static final Item cyan       = CYAN.lens;
  public static final Item blue       = BLUE.lens;
  public static final Item magenta    = MAGENTA.lens;

  public static final Item[] index = { focus_lens, red, orange, yellow, green, cyan, blue, magenta};
  
  public static final int get_index(final ItemStack stack){
    if(ItemUtil.itemStackExists(stack)){
      return get_index(stack.getItem());
    }
    return -1;
  }
  
  public static final int get_index(final Item lens){
    if(lens != null){
      if(lens instanceof LensItem){
        return ((LensItem)lens).index;
      }
    }
    return -1;
  }
  
  static {
    Debug.log_setup_info("Finished loading Lens class.");
  }

}
