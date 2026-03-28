// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.CANDriveSubsystem;
import frc.robot.subsystems.CANFuelSubsystem;
import frc.robot.subsystems.LimeLightVision;
import frc.robot.Constants.DriveConstants;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoC extends SequentialCommandGroup {
  /** Creates a new AutoC. */
  public AutoC(CANDriveSubsystem driveSubsystem,
               CANFuelSubsystem fuelSubsystem,
               LimeLightVision limeLightVision,
               int aprilTag) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      // new Drive in reverse for a second.
      new DriveSeconds(driveSubsystem, 2.5, DriveConstants.AUTO_BACKWARDS_DRIVE),
      new LaunchSequence(fuelSubsystem, limeLightVision, aprilTag, driveSubsystem)
    );
  }
}
