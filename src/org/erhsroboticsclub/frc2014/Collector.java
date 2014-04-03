package org.erhsroboticsclub.frc2014;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Talon;
import org.erhsroboticsclub.frc2014.utilities.MathUtils;
import org.erhsroboticsclub.frc2014.utilities.PIDControllerX2;

public class Collector {
    
    private final Talon collectMotor, rotateMotor1, rotateMotor2;    
    public static double COLLECT_MOTOR_SPEED = -.4;
    public static double HOLD_MOTOR_SPEED = 0;
    public static double MAX_ROTATE_MOTOR_SPEED = 0.7;    
    public final PIDControllerX2 pid;
    public final AnalogChannel anglePot;
    private static final double KP = 0, KI = 0, KD = 0;
    private static final double MIN_POT_VALUE = 1, MAX_POT_VALUE = 4;
    public static final double DEPLOY_ANGLE = 30, STOW_ANGLE = 70;
    
    public Collector() {
        collectMotor = new Talon(RobotMap.COLLECTOR_COLLECT_MOTOR);
        rotateMotor1 = new Talon(RobotMap.COLLECTOR_ROTATION_MOTOR1);
        rotateMotor2 = new Talon(RobotMap.COLLECTOR_ROTATION_MOTOR2);
        anglePot = new AnalogChannel(RobotMap.COLLECTOR_ANGLE_POT);        
        pid = new PIDControllerX2(KP, KI, KD, anglePot.getAverageVoltage());
        pid.capOutput(-MAX_ROTATE_MOTOR_SPEED, MAX_ROTATE_MOTOR_SPEED);
    }
    
    public void collect() {
        collectMotor.set(COLLECT_MOTOR_SPEED);        
    }
    
    public void eject() {
        collectMotor.set(-COLLECT_MOTOR_SPEED);
    }
    
    public void stow() {
        pid.setSetpoint(STOW_ANGLE);
    }
    
    public void deploy() {
        pid.setSetpoint(DEPLOY_ANGLE);
    }
    
    public void stopCollector() {
        collectMotor.set(0);
    }
    
    public void rotate(double speed) {
        double scaledSpeed = MathUtils.map(speed, -1, 1, -MAX_ROTATE_MOTOR_SPEED, MAX_ROTATE_MOTOR_SPEED);
        rotateMotor1.set(scaledSpeed);
        rotateMotor2.set(-scaledSpeed);          
    }
    
    public void stopRotating() {
        rotateMotor1.set(0);
        rotateMotor2.set(0);        
    }
    
    public void setTargetAngle(double angle) {        
        pid.setSetpoint(MathUtils.map(angle, 0, 90, MIN_POT_VALUE, MAX_POT_VALUE));
    }
    
    public double getCurrentAngle() {
        return MathUtils.map(anglePot.getAverageVoltage(), MIN_POT_VALUE, MAX_POT_VALUE, 0, 90);
    }   
    
    public void update() {
        double out = pid.getPIDResponse(anglePot.getAverageVoltage());
        rotateMotor1.set(out);
        rotateMotor2.set(-out);        
    }
}
