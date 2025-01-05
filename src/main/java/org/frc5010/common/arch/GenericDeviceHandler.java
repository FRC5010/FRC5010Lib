package org.frc5010.common.arch;

import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import org.frc5010.common.telemetry.DisplayValuesHelper;

public interface GenericDeviceHandler {
  public void addDevice(String name, Object device);

  public Object getDevice(String name);

  public Mechanism2d getMechVisual();

  public DisplayValuesHelper getDisplayValuesHelper();
}
