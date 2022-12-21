package ml.empee.seniorTrial.utils.helpers.cache;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import javax.swing.text.html.Option;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Holds data for a player and invalidate them if the player goes offline
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlayerContext<T> {

  private static final HashMap<String, PlayerContext<?>> contexts = new HashMap<>();

  private final HashMap<UUID, PlayerData<T>> data = new HashMap<>();

  static {
    JavaPlugin plugin = JavaPlugin.getProvidingPlugin(PlayerDataRemover.class);
    plugin.getServer().getPluginManager().registerEvents(new PlayerDataRemover(), plugin);
  }

  private static class PlayerDataRemover implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
      Player player = event.getPlayer();
      for (PlayerContext<?> container : contexts.values()) {
        container.data.computeIfPresent(
            player.getUniqueId(),
            (uuid, playerData) -> playerData.getPlayer() == null ? playerData : null
        );
      }
    }
  }

  public static <T> PlayerContext<T> get(String name) {
    return (PlayerContext<T>) contexts.computeIfAbsent(name, key -> new PlayerContext<>());
  }

  public void put(PlayerData<T> playerData) {
    data.put(playerData.getUuid(), playerData);
  }

  public boolean remove(Player player) {
    return remove(player.getUniqueId());
  }

  public boolean remove(UUID uuid) {
    return data.remove(uuid) != null;
  }

  public Optional<PlayerData<T>> get(UUID uuid) {
    return Optional.ofNullable(data.get(uuid));
  }

  public Optional<PlayerData<T>> get(Player player) {
    return get(player.getUniqueId());
  }

  public Optional<T> getData(UUID uuid) {
    return get(uuid).map(PlayerData::getData);
  }

  public Optional<T> getData(Player player) {
    return getData(player.getUniqueId());
  }

  @NotNull
  public PlayerData<T> getOrPut(PlayerData<T> playerData) {
    return data.computeIfAbsent(playerData.getUuid(), uuid -> playerData);
  }

  public Stream<PlayerData<T>> stream() {
    return data.values().stream();
  }

}
