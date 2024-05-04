// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.robots;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj2.command.Command;
import java.util.ArrayList;
import java.util.List;
import org.frc5010.common.arch.GenericMechanism;
import org.frc5010.common.constants.DrivePorts;
import org.frc5010.common.constants.GenericDrivetrainConstants;
import org.frc5010.common.mechanisms.Drive;
import org.frc5010.common.sensors.Controller;
import org.frc5010.common.sensors.gyro.GenericGyro;
import org.frc5010.common.sensors.gyro.NavXGyro;
import org.frc5010.common.vision.AprilTags;
import org.frc5010.common.vision.VisionLimeLightSim;
import org.frc5010.common.vision.VisionSystem;

/** Add your docs here. */
public class DefaultRobot extends GenericMechanism {
  private GenericDrivetrainConstants driveConstants;
  private GenericMechanism drive;
  private VisionSystem vision;

  public DefaultRobot(Mechanism2d visual, ShuffleboardTab displayTab) {
    super(visual, displayTab);
    driveConstants = new GenericDrivetrainConstants();
    vision = new VisionLimeLightSim("Vision", 1, AprilTags.aprilTagRoomLayout, new Transform3d());
    GenericGyro gyro = new NavXGyro(SPI.Port.kMXP);

    List<DrivePorts> motorPorts = new ArrayList<>();

    drive = new Drive(vision, gyro, Drive.Type.DIFF_DRIVE, motorPorts, driveConstants, "");
  }

  @Override
  public void configureButtonBindings(Controller driver, Controller operator) {}

  @Override
  public void setupDefaultCommands(Controller driver, Controller operator) {
    drive.setupDefaultCommands(driver, operator);
  }

  @Override
  protected void initRealOrSim() {}

  @Override
  public void initAutoCommands() {
    drive.initAutoCommands();
  }

  @Override
  public Command generateAutoCommand(Command autoCommand) {
    return drive.generateAutoCommand(autoCommand);
  }
}
