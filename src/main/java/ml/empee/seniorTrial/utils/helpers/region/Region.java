package ml.empee.seniorTrial.utils.helpers.region;

import java.util.function.Consumer;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public interface Region {
  World getWorld();

  boolean isRegionBlock(@NotNull Location location);

  boolean isRegionBlock(int x, int y, int z);

  void forEachRegionBlock(Consumer<Location> consumer);
}
