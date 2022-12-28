package ml.empee.seniorTrial.controllers.views;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import ml.empee.seniorTrial.SeniorTrialPlugin;
import ml.empee.seniorTrial.controllers.RegionController;
import ml.empee.seniorTrial.model.SeniorRegion;
import ml.empee.seniorTrial.utils.helpers.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RegionMenu {
  private final ChestGui gui = new ChestGui(3, "Edit region");
  private final RegionController regionController;
  private final SeniorRegion region;

  public static void open(SeniorRegion region, Player player) {
    new RegionMenu(
        SeniorTrialPlugin.getBean(RegionController.class),
        region
    ).open(player);
  }

  private RegionMenu(RegionController regionController, SeniorRegion region) {
    this.regionController = regionController;
    this.region = region;

    setupGui();
  }

  public void open(Player player) {
    gui.show(player);
  }

  private void setupGui() {
    gui.setOnGlobalClick(event -> event.setCancelled(true));

    StaticPane pane = new StaticPane(9, 3);
    pane.addItem(renameRgItem(), 2, 1);
    pane.addItem(redefineCornersItem(), 3, 1);

    pane.addItem(whiteListAddItem(), 5, 1);
    pane.addItem(whiteListRemoveItem(), 6, 1);
    gui.addPane(pane);
  }

  private GuiItem whiteListAddItem() {
    ItemStack item = ItemBuilder.of(Material.GREEN_DYE)
        .setMeta(m -> m
            .setDisplayName("&eAdd to whitelist")
            .setLore("&7    Click to add a player to the region whitelist")
        ).build();

    return new GuiItem(item, e ->
        WhitelistAddMenu.open(region, (Player) e.getWhoClicked())
    );
  }

  private GuiItem whiteListRemoveItem() {
    ItemStack item = ItemBuilder.of(Material.RED_DYE)
        .setMeta(m -> m
            .setDisplayName("&eRemove from whitelist")
            .setLore("&7    Click to remove a player from the region whitelist")
        ).build();

    return new GuiItem(item, e ->
        WhitelistRemoveMenu.open(region, (Player) e.getWhoClicked())
    );
  }

  private GuiItem redefineCornersItem() {
    ItemStack item = ItemBuilder.of(Material.NETHERITE_SCRAP)
        .setMeta(m -> m
            .setDisplayName("&eRedefine corners")
            .setLore("&7    Click to redefine a region corners")
        ).build();

    return new GuiItem(item, e -> {
      Player player = (Player) e.getWhoClicked();
      player.closeInventory();
      regionController.redefineRegionCorners(player, region);
    });
  }

  private GuiItem renameRgItem() {
    ItemStack item = ItemBuilder.of(Material.WRITABLE_BOOK)
        .setMeta(m -> m
            .setDisplayName("&eRename Region")
            .setLore("&7    Click to rename a region")
        ).build();

    return new GuiItem(item, e ->
        RenameRegionMenu.open(region, (Player) e.getWhoClicked())
    );
  }

}
