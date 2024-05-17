// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.sensors.gyro;

import edu.wpi.first.wpilibj.romi.RomiGyro;

/** Add your docs here. */
public class ROMIGyro implements GenericGyro {
  private RomiGyro gyro;

  public ROMIGyro() {
    gyro = new RomiGyro();
  }

  @Override
  public void reset() {
    gyro.reset();
  }

  @Override
  public double getAngle() {
    return gyro.getAngle();
  }

  @Override
  public double getAngleX() {
    return gyro.getAngleX();
  }

  @Override
  public double getAngleY() {
    return gyro.getAngleY();
  }

  @Override
  public double getAngleZ() {
    return gyro.getAngleZ();
  }

  @Override
  public double getRate() {
    return gyro.getRate();
  }

  @Override
  public void setAngle(double angle) {}
}
