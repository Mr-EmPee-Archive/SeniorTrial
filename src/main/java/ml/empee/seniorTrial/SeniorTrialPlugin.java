package ml.empee.seniorTrial;

import com.mongodb.BasicDBObject;
import ml.empee.commandsManager.CommandManager;
import ml.empee.commandsManager.command.CommandExecutor;
import ml.empee.commandsManager.parsers.ParserManager;
import ml.empee.configurator.ConfigurationManager;
import ml.empee.seniorTrial.config.Config;
import ml.empee.seniorTrial.controllers.PluginController;
import ml.empee.seniorTrial.controllers.RegionController;
import ml.empee.seniorTrial.controllers.parsers.RegionParser;
import ml.empee.seniorTrial.controllers.parsers.WhitelistedPlayerParser;
import ml.empee.seniorTrial.controllers.parsers.annotations.WhitelistedPlayer;
import ml.empee.seniorTrial.listeners.RegionProtector;
import ml.empee.seniorTrial.model.SeniorRegion;
import ml.empee.seniorTrial.repositories.RegionRepo;
import ml.empee.seniorTrial.services.RegionService;
import ml.empee.seniorTrial.utils.MCLogger;
import ml.empee.seniorTrial.utils.helpers.plugin.AbstractPlugin;
import org.bukkit.plugin.PluginManager;

public final class SeniorTrialPlugin extends AbstractPlugin {

  private static final String PREFIX = "  &5SeniorTrial &8»&r ";

  @Override
  public void onEnable() {
    MCLogger.setPrefix(PREFIX);

    addConfigs();
    addServices();
    addCommands();
    addListeners();

    testDbConnection();
  }

  private void testDbConnection() {
    try {
      MCLogger.info("Testing database connection...");
      getBean(Config.class).getMongoDatabase().runCommand(new BasicDBObject("ping", "1"));
      MCLogger.info("Test succeeded!");
    } catch (Exception e) {
      throw new RuntimeException("Failed to connect to MongoDB", e);
    }
  }

  private void addServices() {
    addBean(new RegionService(new RegionRepo()));
  }
  private void addListeners() {
    PluginManager pluginManager = getServer().getPluginManager();

    pluginManager.registerEvents(addBean(new RegionProtector()), this);
  }
  private void addConfigs() {
    addBean(ConfigurationManager.loadConfiguration(new Config(this)));
  }
  private void addCommands() {
    CommandExecutor.setPrefix(MCLogger.getPrefix());
    CommandManager commandManager = new CommandManager(this);
    ParserManager parserManager = commandManager.getParserManager();

    parserManager.registerParser(new RegionParser(), null, SeniorRegion.class);
    parserManager.registerParser(new WhitelistedPlayerParser(), WhitelistedPlayer.class);

    addBean(new RegionController());
    commandManager.registerCommand(addBean(new PluginController()));
  }

}
