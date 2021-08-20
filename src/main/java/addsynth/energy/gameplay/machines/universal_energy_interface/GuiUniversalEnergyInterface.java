package addsynth.energy.gameplay.machines.universal_energy_interface;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.buttons.AdjustableButton;
import addsynth.core.util.StringUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.NetworkHandler;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.EnergyProgressBar;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public final class GuiUniversalEnergyInterface extends GuiEnergyBase<TileUniversalEnergyInterface, ContainerUniversalEnergyInterface> {

  private static final ResourceLocation universal_interface_gui_texture =
    new ResourceLocation(ADDSynthEnergy.MOD_ID,"textures/gui/universal_energy_interface.png");

  private final String mode_text   = StringUtil.translate("gui.addsynth_energy.common.mode");
  private final String energy_text = StringUtil.translate("gui.addsynth_energy.common.energy");

  private static final int button_width = 90;
  private final EnergyProgressBar energy_bar = new EnergyProgressBar(156, 18, 12, 34, 206, 28);
  
  private static final int line_1 = 21;
  private static final int line_2 = 41;

  public GuiUniversalEnergyInterface(final ContainerUniversalEnergyInterface container, final PlayerInventory player_inventory, final ITextComponent title){
    super(176, 60, container, player_inventory, title, universal_interface_gui_texture);
  }

  private static final class CycleTransferModeButton extends AdjustableButton {

    private final TileUniversalEnergyInterface tile;

    public CycleTransferModeButton(int xIn, int yIn, TileUniversalEnergyInterface tile){
      super(xIn, yIn, button_width, 16);
      this.tile = tile;
    }

    @Override
    public final void renderButton(MatrixStack matrix, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
      setMessage(new StringTextComponent(tile.get_transfer_mode().toString())); // TEST check to see if we need to redesign how to get the TRANSFER_MODE string, should only return a TranslationTextComponent?
      super.renderButton(matrix, p_renderButton_1_, p_renderButton_2_, p_renderButton_3_);
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new CycleTransferModeMessage(tile.getBlockPos()));
    }

  }

  @Override
  public final void init(){
    super.init();
    final int button_x = leftPos + guiUtil.center_x - (button_width / 2) + 4;
    addButton(new CycleTransferModeButton(button_x, topPos + 17, tile));
  }

  @Override
  protected final void renderBg(MatrixStack matrix, final float partialTicks, final int mouseX, final int mouseY){
    guiUtil.draw_background_texture(matrix);
    energy_bar.drawVertical(matrix, this, energy);
  }

  @Override
  protected final void renderLabels(MatrixStack matrix, final int mouseX, final int mouseY){
    guiUtil.draw_title(matrix, this.title);
    GuiUtil.draw_text_left(matrix, mode_text+":", 6, line_1);
    GuiUtil.draw_text_left(matrix, energy_text+":", 6, line_2);
    GuiUtil.draw_text_right(matrix, energy.getEnergy() + " / "+energy.getCapacity(), 130, line_2);
  }

}
