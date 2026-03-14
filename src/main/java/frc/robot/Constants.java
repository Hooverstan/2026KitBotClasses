// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class DriveConstants {
    // Motor controller IDs for drivetrain motors
    public static final int LEFT_LEADER_ID = 8;
    public static final int LEFT_FOLLOWER_ID = 9;
    public static final int RIGHT_LEADER_ID = 6;
    public static final int RIGHT_FOLLOWER_ID = 7;

    public static final int AUTO_START_LEFT = 1;
    public static final int AUTO_START_RIGHT = 2;
    public static final double AUTO_DRIVE_DURATION = 2.7;
    public static final int AUTO_FORWARD_DRIVE = 1;
    public static final int AUTO_BACKWARDS_DRIVE = 2;

    // Current limit for drivetrain motors. 60A is a reasonable maximum to reduce
    // likelihood of tripping breakers or damaging CIM motors
    public static final int DRIVE_MOTOR_CURRENT_LIMIT = 60;
  }

  public static class LimelightConstants {
    public static final double limelightMountAngleDegrees = 16;
    public static final double limelightCameraHeightInches = 21;
    public static final double targetHeightInches = 44.25; // Hub apriltag height.
    public static final double angular_kP = 0.07;
    public static final double linear_kP = 0.005;
    public static final double linear_base_speed = 0.35;
    public static final double kI = 0.001;
    public static final double kD = 0.3;
    public static final double iZone = 2;  
    public static final int April_Tag_Num = 1;
    public static final int HUB_APRIL_TAG = 5; 
  }

  public static final class FuelConstants {
    // Motor controller IDs for Fuel Mechanism motors
    public static final int FEEDER_MOTOR_ID = 10;
    public static final int INTAKE_MOTOR_ID = 11;
    public static final int LAUNCHER_MOTOR_ID = 12;

    // Current limit and nominal voltage for fuel mechanism motors.
    public static final int FEEDER_MOTOR_CURRENT_LIMIT = 60;
    public static final int INTAKE_LAUNCHER_MOTOR_CURRENT_LIMIT = 60;
    public static final int LAUNCHER_MOTOR_CURRENT_LIMIT = 60;

    // Voltage values for various fuel operations. These values may need to be tuned
    // based on exact robot construction.
    // See the Software Guide for tuning information
    public static final double INTAKE_VOLTAGE = -10;
    public static final double LAUNCHING_FEEDER_VOLTAGE = -12; // feeds into launcher
    public static final double LAUNCHING_LAUNCHER_VOLTAGE = 9; // shoots fuel
    public static final double SPIN_UP_FEEDER_VOLTAGE = -6;
    public static final double SPIN_UP_SECONDS = 0;
    public static final double LAUNCH_DISTANCE_MAX_INCHES = 240.0; // 20 ft
    public static final double LAUNCH_DISTANCE_MIN_INCHES = 36.0;  // 3 ft
    public static final double LAUNCH_ANGLE_MAX_DEG = 10.0;        // Azimuth angle off normal
  }

  public static final class OperatorConstants {
    // Port constants for driver and operator controllers. These should match the
    // values in the Joystick tab of the Driver Station software
    public static final int DRIVER_CONTROLLER_PORT = 0;
    //public static final int OPERATOR_CONTROLLER_PORT = 1;

    // This value is multiplied by the joystick value when rotating the robot to
    // help avoid turning too fast and beign difficult to control
    public static final double DRIVE_SCALING = .7;
    public static final double ROTATION_SCALING = .8;
  }
}
