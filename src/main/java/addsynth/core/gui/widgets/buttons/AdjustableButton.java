package addsynth.core.gui.widgets.buttons;

import javax.annotation.Nonnull;
import addsynth.core.gui.widgets.WidgetUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.text.StringTextComponent;

/** Hey! This has a maximum height of 20! So setting it any higher will have no effect. */
public abstract class AdjustableButton extends AbstractButton {

  private static final int max_width = 200;
  private static final int max_height = 20;

  /**
   * This constructor passes an empty string to the button. Only use this if you intend to
   * change the button text on a per-frame basis, such as responding to changes to a TileEntity.
   * @param x
   * @param y
   * @param width
   * @param height
   */
  public AdjustableButton(int x, int y, int width, int height){
    super(x, y, width, height, new StringTextComponent(""));
  }

  /**
   * Only use this constructor if the button text remains static.
   * @param x
   * @param y
   * @param width
   * @param height
   * @param buttonText
   */
  public AdjustableButton(int x, int y, int width, int height, @Nonnull String buttonText){
    super(x, y, width, height, new StringTextComponent(buttonText));
  }

    /**
     * Draws this button to the screen. This is my own overridden method. It auto adjusts for a variable width AND height.
     * Otherwise it is exactly the same as {@link Widget#renderButton}.
     */
    @Override
    @SuppressWarnings("resource")
    public void renderButton(MatrixStack matrix, final int mouseX, final int mouseY, final float partialTicks){
      final Minecraft minecraft = Minecraft.getInstance();
      minecraft.getTextureManager().bind(WIDGETS_LOCATION);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
      
      final int hover_state = this.getYImage(this.isHovered);
      final int button_texture_y = 46 + (hover_state * 20);
      
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      
      WidgetUtil.crossSplitRender(matrix, x, y, 0, button_texture_y, width, height, max_width, max_height);
      this.renderBg(matrix, minecraft, mouseX, mouseY);
      
      final int text_color = getFGColor(); // 14737632; TODO: Is this the text color I'm looking for? Move this to constants. But now there's one in GuiUtil as well.
      drawCenteredString(matrix, minecraft.font, this.getMessage(), x + (width/2), y + (height/2) - 4, text_color);
    }

}
