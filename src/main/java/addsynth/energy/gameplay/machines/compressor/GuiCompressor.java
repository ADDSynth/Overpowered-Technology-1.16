package addsynth.energy.gameplay.machines.compressor;

import addsynth.core.gui.util.GuiUtil;
import addsynth.energy.gameplay.reference.GuiReference;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public final class GuiCompressor extends GuiEnergyBase<TileCompressor, ContainerCompressor> {

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 79, 160, 5, 8, 194);
  
  public GuiCompressor(final ContainerCompressor container, final PlayerInventory player_inventory, final ITextComponent title){
    super(176, 182, container, player_inventory, title, GuiReference.compressor);
  }

  @Override
  protected final void renderBg(MatrixStack matrix, final float partialTicks, final int mouseX, final int mouseY){
    guiUtil.draw_background_texture(matrix);
    work_progress_bar.draw(matrix, this, tile);
  }

  @Override
  protected final void renderLabels(MatrixStack matrix, final int mouseX, final int mouseY){
    guiUtil.draw_title(matrix, this.title);
    draw_energy_usage(matrix);
    draw_status(matrix, tile.getStatus());
    GuiUtil.drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 80, 42);
    GuiUtil.draw_text_center(matrix, work_progress_bar.getWorkTimeProgress(), guiUtil.center_x, 67);
    draw_time_left(matrix, 88);
  }

}
