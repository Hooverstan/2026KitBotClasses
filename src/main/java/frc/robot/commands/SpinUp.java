// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.CANDriveSubsystem;
import frc.robot.subsystems.CANFuelSubsystem;
import static frc.robot.Constants.FuelConstants.*;
import frc.robot.subsystems.LimeLightVision;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class SpinUp extends Command {
  CANFuelSubsystem fuelSubsystem_;
  LimeLightVision limeLightVision_;
  int aprilTag_;
  CANDriveSubsystem driveSubsystem_;
  private boolean vision_target_found = false;

  public SpinUp(CANFuelSubsystem fuelSystem,
                LimeLightVision limeLightVision,
                int aprilTag,
                CANDriveSubsystem driveSubsystem) 
  {
    addRequirements(fuelSystem, limeLightVision);
    fuelSubsystem_ = fuelSystem;
    limeLightVision_ = limeLightVision;
    aprilTag_ = aprilTag;
    driveSubsystem_ = driveSubsystem;

  }

  // Called when the command is initially scheduled. Set the rollers to the
  // appropriate values for intaking
  @Override
  public void initialize() {
    vision_target_found = false;

    // Spinning the feeder in reverse while launching spins up.
    //fuelSubsystem_.setFeeder(SmartDashboard.getNumber("Launching spin-up feeder value", SPIN_UP_FEEDER_VOLTAGE));

    //fuelSubsystem_
    //    .setLauncher(
    //        SmartDashboard.getNumber("Launching launcher roller value", LAUNCHING_LAUNCHER_VOLTAGE));
    //fuelSubsystem_.setFeeder(SmartDashboard.getNumber("Launching spin-up feeder value", SPIN_UP_FEEDER_VOLTAGE));
  }

  // Called every time the scheduler runs while the command is scheduled. This
  // command doesn't require updating any values while running
  @Override
  public void execute() {
    if (limeLightVision_.isValidVisionTarget()){
      if(limeLightVision_.getTX() < 10.0 && limeLightVision_.getTX() > -10.0)
      {
        driveSubsystem_.driveArcade(0, 0);
        double distanceToTargetInches = limeLightVision_.visionTargetDistance();
        SmartDashboard.putNumber("Hub Apriltag distance(inches):", distanceToTargetInches );
        double targetVoltage = fuelSubsystem_.getVoltageForDistance(distanceToTargetInches);
        fuelSubsystem_
          .setLauncher(
            SmartDashboard.getNumber("Launching launcher roller value", targetVoltage));
      }
      else
      {
        // if you dont see an april tag turn clockwise or counter clock wise based on the constant auto_starting_position_.
        if (Math.abs(limeLightVision_.getTX()) > 10.0)
          driveSubsystem_.driveArcade(0,-0.51); // rotate cw
        else
          driveSubsystem_.driveArcade(0, 0.51); // rotate ccw, applicable for center or right start
      }
  }
}

  // Called once the command ends or is interrupted. Stop the rollers
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
