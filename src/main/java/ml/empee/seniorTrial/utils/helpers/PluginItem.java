package ml.empee.seniorTrial.utils.helpers;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PluginItem {

  private final String name;
  private final ItemStack item;
  private final int version;

  private final ItemMeta meta;
  private final DataContainer data;

  public static PluginItem of(String name, int version, ItemStack item) {
    return new PluginItem(name, version, item);
  }

  private PluginItem(String name, int version, ItemStack item) {
    this.name = name;
    this.item = item;
    this.meta = item.getItemMeta();
    this.version = version;

    data = new DataContainer(name, meta);
    data.setInt("version", version);
  }

  public ItemStack build() {
    item.setItemMeta(meta);
    return item.clone();
  }

  public boolean isPluginItem(ItemStack item, boolean ignoreVersion) {
    DataContainer data = new DataContainer(name, item.getItemMeta());
    int itemVersion = data.getInt("version", -1);
    if(itemVersion == -1) {
      return false;
    } else {
      return ignoreVersion || itemVersion == version;
    }
  }

  public boolean isPluginItem(ItemStack item) {
    return isPluginItem(item, false);
  }

}
