package ml.empee.seniorTrial.controllers.views;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import java.util.List;
import java.util.stream.Collectors;
import ml.empee.seniorTrial.controllers.RegionController;
import ml.empee.seniorTrial.model.SeniorRegion;
import ml.empee.seniorTrial.services.RegionService;
import ml.empee.seniorTrial.utils.helpers.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RegionsListMenu {

  private final ChestGui gui = new ChestGui(6, "Regions");
  private final PaginatedPane pane = new PaginatedPane(9, 5);
  private final RegionService regionService;
  private final RegionController regionController;

  public RegionsListMenu(RegionController controller, RegionService regionService) {
    this.regionService = regionService;
    this.regionController = controller;

    setupGui();
  }

  public void open(Player player) {
    pane.populateWithGuiItems(getRegions());
    gui.update();
    gui.show(player);
  }

  private void setupGui() {
    gui.setOnGlobalClick(e -> e.setCancelled(true));
    gui.addPane(pane);
  }

  private List<GuiItem> getRegions() {
    return regionService.findAll().stream()
        .map(r -> new GuiItem(getRegionItem(r), e ->
            RegionMenu.open(regionController, r, (Player) e.getWhoClicked())
        )).collect(Collectors.toList());
  }

  private ItemStack getRegionItem(SeniorRegion region) {
    return ItemBuilder.of(Material.BOOK).setMeta(
        m -> m.setDisplayName("&e" + region.getName())
    ).build();
  }

}
