package ml.empee.seniorTrial.controllers.listeners;

import java.util.Objects;
import ml.empee.seniorTrial.model.Permissions;
import ml.empee.seniorTrial.model.PluginItems;
import ml.empee.seniorTrial.utils.MCLogger;
import ml.empee.seniorTrial.utils.helpers.cache.PlayerContext;
import ml.empee.seniorTrial.utils.helpers.cache.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class RegionDefiner implements Listener {

  private final PlayerContext<Location[]> regionCornersContext = PlayerContext.get("regionCornersContext");

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (event.getClickedBlock() == null) {
      return;
    }

    if(event.getHand() == EquipmentSlot.OFF_HAND) {
      return;
    }

    if (event.getItem() == null) {
      return;
    }

    if (!PluginItems.WAND.getItem().isPluginItem(event.getItem())) {
      return;
    }

    event.setCancelled(true);
    Player player = event.getPlayer();
    if(!player.hasPermission(Permissions.REGION_CREATE) && !player.hasPermission(Permissions.REGION_MENU)) {
      MCLogger.error(player, "You don't have permission to define regions!");
      return;
    }

    if (!event.getAction().isLeftClick()) {
      onValidClick(player, event.getClickedBlock().getLocation());
    }
  }

  public void onValidClick(Player player, Location location) {
    Location[] locations = regionCornersContext.getOrPut(PlayerData.of(player, new Location[2])).getData();

    if (locations[0] != null && locations[1] != null) {
      locations[0] = null;
      locations[1] = null;
    }

    if (locations[0] == null) {
      locations[0] = location;
      MCLogger.info(player, "First corner set to &eX%s Y%s Z%s", location.getBlockX(), location.getBlockY(), location.getBlockZ());
    } else {
      if (!Objects.equals(locations[0].getWorld(), location.getWorld())) {
        MCLogger.error(player, "The two corners must be in the same dimension!");
        return;
      }

      locations[1] = location;
      MCLogger.info(player, "Second corner set to &eX%s Y%s Z%s", location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
  }

}
