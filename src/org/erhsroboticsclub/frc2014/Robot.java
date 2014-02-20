package org.erhsroboticsclub.frc2014;

import edu.wpi.first.wpilibj.*;
import org.erhsroboticsclub.frc2014.utilities.JoystickX;
import org.erhsroboticsclub.frc2014.utilities.Messenger;

public class Robot extends SimpleRobot {
    
    // Subsystems
    Catapult catapult;
    Collector collector;
    RobotDrive drive;

    // Joysticks
    JoystickX stick;

    // Sensor inputs
    Gyro gyro;

    // Utility classes
    Messenger msg;
    
    // Constants
    private static final long UPDATE_FREQ = 20;
    private static final long AUTO_DRIVE_TIME = 100;

    public void robotInit() {
        // Subsystems
        catapult = new Catapult();
        collector = new Collector();
        drive = new RobotDrive(new Talon(RobotMap.TOP_LEFT_MOTOR),
                new Talon(RobotMap.BOTTOM_LEFT_MOTOR),
                new Talon(RobotMap.TOP_RIGHT_MOTOR),
                new Talon(RobotMap.BOTTOM_RIGHT_MOTOR));
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

        // Joysticks
        stick = new JoystickX(RobotMap.DRIVE_JOYSTICK);

        // Sensor inputs
        gyro = new Gyro(RobotMap.GYRO);

        // Utility classes
        msg = new Messenger();

        killSafety();
    }

    public void autonomous() {
        gyro.reset();
        catapult.prime();
        
        long startTime = System.currentTimeMillis();        
        while(System.currentTimeMillis() - startTime < AUTO_DRIVE_TIME) {
            drive.mecanumDrive_Cartesian(0, -1, 0, gyro.getAngle());
        }
        
        catapult.fire();
    }

    public void operatorControl() {
        //gyro.reset();
        
        while (isEnabled() && isOperatorControl()) {
            long startTime = System.currentTimeMillis();
            driveWithJoystick();
            while(System.currentTimeMillis() - startTime < UPDATE_FREQ);
        }
    }

    public void test() {
        final int SELECT = 0, DRIVE = 1, WINCH = 2, LATCH = 3, COLLECTOR = 4, 
                  CATAPULT = 5;
        String[] MODE   = new String[6];
        MODE[SELECT]    = "Select";
        MODE[DRIVE]     = "Drive";
        MODE[WINCH]     = "Winch";
        MODE[LATCH]     = "Latch";
        MODE[COLLECTOR] = "Collector";
        MODE[CATAPULT]  = "Catapult";
        int mode = 0;

        while (isEnabled() && isTest()) {
            long startTime = System.currentTimeMillis();
            msg.printOnLn("DEBUG: " + MODE[mode], msg.LINE[0]);
            switch (mode) {
                // select mode
                case SELECT:
                    for (int i = 1; i < MODE.length; i++) {
                        msg.printOnLn(i + ": " + MODE[i], msg.LINE[i]);
                    }
                    // TEST: Change the mode
                    for (int i = 1; i <= MODE.length; i++) {
                        if (stick.getRawButton(i)) {
                            mode = i;
                        }
                    }
                    msg.clearConsole();
                    break;
                case DRIVE:
                    driveWithJoystick();
                    break;
                case WINCH:
                    testWinch();
                    break;
                case LATCH:
                    testLatch();
                    break;
                case COLLECTOR:
                    testCollector();
                    break;
                case CATAPULT:
                    testCatapult();
                    break;
                    
            }
            while(System.currentTimeMillis() - startTime < UPDATE_FREQ);
        }        
    }
    
    private void testLatch() {        
        if(stick.getRawButton(RobotMap.TEST_SET_LATCHED)) {
            msg.printOnLn("Latched", msg.LINE[4]);
           catapult.setLatched();
        } else if(stick.getRawButton(RobotMap.TEST_SET_UNLATCHED)) {
            catapult.setUnlatched();
            msg.printOnLn("unLatched", msg.LINE[4]);
        } 
        
        catapult.hold();       
    }
    
    private void testWinch() {
        if(stick.getRawButton(RobotMap.TEST_WIND_WINCH)) {
            catapult.windWinch();
        } else if(stick.getRawButton(RobotMap.TEST_UNWIND_WINCH)) {
            catapult.unwindWinch();
        } else {
            catapult.stopWinch();
        }        
    }
    
    private void testCollector() {
        
    }
    
    private void testCatapult() {
        if(stick.getRawButton(RobotMap.TEST_PRIME)) {
            catapult.prime();
        } else if(stick.getRawButton(RobotMap.TEST_FIRE)) {
            catapult.fire();
        }
        catapult.hold();
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
            drive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), 0, 0 );            
        } else {
            drive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), stick.getZ(), 0);
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
