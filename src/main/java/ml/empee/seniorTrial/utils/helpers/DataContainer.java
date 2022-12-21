package ml.empee.seniorTrial.utils.helpers;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public class DataContainer {

  private final String name;
  private final PersistentDataContainer container;

  public DataContainer(String name, PersistentDataHolder holder) {
    this.name = name;
    this.container = holder.getPersistentDataContainer();
  }

  public void setInt(String key, int value) {
    container.set(getKey(key), PersistentDataType.INTEGER, value);
  }

  public int getInt(String key, int defaultValue) {
    Integer integer = container.get(getKey(key), PersistentDataType.INTEGER);
    return integer != null ? integer : defaultValue;
  }

  public void setString(String key, String value) {
    container.set(getKey(key), PersistentDataType.STRING, value);
  }

  public String getString(String key) {
    return container.get(getKey(key), PersistentDataType.STRING);
  }

  private NamespacedKey getKey(String key) {
    return NamespacedKey.fromString(name + ":" + key);
  }

}
