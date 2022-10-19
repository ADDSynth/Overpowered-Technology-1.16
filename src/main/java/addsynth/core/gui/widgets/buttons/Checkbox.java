package addsynth.core.gui.widgets.buttons;

import addsynth.core.gameplay.reference.GuiReference;
import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.WidgetUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.text.StringTextComponent;

public abstract class Checkbox extends AbstractButton {

  private static final int texture_x = 0;
  private static final int texture_y = 32;
  private static final int texture_width = 24;
  private static final int texture_height = 24;
  public static final int gui_size = 12;

  public Checkbox(final int x, final int y, String label){
    super(x, y, gui_size, gui_size, new StringTextComponent(label));
  }

  protected abstract boolean get_toggle_state();

  @Override
  public final void renderButton(MatrixStack matrix, final int mouseX, final int mouseY, final float partial_ticks){
    final boolean checked = get_toggle_state();
    WidgetUtil.common_button_render_setup(GuiReference.widgets);
    blit(matrix, x, y, gui_size, gui_size, checked ? texture_x : texture_x + texture_height, texture_y, texture_width, texture_height, 256, 256);
    GuiUtil.draw_text_left(matrix, getMessage().getString(), x + 16, y + 2);
  }

}
