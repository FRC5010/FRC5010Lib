// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.sensors.camera;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform3d;
import java.util.Optional;
import java.util.function.Supplier;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;

/** A simulated camera using the PhotonVision library. */
public class SimulatedVisualTargetCamera extends SimulatedCamera {

  /**
   * Constructor
   *
   * @param name - the name of the camera
   * @param colIndex - the column index for the dashboard
   * @param fieldLayout - the field layout
   * @param strategy - the pose strategy
   * @param cameraToRobot - the camera-to-robot transform
   * @param poseSupplier - the pose supplier
   */
  public SimulatedVisualTargetCamera(
      String name,
      int colIndex,
      AprilTagFieldLayout fieldLayout,
      PoseStrategy strategy,
      Transform3d cameraToRobot,
      Supplier<Pose2d> poseSupplier) {
    super(name, colIndex, fieldLayout, strategy, cameraToRobot, poseSupplier);
  }

  /** Update the simulated camera */
  @Override
  public void update() {
    super.update();
    if (camResult.hasTargets()) {
      target = Optional.ofNullable(camResult.getBestTarget());
    }
  }
}
