package ml.empee.seniorTrial.model;

import lombok.RequiredArgsConstructor;
import ml.empee.seniorTrial.utils.helpers.ItemBuilder;
import ml.empee.seniorTrial.utils.helpers.PluginItem;
import org.bukkit.Material;

@RequiredArgsConstructor
public enum PluginItems {
  WAND(
      PluginItem.of(
          "region_wand", 1,
          ItemBuilder.of(Material.STICK)
              .setMeta(meta -> meta
                  .setDisplayName("&eRegion Wand")
                  .setLore("\n\t&7Right click to set a region corner\t\n")
              ).build()
      )
  );

  private final PluginItem item;

  public PluginItem getItem() {
    return item;
  }
}
