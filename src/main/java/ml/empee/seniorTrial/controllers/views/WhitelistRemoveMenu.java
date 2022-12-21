package ml.empee.seniorTrial.controllers.views;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import java.util.List;
import java.util.stream.Collectors;
import ml.empee.seniorTrial.controllers.RegionController;
import ml.empee.seniorTrial.model.Permissions;
import ml.empee.seniorTrial.model.SeniorRegion;
import ml.empee.seniorTrial.utils.MCLogger;
import ml.empee.seniorTrial.utils.helpers.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WhitelistRemoveMenu {

  private final ChestGui gui = new ChestGui(6, "Whitelist remove");
  private final RegionController regionController;
  private final SeniorRegion region;

  public static void open(RegionController regionController, SeniorRegion region, Player player) {
    if(player.hasPermission(Permissions.WHITE_LIST_REMOVE)) {
      new WhitelistRemoveMenu(regionController, region).open(player);
    } else {
      MCLogger.error(player, "You can't open this menu!");
      player.closeInventory();
    }
  }

  private WhitelistRemoveMenu(RegionController regionController, SeniorRegion region) {
    this.regionController = regionController;
    this.region = region;

    setupGui();
  }

  public void open(Player player) {
    gui.show(player);
  }

  private void setupGui() {
    gui.setOnGlobalClick(e -> e.setCancelled(true));

    PaginatedPane pane = new PaginatedPane(9, 5);
    pane.populateWithGuiItems(getWhitelistedPlayerSkulls());
    gui.addPane(pane);
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

}
