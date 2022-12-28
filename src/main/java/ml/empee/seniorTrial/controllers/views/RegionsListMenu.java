package ml.empee.seniorTrial.controllers.views;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Pattern;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import ml.empee.seniorTrial.SeniorTrialPlugin;
import ml.empee.seniorTrial.controllers.RegionController;
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
