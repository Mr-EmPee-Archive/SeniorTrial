package ml.empee.seniorTrial.controllers.parsers;

import java.util.List;
import java.util.stream.Collectors;
import ml.empee.commandsManager.parsers.DescriptionBuilder;
import ml.empee.commandsManager.parsers.ParameterParser;
import ml.empee.seniorTrial.SeniorTrialPlugin;
import ml.empee.seniorTrial.model.SeniorRegion;
import ml.empee.seniorTrial.services.RegionService;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

public class RegionParser extends ParameterParser<SeniorRegion> {

  private final RegionService regionService;

  public RegionParser() {
    regionService = SeniorTrialPlugin.getBean(RegionService.class);
  }

  @Override
  public DescriptionBuilder getDescriptionBuilder() {
    return new DescriptionBuilder("region", "This parameter identify a region by it's name");
  }

  @Override
  public SeniorRegion parse(int i, String... strings) {
    return regionService.findByName(strings[i]).orElseThrow(
        () -> new CommandException("Region with name &e" + strings[i] + "&r not found!")
    );
  }

  @Override
  protected List<String> buildSuggestions(CommandSender source, String arg) {
    return regionService.findAll().stream().map(SeniorRegion::getName).collect(Collectors.toList());
  }

  @Override
  public ParameterParser<SeniorRegion> copyParser() {
    RegionParser parser = new RegionParser();
    parser.label = label;
    parser.defaultValue = defaultValue;
    return parser;
  }
}
