package addsynth.core.gui.widgets.scrollbar;

import addsynth.core.ADDSynthCore;
import addsynth.core.util.StringUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public abstract class AbstractListEntry<E> extends AbstractButton {

  private final ResourceLocation highlight_texture = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/gui_textures.png");
  private final int texture_x = 0;
  private final int texture_y = 224;

  protected int entry_id;
  // private String text; // not much of a performance benefit
  protected boolean selected;
  protected AbstractScrollbar responder;

  public AbstractListEntry(int x, int y, int width, int height){
    super(x, y, width, height, new StringTextComponent(""));
  }

  public final void setScrollbar(final AbstractScrollbar scrollbar){
    this.responder = scrollbar;
  }

  /** This is the code that draws a transparent white box under the list entry
   *  whenever the player mouses over this list entry or it is selected.   */
  protected final void drawListEntryHighlight(final MatrixStack matrix, final Minecraft minecraft){
    if((isHovered && StringUtil.StringExists(getMessage().getString())) || selected){
      minecraft.getTextureManager().bind(highlight_texture);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      blit(matrix, x, y, texture_x, texture_y, width, height);
    }
  }

  @Override
  public void onPress(){
    if(StringUtil.StringExists(getMessage().getString())){
      responder.setSelected(entry_id, true, false);
    }
  }

  public abstract void set(final int entry_id, final E value);

  /** Do not use this to set the selected entry. Use Scrollbar.setSelected(). */
  public void setSelected(final int selected_entry_id){
    this.selected = selected_entry_id >= 0 && entry_id == selected_entry_id;
  }

  public abstract void setNull();

  @Override
  public final void playDownSound(SoundHandler p_playDownSound_1_){
  }

}
