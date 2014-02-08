package org.erhsroboticsclub.frc2014;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Talon;
import org.erhsroboticsclub.frc2014.utilities.Messenger;

public class Robot extends SimpleRobot {
        Talon motor;
        Talon motor2;
        Talon motor3;
        Talon motor4;
        Joystick stick;
        Messenger msg;
        RobotDrive drive;
    
    public void robotInit() {
        getWatchdog().kill();
        motor = new Talon(1);
        motor2 = new Talon(2);
        motor3 = new Talon(3);
        motor4 = new Talon(4);
        stick = new Joystick(1);
        msg = new Messenger();
        drive = new RobotDrive(motor, motor2, motor3, motor4);
    }
        
    public void autonomous() {
        
    }

    public void operatorControl() {
        
    }
    
}
