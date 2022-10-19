package addsynth.overpoweredmod.machines.portal.frame;

import addsynth.core.gui.GuiContainerBase;
import addsynth.overpoweredmod.game.reference.GuiReference;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public final class GuiPortalFrame extends GuiContainerBase<ContainerPortalFrame> {

  public GuiPortalFrame(final ContainerPortalFrame container, final PlayerInventory player_inventory, final ITextComponent title){
    super(container, player_inventory, title, GuiReference.portal_frame);
  }

  @Override
  protected final void renderLabels(MatrixStack matrix, final int mouseX, final int mouseY){
    guiUtil.draw_title(matrix, this.title);
  }

}
