package ml.empee.seniorTrial.utils.helpers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor(staticName = "of")
public class MetaBuilder {
  @Getter
  private final ItemMeta meta;

  public MetaBuilder setDisplayName(String name) {
    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    return this;
  }

  public MetaBuilder setLore(String... lore) {
    meta.setLore(
        Arrays.stream(lore)
            .flatMap(line -> Arrays.stream(line.split("\n")))
            .map(l -> ChatColor.translateAlternateColorCodes('&', l))
            .map(l -> l.replace("\t", "  "))
            .collect(Collectors.toList())
    );
    return this;
  }

  public MetaBuilder addEnchant(Enchantment enchant, int level) {
    meta.addEnchant(enchant, level, true);
    return this;
  }

  public MetaBuilder addFlags(ItemFlag... flags) {
    meta.addItemFlags(flags);
    return this;
  }
}
