package org.erhsroboticsclub.frc2014;

/**
 * A class to contain static fields to easily reference input/output ports and
 * universal robot constants.
 */
public class RobotMap {
    
    // PWM Output
    public static final int TOP_LEFT_MOTOR            = 1;
    public static final int BOTTOM_LEFT_MOTOR         = 2;
    public static final int TOP_RIGHT_MOTOR           = 3;
    public static final int BOTTOM_RIGHT_MOTOR        = 4;
    public static final int WINCH_MOTOR               = 5;
    public static final int LATCH_MOTOR_1             = 6;
    public static final int LATCH_MOTOR_2             = 7;
    public static final int COLLECTOR_COLLECT_MOTOR   = 8;
    public static final int COLLECTOR_ROTATION_MOTOR1 = 9;
    public static final int COLLECTOR_ROTATION_MOTOR2 = 10;
        
    // Digital Input    
    
    // Analog Input
    public static final int GYRO          = 1;
    public static final int WINCH_POT     = 3;
    public static final int LATCH_POT     = 7;
    public static final int AUTO_MODE_POT = 2;
    public static final int COLLECTOR_ANGLE_POT = 5;

    
    // USB Input (Joysticks)
    public static final int DRIVE_JOYSTICK  = 1;
    public static final int COLLECTOR_STICK = 2;
    
    // Drive Joystick Buttons
    public static final int ALLOW_CHASSIS_ROTATION = 1;
    public static final int DRIVE_STRAIGHT      = 2;
    
    // Collector Joystick Buttons
    public static final int COLLECTOR_COLLECT = 1;
    public static final int COLLECTOR_EJECT   = 3;
    
    // Latch Test
    public static final int TEST_SET_LATCHED   = 5;
    public static final int TEST_SET_UNLATCHED = 6;
    // Winch Test
    public static final int TEST_WIND_WINCH    = 3;
    public static final int TEST_UNWIND_WINCH  = 4;
    // Fire Test
    public static final int TEST_PRIME         = 2;
    public static final int TEST_FIRE          = 1;
    // Collecter Test
    public static final int TEST_COLLECT       = 5;
    public static final int TEST_EJECT         = 6;
    
}
