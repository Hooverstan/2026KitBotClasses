// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANFuelSubsystem;
import static frc.robot.Constants.FuelConstants.*;
import frc.robot.subsystems.LimeLightVision;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Launch extends Command {
  /** Creates a new Intake. */

  CANFuelSubsystem fuelSubsystem_;
  LimeLightVision limeLightVision_;
  int aprilTagNum_;

  public Launch(CANFuelSubsystem fuelSystem,
                LimeLightVision limeLightVision,
                int aprilTag) {
    addRequirements(fuelSystem, limeLightVision);
    fuelSubsystem_ = fuelSystem;
    limeLightVision_ = limeLightVision;
    aprilTagNum_ = aprilTag;
  }

  // Called when the command is initially scheduled. Set the rollers to the
  // appropriate values for intaking
  @Override
  public void initialize() {
        // Determine voltage needed given distance
    double distanceToTargetInches = limeLightVision_.visionTargetDistance();
    SmartDashboard.putNumber("Hub Apriltag distance(inches):", distanceToTargetInches );
   
    double targetVoltage = fuelSubsystem_.getVoltageForDistance(distanceToTargetInches);
    fuelSubsystem_
        .setLauncher(
            SmartDashboard.getNumber("Launching launcher value:", targetVoltage));
    fuelSubsystem_.setFeeder(SmartDashboard.getNumber("Launching feeder value:", LAUNCHING_FEEDER_VOLTAGE));

    //fuelSubsystem_.setLauncher(SmartDashboard.getNumber("Launching launcher roller value", LAUNCHING_LAUNCHER_VOLTAGE));
    //fuelSubsystem_.setFeeder(SmartDashboard.getNumber("Launching feeder roller value", LAUNCHING_FEEDER_VOLTAGE));
  }

  // Called every time the scheduler runs while the command is scheduled. This
  // command doesn't require updating any values while running
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted. Stop the rollers
  @Override
  public void end(boolean interrupted) {
    fuelSubsystem_.setLauncher(0.0);
    SmartDashboard.putNumber("Launching launcher value:", 0.0 );
    fuelSubsystem_.setFeeder(0.0);
    SmartDashboard.putNumber("Launching feeder value:", 0.0 );
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
