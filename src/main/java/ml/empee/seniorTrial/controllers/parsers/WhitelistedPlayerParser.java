package ml.empee.seniorTrial.controllers.parsers;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import ml.empee.commandsManager.parsers.DescriptionBuilder;
import ml.empee.commandsManager.parsers.ParameterParser;
import ml.empee.seniorTrial.services.RegionService;
import ml.empee.seniorTrial.utils.helpers.plugin.AbstractPlugin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

public class WhitelistedPlayerParser extends ParameterParser<OfflinePlayer> {

  private final RegionService regionService;
  public WhitelistedPlayerParser() {
    this.regionService = AbstractPlugin.getBean(RegionService.class);
  }

  @Override
  public DescriptionBuilder getDescriptionBuilder() {
    return new DescriptionBuilder("whitelistedPlayer", "A region whitelisted player");
  }

  @Override
  public OfflinePlayer parse(int i, String... strings) {
    return regionService.findByName(strings[i-1]).get()
        .getWhitelist().stream()
        .map(Bukkit::getOfflinePlayer)
        .filter(offlinePlayer -> Objects.equals(offlinePlayer.getName(), strings[i]))
        .findFirst().orElseThrow(() -> new CommandException("The player &e" + strings[i] + " &cisn't whitelisted!"));
  }

  @Override
  protected List<String> buildSuggestions(CommandSender source, int offset, String[] args) {
    return regionService.findByName(args[offset-1]).get()
        .getWhitelist().stream()
        .map(Bukkit::getOfflinePlayer)
        .map(OfflinePlayer::getName)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  @Override
  public Class<?>[] getNeededParsers() {
    return new Class[] {
        RegionParser.class
    };
  }

  @Override
  public ParameterParser<OfflinePlayer> copyParser() {
    WhitelistedPlayerParser parser = new WhitelistedPlayerParser();
    parser.label = label;
    parser.defaultValue = defaultValue;
    return parser;
  }
}
