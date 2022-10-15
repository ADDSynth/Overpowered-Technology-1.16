package addsynth.core.game.item.constants;

import net.minecraft.item.Rarity;
import net.minecraft.util.text.TextFormatting;

public enum ItemValue {

  COMMON   (0, Rarity.COMMON,   Rarity.COMMON.color),
  GOOD     (1, Rarity.UNCOMMON, Rarity.UNCOMMON.color),
  RARE     (2, Rarity.RARE,     Rarity.RARE.color),
  EPIC     (3, Rarity.EPIC,     Rarity.EPIC.color),
  LEGENDARY(4, Rarity.EPIC,     TextFormatting.GOLD);

  public final int value;
  public final Rarity rarity;
  public final TextFormatting color;
  
  private ItemValue(final int value, final Rarity rarity, final TextFormatting color){
    this.value = value;
    this.rarity = rarity;
    this.color = color;
  }

}
