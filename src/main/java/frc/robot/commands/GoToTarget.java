// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.limelightConstants;
import frc.robot.subsystems.CANDriveSubsystem;
import frc.robot.subsystems.LimeLightVision;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class GoToTarget extends Command {
  private CANDriveSubsystem drivetrain_;
  private double angular_kP_ = limelightConstants.angular_kP; // Largest angle=31, 1/31=0.032
  private double linear_kP_ = limelightConstants.linear_kP;
  private double linear_base_speed_ = limelightConstants.linear_base_speed;
  private double currentDistanceToTargetInches_; 
  private double setpoint_; 
  private LimeLightVision vision_;
  private double errorSum = 0;
  private double lastError = 0;
  private double lastTimestamp = 0;
  private  int april_tag_num_;

  /** Creates a new GoToTarget. */
  public GoToTarget(CANDriveSubsystem drivetrain, 
                     double setpoint,
                     LimeLightVision vision,
                     int april_tag_num) 
  {
    drivetrain_ = drivetrain;
    setpoint_ = setpoint;
    vision_ = vision;
    april_tag_num_ = april_tag_num;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(vision_, drivetrain_);  
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() 
  {
    System.out.println("Running GoToTarget Command");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {
     if ( vision_.isValidVisionTarget())
    {
      // 1. Angle to target.
      double angleToTargetDegrees = vision_.getTX();
      SmartDashboard.putNumber("Apriltag angle(degrees):", angleToTargetDegrees );

      double angularVelToTarget = angleToTargetDegrees * angular_kP_ ; // * kMaxAngularSpeed;
      angularVelToTarget *= 0.5; // CCW is positive, limelight w/target to left is negative.
                                 // Note: We oriented the camera upsidedown on the robot meaning
                                 // angle to target from camera is inversed.  Therefore multiply
                                 // by positive value.
      SmartDashboard.putNumber("angularVelToTarget(angular speed):", angularVelToTarget );

      // 2. Distance to target
      currentDistanceToTargetInches_ = vision_.visionTargetDistance();
      // SmartDashboard.putNumber("Apriltag distance(inches):", currentDistanceToTargetInches_ );

      // double error = currentDistanceToTargetInches_ - setpoint_;
      //double dt = Timer.getFPGATimestamp() - lastTimestamp;
      // SmartDashboard.putNumber("Apriltag distance error(inches):", error );
      
      //if (Math.abs(error) > limelightConstants.iZone) {
      //  errorSum += error * dt;
      //}
      
    //double errorRate = (error - lastError) / dt;
                                                                                        // removing the derivative part of PID
      // double linearVelToTarget =  error * linear_kP_ + linear_base_speed_; /* limelightConstants.kI * errorSum + limelightConstants.kD * errorRate*/
      // drivetrain_.driveArcade(linearVelToTarget, angularVelToTarget);
      // lastTimestamp = Timer.getFPGATimestamp();
      // lastError = error;

      if (currentDistanceToTargetInches_ < setpoint_) {
       drivetrain_.driveArcade(0.45,angularVelToTarget);
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) 
  {
    System.out.println("Ending GoToTarget Command");
    drivetrain_.driveArcade(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() 
  {
    // Need to determine how close we need to get to the Apriltag.
    if (currentDistanceToTargetInches_ <= setpoint_) 
      return true;
    else
      return false;
  }
}
