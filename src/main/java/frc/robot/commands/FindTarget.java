// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.CANDriveSubsystem;
import frc.robot.subsystems.LimeLightVision;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class FindTarget extends Command {
  private CANDriveSubsystem drivetrain_;
  private LimeLightVision vision_;
  private Boolean vision_target_found;
  private int april_tag_num_;
  private int auto_starting_position_;
  /** Creates a new FindTarget. */
  public FindTarget( CANDriveSubsystem drivetrain, 
                     LimeLightVision vision, 
                     int auto_starting_position, // 1 is left, 2 is middle, 3 is right.
                     int april_tag_num) 
  {
    drivetrain_ = drivetrain;
    vision_ = vision;
    april_tag_num_ = april_tag_num;
    auto_starting_position_ = auto_starting_position;
    addRequirements(vision_, drivetrain_);    
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() 
  {
    System.out.println("Running FindTarget Command");
    vision_target_found = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {
        if ( vision_.isValidVisionTarget() && Math.abs(vision_.getTX()) < 10.0)
      vision_target_found = true;
    else
    {
      // if you dont see an april tag turn clockwise or counter clock wise based on the constant auto_starting_position_.
      vision_target_found = false;
      if ( auto_starting_position_ == DriveConstants.AUTO_START_LEFT )
        drivetrain_.driveArcade(0, -0.51
        ); // rotate cw
      else
        drivetrain_.driveArcade(0, 0.51); // rotate ccw, applicable for center or right start
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) 
  {
    System.out.println("Ending FindTarget Command");
    drivetrain_.driveArcade(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if ( vision_target_found == true )
      return true;
    else
      return false;
    }
}
