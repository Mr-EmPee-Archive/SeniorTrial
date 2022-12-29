package ml.empee.seniorTrial.controllers.views;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import java.util.List;
import java.util.stream.Collectors;
import ml.empee.seniorTrial.SeniorTrialPlugin;
import ml.empee.seniorTrial.model.SeniorRegion;
import ml.empee.seniorTrial.services.RegionService;
import ml.empee.seniorTrial.utils.helpers.ItemBuilder;
import ml.empee.seniorTrial.utils.helpers.view.PaginatedMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RegionsListMenu extends PaginatedMenu {
  private final RegionService regionService;

  private RegionsListMenu() {
    super("Regions");

    this.regionService = SeniorTrialPlugin.getBean(RegionService.class);
  }

  public static void open(Player player) {
    new RegionsListMenu().show(player);
  }

  private List<GuiItem> getRegions() {
    return regionService.findAll().stream()
        .map(r -> new GuiItem(getRegionItem(r), e ->
            RegionMenu.open(r, (Player) e.getWhoClicked())
        )).collect(Collectors.toList());
  }

  private ItemStack getRegionItem(SeniorRegion region) {
    return ItemBuilder.of(Material.BOOK).setMeta(
        m -> m.setDisplayName("&e" + region.getName())
    ).build();
  }

  @Override
  protected List<GuiItem> populateMenu() {
    return getRegions();
  }
}
