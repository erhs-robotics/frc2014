package org.erhsroboticsclub.frc2014;

import edu.wpi.first.wpilibj.*;
import org.erhsroboticsclub.frc2014.utilities.Messenger;

public class Robot extends SimpleRobot {
        Talon motor;
        Talon motor2;
        Talon motor3;
        Talon motor4;
        Joystick stick;
        Messenger msg;
        RobotDrive drive;
        Gyro gyro;
    public void robotInit() {
        getWatchdog().kill();
        motor = new Talon(1);
        motor2 = new Talon(2);
        motor3 = new Talon(3);
        motor4 = new Talon(4);
        stick = new Joystick(1);
        msg = new Messenger();
        drive = new RobotDrive(motor, motor2, motor3, motor4);
        gyro = new Gyro(1);
    }
        
    public void autonomous() {
        
    }

    public void operatorControl() {
        if (!stick.getRawButton(1)) {
            drive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), 0, gyro.getAngle());
        }
        else {
            drive.mecanumDrive_Cartesian(stick.getX(), stick.getY(), stick.getZ(), gyro.getAngle());
        }
    }
        
    
}
