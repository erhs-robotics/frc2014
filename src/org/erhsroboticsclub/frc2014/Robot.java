package org.erhsroboticsclub.frc2014;

import edu.wpi.first.wpilibj.*;
import org.erhsroboticsclub.frc2014.utilities.Messenger;

public class Robot extends SimpleRobot {

    // Subsystems
    Catapult catapult;
    Collector collector;
    
    // Motors and motor control
    RobotDrive drive;

    // Joysticks
    Joystick stick;

    // Sensor inputs
    Gyro gyro;

    // Utility classes
    Messenger msg;

    public void robotInit() {
        // Subsystems
        catapult = new Catapult();
        collector = new Collector();
        
        // Motors and motor control
        drive = new RobotDrive(new Talon(RobotMap.TOP_LEFT_MOTOR),
                new Talon(RobotMap.BOTTOM_LEFT_MOTOR),
                new Talon(RobotMap.TOP_RIGHT_MOTOR),
                new Talon(RobotMap.BOTTOM_RIGHT_MOTOR));

        // Joysticks
        stick = new Joystick(RobotMap.DRIVE_JOYSTICK);

        // Sensor inputs
        gyro = new Gyro(RobotMap.GYRO);

        // Utility classes
        msg = new Messenger();

        killSafety();
    }

    public void autonomous() {

    }

    public void operatorControl() {
        while (isEnabled() && isOperatorControl()) {
            driveWithJoystick();
        }
    }

    public void test() {
        final int SELECT = 0, DRIVE = 1, WINCH = 2, LATCH = 3, COLLECTOR = 4;
        String[] MODE = new String[5];
        MODE[SELECT] = "Select";
        MODE[DRIVE] = "Drive";
        MODE[WINCH] = "Winch";
        MODE[LATCH] = "Latch";
        MODE[COLLECTOR] = "Collector";
        int mode = 0;

        while (isEnabled() && isTest()) {
            msg.printOnLn("DEBUG: " + MODE[mode], msg.LINE[0]);
            switch (mode) {
                // select mode
                case SELECT:
                    for (int i = 1; i < MODE.length; i++) {
                        msg.printOnLn(i + ": " + MODE[i], msg.LINE[i]);
                    }
                    // TEST: Change the mode
                    for (int i = 1; i < MODE.length; i++) {
                        if (stick.getRawButton(i)) {
                            mode = i;
                        }
                    }
                    break;
                case DRIVE:
                    driveWithJoystick();
                    break;
                case WINCH:
                    break;
                case LATCH:
                    break;
                case COLLECTOR:
                    break;
            }
        }
    }

    /*
     **************************************************************************
     * Control Functions. 
     * Break down the various controls of the robot into small routines that 
     * can be called by the operatorControl() function
     **************************************************************************
     */
    public void driveWithJoystick() {
        if (!stick.getRawButton(RobotMap.NO_CHASSIS_ROTATION)) {
            drive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), 0, gyro.getAngle());
        } else {
            drive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), stick.getZ(), gyro.getAngle());
        }
    }

    /*
     **************************************************************************
     * Private Helper Functions.
     **************************************************************************
     */
    private void killSafety() {
        getWatchdog().kill();
        drive.setSafetyEnabled(false);
    }

}
