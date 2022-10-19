package addsynth.overpoweredmod.machines.plasma_generator;

import addsynth.core.gui.util.GuiUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.OnOffSwitch;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.game.reference.GuiReference;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public final class GuiPlasmaGenerator extends GuiEnergyBase<TilePlasmaGenerator, ContainerPlasmaGenerator> {

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 73, 166, 5, 7, 184);
  
  public GuiPlasmaGenerator(final ContainerPlasmaGenerator container, final PlayerInventory player_inventory, final ITextComponent title){
    super(183, 176, container, player_inventory, title, GuiReference.plasma_generator);
  }

  @Override
  public final void init(){
    super.init();
    addButton(new OnOffSwitch<>(this, tile));
  }

  @Override
  protected final void renderBg(MatrixStack matrix, float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture(matrix);
    work_progress_bar.draw(matrix, this, tile);
  }

  @Override
  protected final void renderLabels(MatrixStack matrix, int mouseX, int mouseY){
    guiUtil.draw_title(matrix, this.title);
    draw_status_after_switch(matrix, tile.getStatus());
    draw_energy_usage(matrix, 6, 38);
    GuiUtil.draw_text_center(matrix, work_progress_bar.getWorkTimeProgress(), 40, 62);
    draw_time_left(matrix, 82);
  }

}
