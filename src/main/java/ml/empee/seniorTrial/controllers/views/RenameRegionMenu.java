package ml.empee.seniorTrial.controllers.views;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.AnvilGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import ml.empee.seniorTrial.SeniorTrialPlugin;
import ml.empee.seniorTrial.controllers.RegionController;
import ml.empee.seniorTrial.model.SeniorRegion;
import ml.empee.seniorTrial.utils.helpers.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RenameRegionMenu {

  private final AnvilGui gui = new AnvilGui("Rename region");
  private final RegionController regionController;
  private final SeniorRegion region;

  public static void open(SeniorRegion region, Player player) {
    new RenameRegionMenu(
        SeniorTrialPlugin.getBean(RegionController.class),
        region
    ).show(player);
  }

  public void show(Player player) {
    setupGui();
    gui.show(player);
  }

  private void setupGui() {
    gui.setOnGlobalClick(event -> event.setCancelled(true));
    gui.setCost((short) 0);

    StaticPane firstPane = new StaticPane(1, 1);
    gui.getFirstItemComponent().addPane(firstPane);
    firstPane.addItem(regionIdentifier(), 0, 0);

    StaticPane secondPane = new StaticPane(1, 1);
    gui.getSecondItemComponent().addPane(secondPane);
    secondPane.addItem(simpleNametag(), 0, 0);

    StaticPane resultPane = new StaticPane(1, 1);
    gui.getResultComponent().addPane(resultPane);
    resultPane.addItem(confirmButton(), 0, 0);
  }

  private GuiItem simpleNametag() {
    return new GuiItem(ItemBuilder.of(Material.NAME_TAG).build());
  }

  private GuiItem regionIdentifier() {
    return new GuiItem(
        ItemBuilder.of(Material.MOJANG_BANNER_PATTERN)
        .setMeta(m -> m
            .setDisplayName("&c" + region.getName())
            .addFlags(ItemFlag.HIDE_POTION_EFFECTS)
        ).build()
    );
  }

  private GuiItem confirmButton() {
    ItemStack item = ItemBuilder.of(Material.FLOWER_BANNER_PATTERN)
        .setMeta(m -> m
            .setDisplayName("&aClick to confirm")
            .addFlags(ItemFlag.HIDE_POTION_EFFECTS)
        ).build();

    return new GuiItem(item, e -> {
      regionController.renameRegion(e.getWhoClicked(), region, gui.getRenameText());
      e.getWhoClicked().closeInventory();
    });
  }

}
