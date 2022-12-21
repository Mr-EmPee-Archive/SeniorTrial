package ml.empee.seniorTrial.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.Setter;
import ml.empee.configurator.ConfigFile;
import ml.empee.configurator.annotations.Path;
import ml.empee.seniorTrial.utils.MongoDBUtils;
import ml.empee.seniorTrial.utils.helpers.plugin.StoppableBean;
import org.bukkit.plugin.java.JavaPlugin;

@Setter
@Getter
public class Config extends ConfigFile implements StoppableBean {

  @Path("mongo.srv-protocol")
  private Boolean useSrv = true;
  @Path("mongo.username")
  private String username;
  @Path("mongo.password")
  private String password;
  @Path("mongo.host")
  private String host;

  private MongoClient mongoClient;
  private MongoDatabase mongoDatabase;

  public Config(JavaPlugin plugin) {
    super(plugin, "config.yml");
  }

  public MongoClient getMongoClient() {
    if (mongoClient != null) {
      return mongoClient;
    }

    try {
      mongoClient = MongoDBUtils.buildMongoClient(username, password, host, useSrv);
    } catch (Exception e) {
      throw new RuntimeException("Failed to connect to MongoDB", e);
    }

    return mongoClient;
  }
  public MongoDatabase getMongoDatabase() {
    if (mongoDatabase != null) {
      return mongoDatabase;
    }

    mongoDatabase = getMongoClient().getDatabase("seniorTrial");
    return mongoDatabase;
  }

  @Override
  public void stop() {
    if (mongoClient != null) {
      mongoClient.close();
    }
  }
}
