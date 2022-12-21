package ml.empee.seniorTrial.listeners;

import ml.empee.seniorTrial.utils.helpers.plugin.StoppableBean;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractListener implements Listener, StoppableBean {

  protected final JavaPlugin plugin = JavaPlugin.getProvidingPlugin(AbstractListener.class);

  public final void stop() {
    HandlerList.unregisterAll(this);
    onUnregister();
  }

  protected void onUnregister() {}

}
