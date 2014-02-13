package org.erhsroboticsclub.frc2014;

import edu.wpi.first.wpilibj.*;
import org.erhsroboticsclub.frc2014.utilities.Messenger;

public class Robot extends SimpleRobot {

    // Motors and motor control
    Talon topLeftMotor, bottomLeftMotor, topRightMotor, bottomRightMotor;
    RobotDrive drive;
    Joystick stick;
    
    // Sensor inputs
    Gyro gyro;
    
    // Utility classes
    Messenger msg;

    public void robotInit() {
        // Motors and motor control
        topLeftMotor     = new Talon(RobotMap.TOP_LEFT_MOTOR);
        bottomLeftMotor  = new Talon(RobotMap.BOTTOM_LEFT_MOTOR);
        topRightMotor    = new Talon(RobotMap.TOP_RIGHT_MOTOR);
        bottomRightMotor = new Talon(RobotMap.BOTTOM_RIGHT_MOTOR);
        drive = new RobotDrive(topLeftMotor, bottomLeftMotor, 
                               topRightMotor, bottomRightMotor);
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
        if (!stick.getRawButton(1)) {
            drive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), 0, gyro.getAngle());
        } else {
            drive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), stick.getZ(), gyro.getAngle());
        }
    }
    
    private void killSafety() {
        getWatchdog().kill();
        drive.setSafetyEnabled(false);
    }

}
