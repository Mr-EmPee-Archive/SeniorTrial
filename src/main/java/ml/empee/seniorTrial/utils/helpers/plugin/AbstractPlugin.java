package ml.empee.seniorTrial.utils.helpers.plugin;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractPlugin extends JavaPlugin {

  private static final List<Object> beans = new ArrayList<>();

  protected static <T> T addBean(T bean) {
    beans.add(bean);
    return bean;
  }

  public static <T> T getBean(Class<T> clazz) {
    return beans.stream()
        .filter(clazz::isInstance)
        .map(clazz::cast)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Bean " + clazz.getSimpleName() + "not found"));
  }

  @Override
  public void onDisable() {
    for(Object bean : beans) {
      if(bean instanceof AutoCloseable) {
        try {
          ((AutoCloseable) bean).close();
        } catch (Exception e) {
          throw new RuntimeException("Error while closing bean " + bean.getClass().getSimpleName(), e);
        }
      }
    }
  }

}
