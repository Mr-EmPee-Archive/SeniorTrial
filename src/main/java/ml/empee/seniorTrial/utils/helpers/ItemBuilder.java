package ml.empee.seniorTrial.utils.helpers;

import java.util.function.Consumer;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;


public class ItemBuilder {

  private final MetaBuilder meta;
  private final ItemStack item;

  public static ItemBuilder of(Material material) {
    return new ItemBuilder(new ItemStack(material));
  }

  public static ItemBuilder of(ItemStack item) {
    return new ItemBuilder(item);
  }

  public static ItemBuilder skullOf(OfflinePlayer player) {
    ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
    SkullMeta meta = (SkullMeta) skull.getItemMeta();
    meta.setOwningPlayer(player);
    skull.setItemMeta(meta);

    return ItemBuilder.of(skull);
  }

  private ItemBuilder(ItemStack item) {
    this.item = item;
    meta = MetaBuilder.of(item.getItemMeta());
  }

  public ItemBuilder setAmount(int amount) {
    item.setAmount(amount);
    return this;
  }

  public ItemBuilder setMeta(Consumer<MetaBuilder> consumer) {
    consumer.accept(meta);
    return this;
  }

  public ItemStack build() {
    item.setItemMeta(meta.getMeta());
    return item.clone();
  }

}
