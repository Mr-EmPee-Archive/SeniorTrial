package ml.empee.seniorTrial.model;

import com.google.common.base.Objects;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ml.empee.seniorTrial.utils.helpers.region.CuboidRegion;
import org.bson.types.ObjectId;
import org.bukkit.Location;

@Getter @Setter
@NoArgsConstructor
public final class SeniorRegion {

  private ObjectId id;
  private String name;
  private CuboidRegion region;
  private Set<UUID> whitelist = new HashSet<>();

  public SeniorRegion(String name, Location firstCorner, Location secondCorner) {
    this.name = name;
    this.region = new CuboidRegion(firstCorner, secondCorner);
  }

  public boolean isWhitelisted(UUID uuid) {
    return whitelist.contains(uuid);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SeniorRegion region = (SeniorRegion) o;
    return Objects.equal(name, region.name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }
}
