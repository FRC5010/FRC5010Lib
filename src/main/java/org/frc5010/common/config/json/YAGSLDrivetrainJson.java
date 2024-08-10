// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.config.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.frc5010.common.arch.GenericRobot;
import org.frc5010.common.constants.MotorFeedFwdConstants;
import org.frc5010.common.constants.SwerveConstants;
import org.frc5010.common.drive.swerve.YAGSLSwerveDrivetrain;
import org.frc5010.common.subsystems.AprilTagPoseSystem;

/** Parameters for a YAGSLSwerveDrivetrain */
public class YAGSLDrivetrainJson implements DrivetrainPropertiesJson {
  /** The directory to read from */
  public String directory = "";

  /** The gear ratio of the turning motor */
  public double turningMotorGearRatio = 1.0;

  /**
   * The file names of the drive module's feed forward constants. These should match what are used
   * in the YAGSL config
   */
  public String[] driveModules;

  public void readDrivetrainConfiguration(GenericRobot robot, File baseDirectory)
      throws IOException {
    SwerveConstants swerveConstants = new SwerveConstants(robot.getDrivetrainConstants());
    for (int i = 0; i < driveModules.length; i++) {
      File moduleFile = new File(baseDirectory, "drive_modules/" + driveModules[i]);
      String moduleName = driveModules[i].substring(0, driveModules[i].indexOf(".json"));
      assert moduleFile.exists();
      YAGSLDriveModuleJson module =
          new ObjectMapper().readValue(moduleFile, YAGSLDriveModuleJson.class);
      MotorFeedFwdConstants feedFwdConstants =
          new MotorFeedFwdConstants(module.s, module.v, module.a);
      swerveConstants.getSwerveModuleConstants().addDriveMotorFF(moduleName, feedFwdConstants);
    }
    robot.setDrivetrainConstants(swerveConstants);
    return;
  }
  ;

  @Override
  public void createDriveTrain(GenericRobot robot) {
    AprilTagPoseSystem atSystem =
        (AprilTagPoseSystem) robot.getSubsystem(CameraConfigurationJson.APRIL_TAG);
    robot.addSubsystem(
        DrivetrainPropertiesJson.DRIVE_TRAIN,
        new YAGSLSwerveDrivetrain(
            robot.getMechVisual(),
            robot.getDrivetrainConstants(),
            turningMotorGearRatio,
            directory,
            atSystem));
  }
}
