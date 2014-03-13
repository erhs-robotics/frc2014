package org.erhsroboticsclub.frc2014;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.erhsroboticsclub.frc2014.utilities.JoystickX;
import org.erhsroboticsclub.frc2014.utilities.MathUtils;
import org.erhsroboticsclub.frc2014.utilities.Messenger;
import org.erhsroboticsclub.frc2014.utilities.PIDControllerX2;

public class Robot extends SimpleRobot {

    // Subsystems
    Catapult catapult;
    Collector collector;
    RobotDrive drive;

    // Joysticks
    JoystickX stick;
    JoystickX collectorStick;

    // Sensor inputs
    Gyro gyro;
    PIDControllerX2 gyroPID;
    AnalogChannel autoModePot;

    // Utility classes
    Messenger msg;

    // Constants
    private static final long UPDATE_FREQ = 20;
    private static final long AUTO_DRIVE_TIME = 100;
    private static final int AUTO_DRIVE_SPEED = 1;
    private static double AUTO_BIAS = 2;

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
        collectorStick = new JoystickX(RobotMap.COLLECTOR_STICK);

        // Sensor inputs
        gyro = new Gyro(RobotMap.GYRO);
        gyroPID = new PIDControllerX2(0, 0, 0, 5);
        autoModePot = new AnalogChannel(RobotMap.AUTO_MODE_POT);

        // Utility classes
        msg = new Messenger();

