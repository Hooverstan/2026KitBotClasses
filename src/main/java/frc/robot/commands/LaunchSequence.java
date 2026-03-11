// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.FuelConstants;
import frc.robot.subsystems.CANFuelSubsystem;
import frc.robot.Constants.limelightConstants;
import frc.robot.subsystems.LimeLightVision;
import frc.robot.Constants;


// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class LaunchSequence extends SequentialCommandGroup {
  /** Creates a new LaunchSequence. */
    private final LimeLightVision limelight = new LimeLightVision();
    private double currentDistanceToTargetInches; 
    private double angleToTargetDegrees;
    private double velocity;
  public LaunchSequence(CANFuelSubsystem fuelSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
          angleToTargetDegrees = limelight.getTX();
          currentDistanceToTargetInches = limelight.visionTargetDistance();
          double ShooterVoltage = 10 * ((Math.log(1 + (velocity+2)/16) - Math.log(1 - (velocity+2)/16)) / 2);
    addCommands(
        //new SpinUp(fuelSubsystem).withTimeout(FuelConstants.SPIN_UP_SECONDS));
        new Launch(fuelSubsystem));
  }
}
