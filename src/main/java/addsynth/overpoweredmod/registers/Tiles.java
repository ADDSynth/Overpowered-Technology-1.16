package addsynth.overpoweredmod.registers;

import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.TileAdvancedOreRefinery;
import addsynth.overpoweredmod.machines.black_hole.TileBlackHole;
import addsynth.overpoweredmod.machines.crystal_matter_generator.TileCrystalMatterGenerator;
import addsynth.overpoweredmod.machines.data_cable.TileDataCable;
import addsynth.overpoweredmod.machines.energy_extractor.TileCrystalEnergyExtractor;
import addsynth.overpoweredmod.machines.fusion.chamber.TileFusionChamber;
import addsynth.overpoweredmod.machines.fusion.converter.TileFusionEnergyConverter;
import addsynth.overpoweredmod.machines.gem_converter.TileGemConverter;
import addsynth.overpoweredmod.machines.identifier.TileIdentifier;
import addsynth.overpoweredmod.machines.inverter.TileInverter;
import addsynth.overpoweredmod.machines.laser.machine.TileLaserHousing;
import addsynth.overpoweredmod.machines.magic_infuser.TileMagicInfuser;
import addsynth.overpoweredmod.machines.plasma_generator.TilePlasmaGenerator;
import addsynth.overpoweredmod.machines.portal.control_panel.TilePortalControlPanel;
import addsynth.overpoweredmod.machines.portal.frame.TilePortalFrame;
import addsynth.overpoweredmod.machines.portal.rift.TilePortal;
import addsynth.overpoweredmod.machines.suspension_bridge.TileSuspensionBridge;
import net.minecraft.tileentity.TileEntityType;

public final class Tiles {

  public static final TileEntityType<TileCrystalEnergyExtractor> CRYSTAL_ENERGY_EXTRACTOR =
    TileEntityType.Builder.of(TileCrystalEnergyExtractor::new, OverpoweredBlocks.crystal_energy_extractor).build(null);

  public static final TileEntityType<TileGemConverter> GEM_CONVERTER =
    TileEntityType.Builder.of(TileGemConverter::new, OverpoweredBlocks.gem_converter).build(null);

  public static final TileEntityType<TileInverter> INVERTER =
    TileEntityType.Builder.of(TileInverter::new, OverpoweredBlocks.inverter).build(null);

  public static final TileEntityType<TileMagicInfuser> MAGIC_INFUSER =
    TileEntityType.Builder.of(TileMagicInfuser::new, OverpoweredBlocks.magic_infuser).build(null);

  public static final TileEntityType<TileIdentifier> IDENTIFIER =
    TileEntityType.Builder.of(TileIdentifier::new, OverpoweredBlocks.identifier).build(null);

  public static final TileEntityType<TileAdvancedOreRefinery> ADVANCED_ORE_REFINERY =
    TileEntityType.Builder.of(TileAdvancedOreRefinery::new, OverpoweredBlocks.advanced_ore_refinery).build(null);

  public static final TileEntityType<TileCrystalMatterGenerator> CRYSTAL_MATTER_REPLICATOR =
    TileEntityType.Builder.of(TileCrystalMatterGenerator::new, OverpoweredBlocks.crystal_matter_generator).build(null);

  public static final TileEntityType<TileSuspensionBridge> ENERGY_SUSPENSION_BRIDGE =
    TileEntityType.Builder.of(TileSuspensionBridge::new, OverpoweredBlocks.energy_suspension_bridge).build(null);

  public static final TileEntityType<TileDataCable> DATA_CABLE =
    TileEntityType.Builder.of(TileDataCable::new, OverpoweredBlocks.data_cable).build(null);

  public static final TileEntityType<TilePortalControlPanel> PORTAL_CONTROL_PANEL =
    TileEntityType.Builder.of(TilePortalControlPanel::new, OverpoweredBlocks.portal_control_panel).build(null);

  public static final TileEntityType<TilePortalFrame> PORTAL_FRAME =
    TileEntityType.Builder.of(TilePortalFrame::new, OverpoweredBlocks.portal_frame).build(null);

  public static final TileEntityType<TilePortal> PORTAL_BLOCK =
    TileEntityType.Builder.of(TilePortal::new, OverpoweredBlocks.portal).build(null);

  public static final TileEntityType<TileLaserHousing> LASER_MACHINE =
    TileEntityType.Builder.of(TileLaserHousing::new, OverpoweredBlocks.laser_housing).build(null);

  public static final TileEntityType<TileFusionEnergyConverter> FUSION_ENERGY_CONVERTER =
    TileEntityType.Builder.of(TileFusionEnergyConverter::new, OverpoweredBlocks.fusion_converter).build(null);

  public static final TileEntityType<TileFusionChamber> FUSION_CHAMBER =
    TileEntityType.Builder.of(TileFusionChamber::new, OverpoweredBlocks.fusion_chamber).build(null);

  public static final TileEntityType<TileBlackHole> BLACK_HOLE =
    TileEntityType.Builder.of(TileBlackHole::new, OverpoweredBlocks.black_hole).build(null);

  public static final TileEntityType<TilePlasmaGenerator> PLASMA_GENERATOR =
    TileEntityType.Builder.of(TilePlasmaGenerator::new, OverpoweredBlocks.plasma_generator).build(null);

}
