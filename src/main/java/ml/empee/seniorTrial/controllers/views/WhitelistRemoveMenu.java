package ml.empee.seniorTrial.controllers.views;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import java.util.List;
import java.util.stream.Collectors;
import ml.empee.seniorTrial.SeniorTrialPlugin;
import ml.empee.seniorTrial.controllers.RegionController;
import ml.empee.seniorTrial.model.Permissions;
import ml.empee.seniorTrial.model.SeniorRegion;
import ml.empee.seniorTrial.utils.MCLogger;
import ml.empee.seniorTrial.utils.helpers.ItemBuilder;
import ml.empee.seniorTrial.utils.helpers.view.PaginatedMenu;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WhitelistRemoveMenu extends PaginatedMenu {

  private final RegionController regionController;
  private final SeniorRegion region;

  private WhitelistRemoveMenu(SeniorRegion region) {
    super("Whitelist remove");

    this.region = region;
    this.regionController = SeniorTrialPlugin.getBean(RegionController.class);
  }

  public static void open(SeniorRegion region, Player player) {
    if(player.hasPermission(Permissions.WHITE_LIST_REMOVE)) {
      new WhitelistRemoveMenu(region).show(player);
    } else {
      MCLogger.error(player, "You can't open this menu!");
      player.closeInventory();
    }
  }

  private List<GuiItem> getWhitelistedPlayerSkulls() {
    return region.getWhitelist().stream()
        .map(Bukkit::getOfflinePlayer)
        .map(p -> new GuiItem(getSkull(p), e -> {
          Player player = (Player) e.getWhoClicked();
          regionController.whitelistRemove(player, region, p);
          player.closeInventory();
        })).collect(Collectors.toList());
  }

  private ItemStack getSkull(OfflinePlayer player) {
    return ItemBuilder.skullOf(player).setMeta(
        m -> m.setDisplayName("&e" + player.getName())
    ).build();
  }

  @Override
  protected List<GuiItem> populateMenu() {
    return getWhitelistedPlayerSkulls();
  }
}
