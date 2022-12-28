package ml.empee.seniorTrial.controllers;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import ml.empee.commandsManager.command.CommandExecutor;
import ml.empee.commandsManager.command.CommandNode;
import ml.empee.commandsManager.parsers.types.annotations.IntegerParam;
import ml.empee.seniorTrial.SeniorTrialPlugin;
import ml.empee.seniorTrial.utils.helpers.plugin.StoppableBean;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.C;

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
