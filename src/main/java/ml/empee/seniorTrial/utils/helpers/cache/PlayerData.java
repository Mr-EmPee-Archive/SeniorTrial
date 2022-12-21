package ml.empee.seniorTrial.utils.helpers.cache;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlayerData<T> {

  public static <T> PlayerData<T> of(@NotNull Player player, T data) {
    return new PlayerData<>(player.getUniqueId(), player, data);
  }

  public static <T> PlayerData<T> of(@NotNull UUID uuid, T data) {
    return new PlayerData<>(uuid, null, data);
  }

  private final UUID uuid;
  @Nullable
  private final Player player;

  private T data;

}
