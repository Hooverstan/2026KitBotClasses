// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.CANDriveSubsystem;
import frc.robot.subsystems.CANFuelSubsystem;
import frc.robot.subsystems.LimeLightVision;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.limelightConstants;;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ExampleAuto extends SequentialCommandGroup {
  /** Creates a new ExampleAuto. */
  public ExampleAuto(CANDriveSubsystem driveSubsystem, CANFuelSubsystem ballSubsystem, 
                          LimeLightVision vision,
                          int starting_position, // left, middle or right
                          int april_tag_num,
                          double drive_forward_duration) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
    // Drive backwards for .25 seconds. The driveArcadeAuto command factory
    // intentionally creates a command which does not end which allows us to control
    // the timing using the withTimeout decorator
    new AutoDrive(driveSubsystem,0.5,  0.0).withTimeout(1.25), // move from start area
    new FindTarget(driveSubsystem, vision, starting_position, april_tag_num), // look for shooting basket april tag
    // new Launch(ballSubsystem).withTimeout(10), // shoot fuel into basket
    // new AutoDrive(driveSubsystem,0.0,  0.5).withTimeout(1.25), // turn towards ladder
    // new AutoDrive(driveSubsystem,0.5,  0.0).withTimeout(1.25), // move towards ladder
    // new FindTarget(driveSubsystem, vision, starting_position, april_tag_num), // look for climbing april tag
    new GoToTarget(driveSubsystem, 20, vision, april_tag_num)); // move towards climb

    // Spin up the launcher for 1 second and then launch balls for 9 seconds, for a
    // total of 10 seconds
  }
}
