// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANDriveSubsystem;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants.DriveConstants;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class DriveSeconds extends Command {
    private final CANDriveSubsystem driveSubsystem_;
    private double duration_sec_;  // Time to drive forward.
    private double startTime_;
    private int drive_dir_;

  /** Creates a new DriveSeconds. */
  public DriveSeconds(CANDriveSubsystem driveSubsystem, 
                      double duration_sec, 
                      int drive_dir) 
  {
    drive_dir_ = drive_dir;
    driveSubsystem_ = driveSubsystem;
    duration_sec_ = duration_sec;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSubsystem_);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() 
  {
    startTime_ = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {
    // Drive at half speed until timer expires.
    if(drive_dir_ == DriveConstants.AUTO_FORWARD_DRIVE){
      driveSubsystem_.driveArcade(0.5, 0.0);
    }
    else{
      driveSubsystem_.driveArcade(-0.5, 0.0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) 
  {
    driveSubsystem_.driveArcade(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double now = Timer.getFPGATimestamp();

    if ( now - startTime_ > duration_sec_ )
    {
      System.out.println("DriveSeconds, isFinished TRUE");
      return true;
    }
    else
    {
      return false;
    }
  }
}
