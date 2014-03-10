package org.erhsroboticsclub.frc2014;

import edu.wpi.first.wpilibj.Victor;

public class Collector {
    private final Victor collectMotor, rotateMotor1, rotateMotor2;
    public static double COLLECT_MOTOR_SPEED = .29;
    private static final double ROTATE_MOTOR_SPEED = 0.4;
    
    public Collector() {
        collectMotor = new Victor(RobotMap.COLLECTER_COLLECT_MOTOR);
        rotateMotor1 = new Victor(RobotMap.COLLECTER_ROTATION_MOTOR1);
        rotateMotor2 = new Victor(RobotMap.COLLECTER_ROTATION_MOTOR2);
    }
    
    public void collect() {
        collectMotor.set(COLLECT_MOTOR_SPEED);
    }
    
    public void eject() {
        collectMotor.set(-COLLECT_MOTOR_SPEED);
    }
    
    public void stopCollecter() {
        collectMotor.set(0);
    }
    
    public void rotateUp() {
        rotateMotor1.set(ROTATE_MOTOR_SPEED);
        rotateMotor2.set(ROTATE_MOTOR_SPEED);          
    }
    
    public void rotateDown() {
        rotateMotor1.set(-ROTATE_MOTOR_SPEED);
        rotateMotor2.set(-ROTATE_MOTOR_SPEED);          
    }
    
    public void stopRotating() {
        rotateMotor1.set(0);
        rotateMotor2.set(0);        
    }

}
