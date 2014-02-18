package org.erhsroboticsclub.frc2014;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Talon;
import org.erhsroboticsclub.frc2014.utilities.PIDControllerX2;

public class Catapult {

    // Constants
    private final double WINCH_MOTOR_SPEED = 0.50;
    private final double LATCHED_VOLTAGE   = 1.41;
    private final double UNLATCHED_VOLTAGE = 2.58;
    private final long   LATCH_WAIT_TIME   = 2500;
    private final double KP = 0.5, KI = 0, KD = 0;

    // Motors
    private final Talon winchMotor;
    private final PWM   latchMotor1, latchMotor2;

    // Potentiometers
    private final AnalogChannel winchPot, latchPot;

    // Feedback controllers
    private final PIDControllerX2 latchPID;

    // Instance/State variables
    public final double baseWinchPotVoltage, targetWinchPotVoltage;
    public boolean isPrimed;

    public Catapult() {
        winchMotor = new Talon(RobotMap.WINCH_MOTOR);
        winchPot = new AnalogChannel(RobotMap.WHICH_POT);
        latchMotor1 = new PWM(RobotMap.LATCH_MOTOR_1);
        latchMotor2 = new PWM(RobotMap.LATCH_MOTOR_2);
        latchPot = new AnalogChannel(RobotMap.LATCH_POT);
        latchPID = new PIDControllerX2(KP, KI, KD, UNLATCHED_VOLTAGE);
        latchPID.capOutput(-1, 1);
        baseWinchPotVoltage = winchPot.getAverageVoltage();
        targetWinchPotVoltage = baseWinchPotVoltage + 5 * (3.5/10);
        isPrimed = false;
    }
    
    /*
     **************************************************************************
     * Primary functions.
     **************************************************************************
     */

    /**
     * Wind the winch, set the latch, unwind the winch, but do not fire.
     */
    public void prime() {
        if(!isPrimed) {
            // unlatch
            setUnlatched();
            waitForLatch(LATCH_WAIT_TIME);

            // wind winch
            while(winchPot.getAverageVoltage() < targetWinchPotVoltage) {
                adjustLatch();
                windWinch();
            }

            // latch
            setLatched();
            waitForLatch(LATCH_WAIT_TIME);

            // unwind winch
            while(winchPot.getAverageVoltage() > baseWinchPotVoltage) {
                adjustLatch();
                unwindWinch();
            }
            
            // stop winch
            stopWinch();

            isPrimed = true;
        } else {
            adjustLatch();
        }
    }

    /**
     * Once primed, unlatch to fire the catapult.
     */
    public void fire() {
        if(!isPrimed) {
            prime();
        }
        
        // unlatch
        setUnlatched();
        waitForLatch(LATCH_WAIT_TIME);
        
        isPrimed = false;
    }
  
    /**
     * This function calls the latch PID loop to maintain the position of the
     * latch. If neither the prime() nor the fire() functions are being called,
     * this method should be.
     */
    public void hold() {
        adjustLatch();
    }

    /*
     **************************************************************************
     * Direct operator control.
     * Control the robot with the joystick for debugging purposes
     **************************************************************************
     */
    
    // TODO: add operator control functions
    
    /*
     **************************************************************************
     * Public helper functions.
     * These helper functions are not declared private because they are useful
     * for debugging/testing
     **************************************************************************
     */
    public void windWinch() {
        winchMotor.set(WINCH_MOTOR_SPEED);
    }
    public void unwindWinch() {
        winchMotor.set(-WINCH_MOTOR_SPEED);
    }
    public void stopWinch() {
        winchMotor.set(0);
    }
    public void setLatched() {
        latchPID.setSetpoint(LATCHED_VOLTAGE);
    }
    public void setUnlatched() {
        latchPID.setSetpoint(UNLATCHED_VOLTAGE);
    }
    public void adjustLatch() {
        double response = latchPID.getPIDResponse(latchPot.getAverageVoltage());
        // map response from [-1, 1] to [0, 255] because the VEX motors used
        // to control the latch take a raw PWM signal
        int mappedResponse1 = (int) map(response, -1, 1, 0, 255);
        int mappedResponse2 = (int) map(response, -1, 1, 255, 0);
        latchMotor1.setRaw(mappedResponse1);
        latchMotor2.setRaw(mappedResponse2);
    }
    public void waitForLatch(long waitTime) {
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < waitTime) {
            adjustLatch();
        }
    }
    
    /*
     **************************************************************************
     * Private helper functions.
     **************************************************************************
     */

    private double map(double x, double inMin, double inMax, double outMin, double outMax) {
        return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    } 

}
