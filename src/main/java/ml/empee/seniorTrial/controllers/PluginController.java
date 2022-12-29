package ml.empee.seniorTrial.controllers;

import ml.empee.commandsManager.command.CommandExecutor;
import ml.empee.commandsManager.command.CommandNode;
import ml.empee.commandsManager.parsers.types.annotations.IntegerParam;
import ml.empee.seniorTrial.SeniorTrialPlugin;
import ml.empee.seniorTrial.utils.helpers.plugin.StoppableBean;
import org.bukkit.command.CommandSender;

@CommandNode(label = "rg", aliases = {"region"}, exitNode = false)
public class PluginController extends CommandExecutor implements StoppableBean {

  public PluginController() {
    addSubController(SeniorTrialPlugin.getBean(RegionController.class));
  }

  @CommandNode(
      parent = "rg",
      label = "help",
      permission = "region.help",
      description = "Shows the help menu"
  )
  public void sendHelpMenu(CommandSender sender, @IntegerParam(min = 1, defaultValue = "1") int page) {
    getHelpMenu().sendHelpMenu(sender, page);
  }

  @Override
  public void stop() {
    unregister();
  }
}
