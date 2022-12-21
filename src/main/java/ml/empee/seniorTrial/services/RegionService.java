package ml.empee.seniorTrial.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ml.empee.seniorTrial.model.SeniorRegion;
import ml.empee.seniorTrial.repositories.RegionRepo;
import ml.empee.seniorTrial.utils.helpers.plugin.StoppableBean;
import org.bson.types.ObjectId;
import org.bukkit.Location;

@RequiredArgsConstructor
public final class RegionService {

  private final RegionRepo repo;

  public Optional<SeniorRegion> findById(ObjectId id) {
    return repo.findById(id);
  }
  public List<SeniorRegion> findRegionsAt(Location location) {
    return findAll().stream()
        .filter(r -> r.getRegion().isRegionBlock(location))
        .collect(Collectors.toList());
  }
  public Optional<SeniorRegion> findByName(String name) {
    return findAll().stream()
        .filter(r -> r.getName().equalsIgnoreCase(name))
        .findFirst();
  }
  public List<SeniorRegion> findAll() {
    return repo.findAll();
  }
  public void create(SeniorRegion region) {
    findByName(region.getName()).ifPresent(r -> {
      throw new IllegalArgumentException("Region with name " + region.getName() + " already exists");
    });

    repo.save(region);
  }
  public void update(ObjectId id, SeniorRegion region) {
    findById(id).orElseThrow(
        () -> new IllegalArgumentException("Region with id " + id + " does not exist!")
    );

    repo.update(id, region);
  }
  public void delete(ObjectId id) {
    findById(id).orElseThrow(
        () -> new IllegalArgumentException("Region with id " + id + " does not exist!")
    );

    repo.delete(id);
  }

}
