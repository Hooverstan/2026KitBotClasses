// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.subsystems.LimeLightVision;
import frc.robot.Constants.FuelConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class VerifyLaunchPose extends Command {
  LimeLightVision limelight_;
  int aprilTagNum_;
  private double distanceToTargetInches_;

  /** Creates a new VerifyLaunchPose. */
  public VerifyLaunchPose( LimeLightVision limelight,
                           int aprilTagNum ) {
    limelight_ = limelight;
    //aprilTagNum_ = aprilTagNum_;

    addRequirements(limelight_);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() 
  {
     // Get distance to april tag. Assure within 10degrees in azimuth
     if (limelight_.isValidVisionTarget() && 
            Math.abs(limelight_.getTX()) < FuelConstants.LAUNCH_ANGLE_MAX_DEG)
     {
        distanceToTargetInches_ = limelight_.visionTargetDistance();
     }
     else
     {
        distanceToTargetInches_ = 0;
     }
     SmartDashboard.putNumber("Apriltag distance(inches):", distanceToTargetInches_ );
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) 
  {
    SmartDashboard.putNumber("Apriltag distance(inches):", distanceToTargetInches_ );
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if ((distanceToTargetInches_ < FuelConstants.LAUNCH_DISTANCE_MIN_INCHES) ||
        (distanceToTargetInches_ > FuelConstants.LAUNCH_DISTANCE_MAX_INCHES))
    distanceToTargetInches_ = 0;

    return true;
  }
}
