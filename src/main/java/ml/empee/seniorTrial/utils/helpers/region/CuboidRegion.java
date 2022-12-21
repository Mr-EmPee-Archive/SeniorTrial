package ml.empee.seniorTrial.utils.helpers.region;

import java.util.Objects;
import java.util.function.Consumer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter @Setter
@NoArgsConstructor
public class CuboidRegion implements Region {

  private Location firstCorner;
  private Location secondCorner;
  private transient World world;

  public CuboidRegion(Location firstCorner, Location secondCorner) {
    this.firstCorner = firstCorner;
    this.secondCorner = secondCorner;
    validateCorners();
  }

  private void validateCorners() {
    if (!Objects.equals(firstCorner.getWorld(), secondCorner.getWorld())) {
      throw new IllegalArgumentException("Two corners of a region must be in the same dimension");
    }

    int minX = Math.min(firstCorner.getBlockX(), secondCorner.getBlockX());
    int maxX = Math.max(firstCorner.getBlockX(), secondCorner.getBlockX());
    int minY = Math.min(firstCorner.getBlockY(), secondCorner.getBlockY());
    int maxY = Math.max(firstCorner.getBlockY(), secondCorner.getBlockY());
    int minZ = Math.min(firstCorner.getBlockZ(), secondCorner.getBlockZ());
    int maxZ = Math.max(firstCorner.getBlockZ(), secondCorner.getBlockZ());

    this.firstCorner = new Location(firstCorner.getWorld(), minX, minY, minZ);
    this.secondCorner = new Location(secondCorner.getWorld(), maxX, maxY, maxZ);
    world = firstCorner.getWorld();
  }

  public void setFirstCorner(@NotNull Location firstCorner) {
    this.firstCorner = firstCorner;

    if(secondCorner != null) {
      validateCorners();
    }
  }

  public void setSecondCorner(@NotNull Location secondCorner) {
    this.secondCorner = secondCorner;

    if(firstCorner != null) {
      validateCorners();
    }
  }

  /**
   * Get the minimum corner of this Region
   *
   * @return The corner with the lowest X, Y, and Z values
   */
  @NotNull
  public Location getFirstCorner() {
    return firstCorner.clone();
  }

  /**
   * Get the maximum corner of this Region
   *
   * @return The corner with the highest X, Y, and Z values
   */
  @NotNull
  public Location getSecondCorner() {
    return secondCorner.clone();
  }

  public boolean isRegionBlock(@NotNull Location location) {
    if (!Objects.equals(location.getWorld(), getWorld())) {
      return false;
    }

    return isRegionBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ());
  }
  public boolean isRegionBlock(int x, int y, int z) {
    return x >= firstCorner.getBlockX() && x <= secondCorner.getBlockX() &&
        y >= firstCorner.getBlockY() && y <= secondCorner.getBlockY() &&
        z >= firstCorner.getBlockZ() && z <= secondCorner.getBlockZ();
  }

  public void forEachRegionBlock(Consumer<Location> consumer) {
    for(int x=firstCorner.getBlockX(); x<=secondCorner.getBlockX(); x++) {
      for(int y=firstCorner.getBlockY(); y<=secondCorner.getBlockY(); y++) {
        for(int z=firstCorner.getBlockZ(); z<=secondCorner.getBlockZ(); z++) {
          consumer.accept(new Location(getWorld(), x, y, z));
        }
      }
    }
  }

}
