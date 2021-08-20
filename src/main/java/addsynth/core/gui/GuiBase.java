package addsynth.core.gui;

import addsynth.core.gui.util.GuiUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/** Extend from this class if you just want your own gui screen. */
public abstract class GuiBase extends Screen {

  protected final GuiUtil guiUtil;

  protected GuiBase(final int width, final int height, final ITextComponent title, final ResourceLocation gui_texture){
    super(title);
    guiUtil = new GuiUtil(gui_texture, width, height);
  }

  @Override
  public void render(MatrixStack matrix, int mouse_x, int mouse_y, float partialTicks){
    // REPLICA: This is a replica of ContainerScreen.render() but without drawing any ItemStacks. Make sure it matches whenever we update Forge.
    super.renderBackground(matrix);
    drawGuiBackgroundLayer(matrix, partialTicks, mouse_x, mouse_y);
    // net.minecraftforge.common.MinecraftForge.EVENT_BUS.pose(new net.minecraftforge.client.event.GuiContainerEvent.DrawBackground(this, mouse_x, mouse_y));
    RenderSystem.disableRescaleNormal();
    RenderSystem.disableDepthTest();
    super.render(matrix, mouse_x, mouse_y, partialTicks);
    RenderSystem.pushMatrix();
    RenderSystem.translatef((float)guiUtil.guiLeft, (float)guiUtil.guiTop, 0.0f);
    RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
    RenderSystem.enableRescaleNormal();
    
    RenderSystem.glMultiTexCoord2f(33986,  240.0f,  240.0f);
    drawGuiForegroundLayer(matrix, mouse_x, mouse_y);
    RenderSystem.popMatrix();
    RenderSystem.enableDepthTest();
  }

  @Override
  public final boolean isPauseScreen(){
    return false;
  }

  protected void drawGuiBackgroundLayer(MatrixStack matrix, float partialTicks, int mouse_x, int mouse_y){
    guiUtil.draw_background_texture(matrix);
  }
  
  protected void drawGuiForegroundLayer(MatrixStack matrix, int mouse_x, int mouse_y){
  }

}
