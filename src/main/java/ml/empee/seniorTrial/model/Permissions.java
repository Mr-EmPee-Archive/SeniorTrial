package ml.empee.seniorTrial.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Permissions {

  public static final String WHITE_LIST_ADD = "region.whitelist";
  public static final String WHITE_LIST_REMOVE = "region.whitelist";
  public static final String WHITE_LIST = "region.whitelist";

  public static final String REGION_CREATE = "region.create";
  public static final String REGION_MENU = "region.menu";
  public static final String PROTECTION_BYPASS = "region.bypass";

}