        killSafety();
    }

    public void autonomous() {
        gyro.reset();
        AUTO_BIAS = SmartDashboard.getNumber("AutoBias");
        msg.printLn("Pot: " + autoModePot.getAverageVoltage());
        boolean driftLeft = autoModePot.getAverageVoltage() >= 2.5;
        double bias = driftLeft ? -AUTO_BIAS : AUTO_BIAS;

        long time = System.currentTimeMillis();
        // may have to hold collector up
        while (System.currentTimeMillis() - time < AUTO_DRIVE_TIME) {
            driveStraight(AUTO_DRIVE_SPEED, 0, bias);
        }
        // may have to hold collector up
        collector.eject();

    }

    public void operatorControl() {
        gyro.reset();

        while (isEnabled() && isOperatorControl()) {
            long startTime = System.currentTimeMillis();

            operatorDrive();
            operatorCollector();

            while (System.currentTimeMillis() - startTime < UPDATE_FREQ);
        }
    }

    /*
     **************************************************************************
     * Control Functions. 
     * Break down the various controls of the robot into small routines that 
     * can be called by the operatorControl() function
     **************************************************************************
     */
    ////////////////////////////////////////////////////////////////////////////
    // DRIVE                                                                  //
    ////////////////////////////////////////////////////////////////////////////
    public void driveWithJoystick() {
        if (stick.getRawButton(RobotMap.ALLOW_CHASSIS_ROTATION)) {
            drive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), stick.getZ(), 0);
        } else {
            drive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), 0, 0);
        }
    }

    public void driveStraight(double speed, double targetAngle, double bias) {
        double actualAngle = gyro.getAngle();
        double e = targetAngle - actualAngle;
        gyroPID.setSetpoint(targetAngle);
        double out = gyroPID.getPIDResponse(actualAngle);
        msg.printLn("" + e);
        drive.mecanumDrive_Cartesian(0, speed, out + bias, 0);
    }

    public void operatorDrive() {
        if (stick.buttonPressed(RobotMap.DRIVE_STRAIGHT)) {
            gyro.reset();
        }
        if (stick.getRawButton(RobotMap.DRIVE_STRAIGHT)) {
            driveStraight(-stick.getY(), 0, 0);
            System.out.println("Driving with PID");
        } else {
            driveWithJoystick();
            System.out.println("Driving unaided");
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // COLLECTOR                                                              //
    ////////////////////////////////////////////////////////////////////////////
    public void operatorCollector() {
        // Control angle
        collector.rotate(-collectorStick.getY());

        // Collect or eject
        if (collectorStick.getRawButton(RobotMap.COLLECTOR_COLLECT)) {
            collector.collect();
        } else if (collectorStick.getRawButton(RobotMap.COLLECTOR_EJECT)) {
            collector.eject();
        } else {
            collector.stopCollector();
        }
    }

    /*
     **************************************************************************
     * Test functions
     * Break down the parts of the robot into small subsystems and write unit
     * tests for each.
     **************************************************************************
     */
    public void test() {
        final int SELECT = 0, DRIVE = 1, WINCH = 2, LATCH = 3, COLLECTOR = 4,
                CATAPULT = 5, COLLECTOR_PID = 6;
        String[] MODE = new String[7];
        MODE[SELECT] = "Select";
        MODE[DRIVE] = "Drive";
        MODE[WINCH] = "Winch";
        MODE[LATCH] = "Latch";
        MODE[COLLECTOR] = "Collector";
        MODE[CATAPULT] = "Catapult";
        MODE[COLLECTOR_PID] = "Collector PID";
        int mode = 0;

        // init SD!
        initSmartDashboard();

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
                    testDrive();
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
                case COLLECTOR_PID:
                    testCollectorPID();
                    break;
            }
            while (System.currentTimeMillis() - startTime < UPDATE_FREQ);
        }
    }

    private void initSmartDashboard() {
        SmartDashboard.putNumber("KP", 0.06);
        SmartDashboard.putNumber("KI", 0.00);
        SmartDashboard.putNumber("KD", 0.07);

        SmartDashboard.putNumber("CollectSpeed", 0.29);
        SmartDashboard.putNumber("HoldSpeed", 0);
        SmartDashboard.putNumber("RotateSpeed", 0.40);

        SmartDashboard.putNumber("AutoBias", AUTO_BIAS);
    }

    private void testDrive() {
        /* Update PID from SmartDashboard */
        double p = SmartDashboard.getNumber("KP", 0.06);
        double i = SmartDashboard.getNumber("KI", 0);
        double d = SmartDashboard.getNumber("KD", 0.07);
        gyroPID.setKP(p);
        gyroPID.setKI(i);
        gyroPID.setKD(d);

        if (stick.buttonPressed(RobotMap.DRIVE_STRAIGHT)) {
            gyro.reset();
        }
        if (stick.getRawButton(RobotMap.DRIVE_STRAIGHT)) {
            driveStraight(-stick.getY(), 0, 0);
            System.out.println("Driving with PID");
        } else {
            driveWithJoystick();
            System.out.println("Driving unaided");
        }
    }

    private void testLatch() {

        if (stick.getRawButton(RobotMap.TEST_SET_LATCHED)) {
            msg.printOnLn("Latched", msg.LINE[4]);
            catapult.setLatched();
        } else if (stick.getRawButton(RobotMap.TEST_SET_UNLATCHED)) {
            catapult.setUnlatched();
            msg.printOnLn("unLatched", msg.LINE[4]);
        }

        catapult.hold();
    }

    private void testWinch() {
        testLatch();
        if (stick.getRawButton(RobotMap.TEST_WIND_WINCH)) {
            catapult.windWinch();
        } else if (stick.getRawButton(RobotMap.TEST_UNWIND_WINCH)) {
            catapult.unwindWinch();
        } else {
            catapult.stopWinch();
        }
        catapult.latchMotor1.setRaw((int) Catapult.map(stick.getY(), -1, 1, 0, 255));
        catapult.latchMotor2.setRaw((int) Catapult.map(stick.getY(), -1, 1, 255, 0));
    }

    private void testCollector() {
        Collector.COLLECT_MOTOR_SPEED = SmartDashboard.getNumber("CollectSpeed");
        Collector.HOLD_MOTOR_SPEED = SmartDashboard.getNumber("HoldSpeed");
        Collector.MAX_ROTATE_MOTOR_SPEED = SmartDashboard.getNumber("RotateSpeed");
        msg.printOnLn("Pot: " + collector.anglePot.getAverageValue(), DriverStationLCD.Line.kUser1);
        operatorCollector();

    }

    private void testCollectorPID() {
        double p = SmartDashboard.getNumber("KP", 0.06);
        double i = SmartDashboard.getNumber("KI", 0);
        double d = SmartDashboard.getNumber("KD", 0);
        collector.pid.setKP(p);
        collector.pid.setKI(i);
        collector.pid.setKD(d);
        double angle = collectorStick.getZ(); // might be getThrottle() instead
        angle = MathUtils.map(angle, -1, 1, 0, 90);
        collector.setTargetAngle(angle);
        collector.update();

        msg.printOnLn("Set: " + angle, DriverStationLCD.Line.kUser2);
        msg.printOnLn("Cur: " + collector.getCurrentAngle(), DriverStationLCD.Line.kUser3);
    }

    private void testCatapult() {
        if (stick.getRawButton(RobotMap.TEST_PRIME)) {
            catapult.prime();
        } else if (stick.getRawButton(RobotMap.TEST_FIRE)) {
            catapult.fire();
        }
        catapult.hold();
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
