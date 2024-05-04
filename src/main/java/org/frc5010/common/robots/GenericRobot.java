package org.frc5010.common.robots;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import java.util.Optional;
import org.frc5010.common.arch.GenericMechanism;
import org.frc5010.common.arch.PersistedEnums;
import org.frc5010.common.arch.WpiNetworkTableValuesHelper;
import org.frc5010.common.constants.RobotConstantsDef;
import org.frc5010.common.sensors.Controller;
import org.frc5010.common.subsystems.Color;
import org.frc5010.common.telemetery.WpiDataLogging;

public abstract class GenericRobot extends GenericMechanism {
  private SendableChooser<Command> command;
  private Controller driver;
  private Controller operator;
  private static Alliance alliance;
  private GenericMechanism robot;
  private static String MAC_Address = "MAC ADDRESS";
  private DigitalInput startupBypass;

  public enum LogLevel {
    DEBUG,
    COMPETITION
  }

  public static LogLevel logLevel = LogLevel.DEBUG;

  public GenericRobot(String tabName) {
    super(tabName);
    values.declare(MAC_Address, "");

    // Setup controllers
    driver = new Controller(Controller.JoystickPorts.ZERO.ordinal());
    operator = new Controller(Controller.JoystickPorts.ONE.ordinal());
    if (!operator.isPluggedIn()) {
      operator = driver;
      driver.setSingleControllerMode(true);
    }
    // Put Mechanism 2d to SmartDashboard
    mechVisual =
        new Mechanism2d(
            PersistedEnums.ROBOT_VISUAL_H.getInteger(),
            RobotConstantsDef.robotVisualV.getInteger());
    SmartDashboard.putData("Robot Visual", mechVisual);

    DriverStation.silenceJoystickConnectionWarning(true);
    alliance = determineAllianceColor();
    SmartDashboard.putString("Alliance", alliance.toString());

    initRealOrSim();

    // Configure the button bindings
    configureButtonBindings(driver, operator);
    initAutoCommands();
    WpiNetworkTableValuesHelper.loadRegisteredToNetworkTables();
  }

  public static LogLevel getLoggingLevel() {
    return logLevel;
  }

  public static void setLoggingLevel(LogLevel level) {
    logLevel = level;
  }

  @Override
  protected void initRealOrSim() {
    if (RobotBase.isReal()) {
      WpiDataLogging.start(true);
    } else {
      WpiDataLogging.start(false);
      // NetworkTableInstance instance = NetworkTableInstance.getDefault();
      // instance.stopServer();
      // set the NT server if simulating this code.
      // "localhost" for photon on desktop, or "photonvision.local" / "[ip-address]"
      // for coprocessor
      // instance.setServer("localhost");
      // instance.startClient4("myRobot");
    }
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  public void configureButtonBindings(Controller driver, Controller operator) {
    robot.configureButtonBindings(driver, operator);
    if (driver.isSingleControllerMode()) {
      // TODO: Add code to handle single driver mode
    } else {
      if (RobotBase.isReal()) {}
    }
  }

  @Override
  public void setupDefaultCommands(Controller driver, Controller operator) {
    if (DriverStation.isTeleop() || DriverStation.isAutonomous()) {
      robot.setupDefaultCommands(driver, operator);
    } else if (DriverStation.isTest()) {
      robot.setupTestDefaultCommmands(driver, operator);
    }
  }

  @Override
  public void initAutoCommands() {
    robot.initAutoCommands();

    // TODO: Figure out Pathplanner Warmup Command

    command = AutoBuilder.buildAutoChooser();
    if (null != command) {
      shuffleTab.add("Auto Modes", command).withSize(2, 1);
    }
  }

  @Override
  public Command generateAutoCommand(Command autoCommand) {
    return robot.generateAutoCommand(autoCommand);
  }

  public Alliance determineAllianceColor() {
    Optional<Alliance> color = DriverStation.getAlliance();
    return color.orElse(Alliance.Blue);
  }

  public static Color chooseAllianceColor() {
    Optional<Alliance> alllianceColor = DriverStation.getAlliance();
    if (alllianceColor.isPresent()) {
      return alllianceColor.get() == Alliance.Red ? Color.RED : Color.BLUE;
    }
    return Color.ORANGE;
  }

  public static Alliance getAlliance() {
    return alliance;
  }

  public void disabledBehavior() {
    robot.disabledBehavior();
  }
}
