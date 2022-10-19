package addsynth.overpoweredmod.machines.crystal_matter_generator;

import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.OnOffSwitch;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.game.reference.GuiReference;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public final class GuiCrystalMatterGenerator extends GuiEnergyBase<TileCrystalMatterGenerator, ContainerCrystalGenerator> {

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 89, 160, 5, 11, 194);
  
  private static final int work_percentage_text_y = 77; // OPTIMIZE any guis that have this variable and only use it once.

  public GuiCrystalMatterGenerator(final ContainerCrystalGenerator container, final PlayerInventory player_inventory, final ITextComponent title){
    super(176, 192, container, player_inventory, title, GuiReference.crystal_matter_generator);
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
    guiUtil.draw_text_center(matrix, work_progress_bar.getWorkTimeProgress(), work_percentage_text_y);
    draw_time_left(matrix, 98);
  }

}
