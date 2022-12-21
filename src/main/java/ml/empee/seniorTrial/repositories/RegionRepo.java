package ml.empee.seniorTrial.repositories;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ml.empee.seniorTrial.SeniorTrialPlugin;
import ml.empee.seniorTrial.config.Config;
import ml.empee.seniorTrial.model.SeniorRegion;
import org.bson.types.ObjectId;

public class RegionRepo {
  private final MongoCollection<SeniorRegion> regions;

  public RegionRepo() {
    regions = SeniorTrialPlugin.getBean(Config.class).getMongoDatabase().getCollection("regions", SeniorRegion.class);
  }

  public void save(SeniorRegion region) {
    regions.insertOne(region);
  }

  public List<SeniorRegion> findAll() {
    return regions.find().into(new ArrayList<>());
  }

  public void delete(ObjectId id) {
    regions.deleteOne(eq("_id", id));
  }

  public Optional<SeniorRegion> findById(ObjectId id) {
    return Optional.ofNullable(regions.find(eq("_id", id)).first());
  }

  public void update(ObjectId id, SeniorRegion region) {
    regions.replaceOne(eq("_id", id), region);
  }

}
