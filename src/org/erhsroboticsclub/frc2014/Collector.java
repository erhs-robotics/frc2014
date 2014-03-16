package org.erhsroboticsclub.frc2014;

import edu.wpi.first.wpilibj.Talon;

public class Collector {
    
    private final Talon collectMotor, rotateMotor1, rotateMotor2;    
    public static double COLLECT_MOTOR_SPEED = -.4;
    public static double HOLD_MOTOR_SPEED = 0;
    public static double MAX_ROTATE_MOTOR_SPEED = 0.7;    
    
    public Collector() {
        collectMotor = new Talon(RobotMap.COLLECTOR_COLLECT_MOTOR);
        rotateMotor1 = new Talon(RobotMap.COLLECTOR_ROTATION_MOTOR1);
        rotateMotor2 = new Talon(RobotMap.COLLECTOR_ROTATION_MOTOR2);        
    }
    
    public void collect() {
        collectMotor.set(COLLECT_MOTOR_SPEED);        
    }
    
    public void eject() {
        collectMotor.set(-COLLECT_MOTOR_SPEED);
    }
    
    public void stopCollector() {
        collectMotor.set(0);
    }
    
    public void rotate(double speed) {
        double scaledSpeed = Catapult.map(speed, -1, 1, -MAX_ROTATE_MOTOR_SPEED, MAX_ROTATE_MOTOR_SPEED);
        rotateMotor1.set(scaledSpeed);
        rotateMotor2.set(scaledSpeed);          
    }
    
    public void stopRotating() {
        rotateMotor1.set(0);
        rotateMotor2.set(0);        
    }
}
