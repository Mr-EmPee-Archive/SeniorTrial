package ml.empee.seniorTrial.utils.helpers.view;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Pattern;
import java.util.List;
import ml.empee.seniorTrial.utils.helpers.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class PaginatedMenu {

  private final ChestGui gui;
  private final PaginatedPane itemsPane = new PaginatedPane(1, 1, 7, 4);

  protected PaginatedMenu(String title) {
    gui = new ChestGui(6, title);
  }

  protected abstract List<GuiItem> populateMenu();

  public void show(Player player) {
    setupGui();
    gui.show(player);
  }

  private void setupGui() {
    gui.setOnGlobalClick(e -> e.setCancelled(true));
    setBackground();
    itemsPane.populateWithGuiItems(populateMenu());
    gui.addPane(itemsPane);
    addPageButtons();
  }
  private void setBackground() {
    PatternPane background = new PatternPane(9, 6, new Pattern(
        "111111111", "100000001",
        "100000001", "100000001",
        "100000001", "111111111"
    ));

    ItemStack backgroundItem = ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE)
        .setMeta(m -> m
            .setDisplayName(" ")
        ).build();
    background.bindItem('1', new GuiItem(backgroundItem));
    gui.addPane(background);
  }
  private void addPageButtons() {
    if(itemsPane.getPages() <= 1) {
      return;
    }

    StaticPane buttonsPane = new StaticPane(3, 5, 3, 1, Pane.Priority.HIGH);

    GuiItem nextButton = nextButton();
    GuiItem previousButton = previousButton();
    previousButton.setVisible(false);

    nextButton.setAction(e -> {
      itemsPane.setPage(itemsPane.getPage() + 1);
      if (itemsPane.getPages() == itemsPane.getPage() + 1) {
        nextButton.setVisible(false);
      }

      previousButton.setVisible(true);
      gui.update();
    });

    previousButton.setAction(e -> {
      itemsPane.setPage(itemsPane.getPage() - 1);
      if (itemsPane.getPage() == 0) {
        previousButton.setVisible(false);
      }

      nextButton.setVisible(true);
      gui.update();
    });

    buttonsPane.addItem(previousButton, 0, 0);
    buttonsPane.addItem(nextButton, 2, 0);

    gui.addPane(buttonsPane);
  }
  private GuiItem nextButton() {
    ItemStack itemStack = ItemBuilder.of(Material.PURPLE_DYE)
        .setMeta(m -> m.setDisplayName("&eNext page"))
        .build();

    return new GuiItem(itemStack);
  }
  private GuiItem previousButton() {
    ItemStack itemStack = ItemBuilder.of(Material.MAGENTA_DYE)
        .setMeta(m -> m.setDisplayName("&ePrevious page"))
        .build();

    return new GuiItem(itemStack);
  }

}
