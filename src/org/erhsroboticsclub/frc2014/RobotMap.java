package org.erhsroboticsclub.frc2014;

/**
 * A class to contain static fields to easily reference input/output ports and
 * universal robot constants.
 */
public class RobotMap {
    
    // PWM Output
    public static final int TOP_LEFT_MOTOR     = 1;
    public static final int BOTTOM_LEFT_MOTOR  = 2;
    public static final int TOP_RIGHT_MOTOR    = 3;
    public static final int BOTTOM_RIGHT_MOTOR = 4;
    public static final int WINCH_MOTOR        = 5; // TODO
    public static final int LATCH_MOTOR_1      = 6; // TODO
    public static final int LATCH_MOTOR_2      = 7; // TODO
        
    // Digital Input
    
    // Analog Input
    public static final int GYRO      = 2;
    public static final int WINCH_POT = 3; // TODO
    public static final int LATCH_POT = 1; // TODO
    
    // USB Input (Joysticks)
    public static final int DRIVE_JOYSTICK = 1;
    
    // Drive Joystick Buttons
    public static final int NO_CHASSIS_ROTATION = 1;
    public static final int TEST_SET_LACHED     = 5;
    public static final int TEST_SET_UNLACHED   = 6;
    public static final int TEST_WIND_WINCH     = 3;
    public static final int TEST_UNWIND_WINCH   = 4;
    public static final int TEST_PRIME          = 2;
    public static final int TEST_FIRE           = 1;
    public static final int DRIVE_STRAIGHT      = 9;
    
}
