package org.erhsroboticsclub.frc2014;

/**
 * A class to contain static fields to easily reference input/output ports and
 * universal robot constants.
 */
public class RobotMap {
    
    // PWM Output
    public static final int TOP_LEFT_MOTOR            = 4;
    public static final int BOTTOM_LEFT_MOTOR         = 3;
    public static final int TOP_RIGHT_MOTOR           = 2;
    public static final int BOTTOM_RIGHT_MOTOR        = 1;    
    public static final int COLLECTOR_COLLECT_MOTOR   = 10;
    public static final int COLLECTOR_ROTATION_MOTOR1 = 8;
    public static final int COLLECTOR_ROTATION_MOTOR2 = 9;
        
    // Digital Input    
    
    // Analog Input
    public static final int GYRO                = 1;    
    public static final int AUTO_MODE_POT       = 2;
    public static final int COLLECTOR_ANGLE_POT = 3;
    
    // USB Input (Joysticks)
    public static final int DRIVE_JOYSTICK  = 1;
    public static final int COLLECTOR_STICK = 2;
    
    // Drive Joystick Buttons
    public static final int SMOOTH_DRIVE       = 1;
    public static final int DRIVE_STRAIGHT     = 2;
    
    
    // Collector Joystick Buttons
    public static final int COLLECTOR_COLLECT  = 1;
    public static final int COLLECTOR_EJECT    = 3;
    public static final int COLLECTOR_DEPLOYED = 4;
    public static final int COLLECTOR_STOWED   = 5;    
}
