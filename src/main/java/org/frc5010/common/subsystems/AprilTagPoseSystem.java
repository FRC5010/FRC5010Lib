// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.frc5010.common.subsystems;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.frc5010.common.sensors.camera.GenericCamera;

/**
 * AprilTagPoseSystem
 *
 * @author Team 5010 Subsystem for managing multiple AprilTag cameras
 */
public class AprilTagPoseSystem extends CameraSystem {
  /** Map of camera name to robot pose */
  protected Map<String, Optional<Pose3d>> robotPose3ds = new HashMap<>();
  /** Map of camera name to tracked target pose */
  protected Map<String, Optional<Pose3d>> targetPose3ds = new HashMap<>();
  /** Map of camera name to latency */
  protected Map<String, Double> latencies = new HashMap<>();
  /** A list of cameras */
  protected List<GenericCamera> cameras = new ArrayList<>();
  /** The field layout */
  protected AprilTagFieldLayout fieldLayout;

  /**
   * Constructor
   *
   * @param fieldLayout - the field layout to use
   */
  public AprilTagPoseSystem(AprilTagFieldLayout fieldLayout) {
    super(null);
    this.fieldLayout = fieldLayout;
  }

  /**
   * Constructor
   *
   * @param camera - the camera to add
   * @param fieldLayout - the field layout to use
   */
  public AprilTagPoseSystem(GenericCamera camera, AprilTagFieldLayout fieldLayout) {
    super(camera);
    this.fieldLayout = fieldLayout;
    addCamera(camera);
  }

  /**
   * Add a camera
   *
   * @param camera - the camera to add
   */
  public void addCamera(GenericCamera camera) {
    if (null == this.camera) {
      this.camera = camera;
    }
    cameras.add(camera);
    camera.registerUpdater(() -> robotPose3ds.put(camera.name(), camera.getRobotPose()));
    camera.registerUpdater(() -> targetPose3ds.put(camera.name(), camera.getRobotToTargetPose()));
    camera.registerUpdater(() -> latencies.put(camera.name(), camera.getLatency()));
  }

  /**
   * Get the pose of the robot from a camera
   *
   * @param name - the name of the camera
   * @return the pose of the robot as an optional type
   */
  public Optional<Pose3d> getRobotPose3d(String name) {
    return Optional.ofNullable(robotPose3ds.get(name)).orElse(Optional.empty());
  }

  /**
   * Get the latency from a camera
   *
   * @param name - the name of the camera
   * @return the latency as a double
   */
  public double getLatency(String name) {
    return latencies.get(name);
  }

  /**
   * Get the list of camera names
   *
   * @return the list of camera names
   */
  public List<String> getCameraNames() {
    return cameras.stream().map(c -> c.name()).toList();
  }

  /** Update the camera info */
  @Override
  public void updateCameraInfo() {
    for (GenericCamera camera : cameras) {
      camera.update();
    }
  }

  /**
   * Get the field layout
   *
   * @return the field layout
   */
  public AprilTagFieldLayout getFieldLayout() {
    return fieldLayout;
  }

  /**
   * Get the distance to the target
   *
   * @param camera - the name of the camera
   * @return the distance to the target
   */
  public double getDistanceToTarget(String camera) {
    return Optional.ofNullable(targetPose3ds.get(camera))
        .map(it -> it.get().getTranslation().getNorm())
        .orElse(Double.MAX_VALUE);
  }

  /**
   * Get the distance to the closest target
   *
   * @return the distance to the target
   */
  @Override
  public double getDistanceToTarget() {
    double shortestDistance = Double.MAX_VALUE;
    for (GenericCamera camera : cameras) {
      double distance = getDistanceToTarget(camera.name());
      if (distance < shortestDistance || shortestDistance == 0.0) {
        shortestDistance = distance;
      }
    }
    return shortestDistance;
  }

  /**
   * Gets a calibrated vector based on the distance
   *
   * @param distance - the distance
   * @return the calibrated vector
   */
  public Vector<N3> getStdVector(double distance) {
    double calib = distance * 0.15;
    return VecBuilder.fill(calib, calib, Units.degreesToRadians(5 * distance));
  }

  /**
   * Determines if any cameras have a valida target
   *
   * @return true if any cameras have a valid target
   */
  @Override
  public boolean hasValidTarget() {
    for (GenericCamera camera : cameras) {
      if (camera.hasValidTarget()) {
        return true;
      }
    }
    return false;
  }
}
