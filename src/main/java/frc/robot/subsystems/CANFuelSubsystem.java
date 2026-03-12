// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.FuelConstants.*;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;

public class CANFuelSubsystem extends SubsystemBase {
  private final SparkMax IntakeRoller;
  private final SparkMax LauncherFeederRoller;
  private final SparkMax LauncherRoller;
  private final InterpolatingDoubleTreeMap speedTable;

  /** Creates a new CANBallSubsystem. */
  public CANFuelSubsystem() {
    // create brushed motors for each of the motors on the launcher mechanism
    LauncherFeederRoller = new SparkMax(FEEDER_MOTOR_ID, MotorType.kBrushed);
    IntakeRoller = new SparkMax(INTAKE_MOTOR_ID, MotorType.kBrushless);
    LauncherRoller = new SparkMax(LAUNCHER_MOTOR_ID, MotorType.kBrushless);
    
    speedTable = new InterpolatingDoubleTreeMap();
    speedTable.put(1.5, 6.0); // Distance(m), Voltage(v)
    speedTable.put(3.0, 8.5);
    speedTable.put(5.0, 12.0);
    // create the configuration for the feeder roller, set a current limit and apply
    // the config to the controller
    SparkMaxConfig feederConfig = new SparkMaxConfig();
    feederConfig.smartCurrentLimit(FEEDER_MOTOR_CURRENT_LIMIT);
    //feederRoller.configure(feederConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // create the configuration for the launcher roller, set a current limit, set
    // the motor to inverted so that positive values are used for both intaking and
    // launching, and apply the config to the controller
    SparkMaxConfig launcherConfig = new SparkMaxConfig();
    launcherConfig.inverted(true);
    launcherConfig.smartCurrentLimit(LAUNCHER_MOTOR_CURRENT_LIMIT);
    //intakeLauncherRoller.configure(launcherConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // put default values for various fuel operations onto the dashboard
    // all commands using this subsystem pull values from the dashbaord to allow
    // you to tune the values easily, and then replace the values in Constants.java
    // with your new values. For more information, see the Software Guide.
    SmartDashboard.putNumber("Intaking intake roller value", INTAKE_VOLTAGE);
    SmartDashboard.putNumber("Launching feeder roller value", LAUNCHING_FEEDER_VOLTAGE);
    SmartDashboard.putNumber("Launching launcher roller value", LAUNCHING_LAUNCHER_VOLTAGE);
    SmartDashboard.putNumber("Spin-up feeder roller value", SPIN_UP_FEEDER_VOLTAGE);
  }

  // A method to set the voltage of the intake roller
  public void setFeeder(double voltage) {
    LauncherFeederRoller.setVoltage(voltage);
  }

  // A method to set the voltage of the intake roller
  public void setIntake(double voltage) {
    IntakeRoller.setVoltage(voltage);
  }

  public void setLauncher(double voltage) {
    LauncherRoller.setVoltage(voltage);
  }

  public double getVoltageForDistance(double distance) {
    // Returns interpolated voltage for any distance
    return speedTable.get(distance);
  }

  // A method to stop the rollers
  public void stop() {
    IntakeRoller.set(0);
    LauncherFeederRoller.set(0);
    LauncherRoller.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
