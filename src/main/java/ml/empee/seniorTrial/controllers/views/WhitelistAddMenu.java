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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WhitelistAddMenu extends PaginatedMenu {

  private final RegionController regionController;
  private final SeniorRegion region;

  private WhitelistAddMenu(SeniorRegion region) {
    super("Whitelist add");

    this.region = region;
    this.regionController = SeniorTrialPlugin.getBean(RegionController.class);
  }

  public static void open(SeniorRegion region, Player player) {
    if(player.hasPermission(Permissions.WHITE_LIST_ADD)) {
      new WhitelistAddMenu(region).show(player);
    } else {
      MCLogger.error(player, "You can't open this menu!");
      player.closeInventory();
    }
  }

  private List<GuiItem> getOnlinePlayerSkulls() {
    return Bukkit.getOnlinePlayers().stream()
        .filter(p -> !region.getWhitelist().contains(p.getUniqueId()))
        .map(p -> new GuiItem(getSkull(p), e -> {
          Player player = (Player) e.getWhoClicked();
          regionController.whitelistAdd(player, region, p);
          player.closeInventory();
        })).collect(Collectors.toList());
  }

  private ItemStack getSkull(Player player) {
    return ItemBuilder.skullOf(player).setMeta(
        m -> m.setDisplayName("&e" + player.getName())
    ).build();
  }

  @Override
  protected List<GuiItem> populateMenu() {
    return getOnlinePlayerSkulls();
  }
}
