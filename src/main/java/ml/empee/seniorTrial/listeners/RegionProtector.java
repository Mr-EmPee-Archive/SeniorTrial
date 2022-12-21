package ml.empee.seniorTrial.listeners;

import lombok.RequiredArgsConstructor;
import ml.empee.seniorTrial.SeniorTrialPlugin;
import ml.empee.seniorTrial.model.Permissions;
import ml.empee.seniorTrial.services.RegionService;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class RegionProtector extends AbstractListener {
  private final RegionService regionService;
  public RegionProtector() {
    this.regionService = SeniorTrialPlugin.getBean(RegionService.class);
  }

  private boolean canInteractAt(Player player, Location location) {
    if(player.hasPermission(Permissions.PROTECTION_BYPASS)) {
      return true;
    }

    return regionService.findRegionsAt(location).stream().allMatch(
        r -> r.isWhitelisted(player.getUniqueId())
    );
  }

  @EventHandler
  public void cancelOnRegionInteract(PlayerInteractEvent event) {
    if (isBlockChangeAction(event)) {
      return;
    }

    if (!canInteractAt(event.getPlayer(), event.getClickedBlock().getLocation())) {
      event.setCancelled(true);
    }
  }

  private boolean isBlockChangeAction(PlayerInteractEvent event) {
    Block clickedBlock = event.getClickedBlock();

    return event.getAction().isLeftClick() || (event.getPlayer().isSneaking() && event.getItem() != null) ||
            clickedBlock == null || !isIntractable(clickedBlock.getType());
  }
  private boolean isIntractable(Material material) {
    if (!material.isInteractable()) {
      return false;
    }

    // This check is done using string to support legacy MC versions too.
    String str = material.toString();
    return !(
        str.endsWith("_STAIRS")
        || str.endsWith("_FENCE")
        || str.equals("REDSTONE_ORE")
        || str.equals("REDSTONE_WIRE")
        || str.equals("PUMPKIN")
        || str.equals("MOVING_PISTON")
        || str.equals("BEEHIVE")
        || str.equals("BEE_NEST")
    );
  }

}
