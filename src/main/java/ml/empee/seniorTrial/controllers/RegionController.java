package ml.empee.seniorTrial.controllers;

import ml.empee.commandsManager.command.CommandNode;
import ml.empee.commandsManager.command.Controller;
import ml.empee.seniorTrial.SeniorTrialPlugin;
import ml.empee.seniorTrial.controllers.listeners.RegionDefiner;
import ml.empee.seniorTrial.controllers.parsers.annotations.WhitelistedPlayer;
import ml.empee.seniorTrial.controllers.views.RegionMenu;
import ml.empee.seniorTrial.controllers.views.RegionsListMenu;
import ml.empee.seniorTrial.model.Permissions;
import ml.empee.seniorTrial.model.PluginItems;
import ml.empee.seniorTrial.model.SeniorRegion;
import ml.empee.seniorTrial.services.RegionService;
import ml.empee.seniorTrial.utils.MCLogger;
import ml.empee.seniorTrial.utils.helpers.cache.PlayerContext;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegionController extends Controller {

  private static final PlayerContext<Location[]> regionCornersContext = PlayerContext.get("regionCornersContext");
  private final RegionService regionService;

  public RegionController() {
    this.regionService = SeniorTrialPlugin.getBean(RegionService.class);
    registerListeners(new RegionDefiner());
  }

  @CommandNode(
      parent = "rg",
      label = "list",
      permission = Permissions.REGION_MENU
  )
  public void openRegionsListMenu(Player sender) {
    RegionsListMenu.open(sender);
  }

  @CommandNode(
      parent = "rg",
      label = "edit",
      permission = Permissions.REGION_MENU,
      description = "Opens the region management menu"
  )
  public void openRegionManagementMenu(Player sender, SeniorRegion region) {
    RegionMenu.open(region, sender);
  }

  @CommandNode(
      parent = "rg",
      label = "wand",
      description = "Gives you the region wand"
  )
  public void giveRegionWand(Player sender) {
    sender.getInventory().addItem(
        PluginItems.WAND.getItem().build()
    );

    MCLogger.info(sender, "You have been given the region wand!");
  }

  @CommandNode(
      parent = "rg",
      label = "create",
      permission = Permissions.REGION_CREATE,
      description = "Create a region"
  )
  public void createRegion(Player sender, String name) {
    if(regionService.findByName(name).isPresent()) {
      MCLogger.error(sender, "A region with that name already exists!");
      return;
    }

    Location[] corners = regionCornersContext.getData(sender).orElse(new Location[2]);
    if (corners[1] == null) {
      MCLogger.error(sender, "You need to select two corners!");
      return;
    }

    regionService.create(new SeniorRegion(name, corners[0], corners[1]));
    regionCornersContext.remove(sender);

    MCLogger.info(sender, "The region &e%s &rhas been created!", name);
  }

  public void redefineRegionCorners(Player sender, SeniorRegion region) {
    Location[] corners = regionCornersContext.getData(sender).orElse(new Location[2]);
    if (corners[1] == null) {
      MCLogger.error(sender, "You need to select two corners!");
      return;
    }

    region.getRegion().setFirstCorner(corners[0]);
    region.getRegion().setSecondCorner(corners[1]);
    regionService.update(region.getId(), region);

    regionCornersContext.remove(sender);

    MCLogger.info(sender, "The corners of &e%s &rhave been changed!", region.getName());
  }

  @CommandNode(
      parent = "rg",
      label = "add",
      permission = Permissions.WHITE_LIST_ADD,
      description = "Add a player to a region's whitelist"
  )
  public void whitelistAdd(CommandSender sender, SeniorRegion region, OfflinePlayer player) {
    if(region.getWhitelist().add(player.getUniqueId())) {
      regionService.update(region.getId(), region);
      MCLogger.info(sender,"The player &e%s &rhas been whitelisted to the region &e%s&r!", player.getName(), region.getName());
    } else {
      MCLogger.error(sender, "The player &e%s &ris already whitelisted to the region &e%s&r!", player.getName(), region.getName());
    }
  }

  @CommandNode(
      parent = "rg",
      label = "remove",
      permission = Permissions.WHITE_LIST_REMOVE,
      description = "Remove a player from a region's whitelist"
  )
  public void whitelistRemove(CommandSender sender, SeniorRegion region, @WhitelistedPlayer
  OfflinePlayer player) {
    region.getWhitelist().remove(player.getUniqueId());
    regionService.update(region.getId(), region);

    MCLogger.info(sender, "The player &e%s &rhas been removed from the whitelist of the region &e%s&r!", player.getName(), region.getName());
  }

  @CommandNode(
      parent = "rg",
      label = "whitelist",
      permission = Permissions.WHITE_LIST,
      description = "List all whitelisted players of a region"
  )
  public void printWhitelist(CommandSender sender, SeniorRegion region) {
    MCLogger.info(
        sender, "Whitelist: &e%s", region.getWhitelist().stream()
        .map(Bukkit::getOfflinePlayer)
        .map(OfflinePlayer::getName)
        .reduce((a, b) -> a + ", " + b)
        .orElse("None")
    );
  }

  public void renameRegion(CommandSender sender, SeniorRegion region, String newName) {
    if(regionService.findByName(newName).isPresent()) {
      MCLogger.error(sender, "A region with that name already exists!");
      return;
    }

    String oldName = region.getName();
    region.setName(newName);
    regionService.update(region.getId(), region);

    MCLogger.info(sender, "The region &e%s &rhas been renamed to &e%s&r!", oldName, newName);
  }

}
