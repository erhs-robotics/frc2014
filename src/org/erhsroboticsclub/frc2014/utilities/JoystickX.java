package org.erhsroboticsclub.frc2014.utilities;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Class extending the capabilities of the FRC Joystick class.
 * 
 * @author David
 */
public class JoystickX extends Joystick {
    
    private final int MAX_BUTTON = 20;
    private final int MAX_AXIS = 10;
    private boolean[] flags = new boolean[MAX_BUTTON];
    private boolean flag_trigger;
    private double throttle;
    /**
     * Wrapper for the FRC Joystick Class 
     * @param port
     */
    public JoystickX(int port) {
        super(port);
    }
    
    /**
     * Use instead of getRawButton()
     * @param button
     * @return if the button is currently being held down.
     */
    public boolean isButtonDown(int button) {
        if(button >= MAX_BUTTON || button <= 0) return false;
        return getRawButton(button);
    }
    
    /**
     * 
     * @param button
     * @return whether the button has been pressed
     */
    public boolean buttonPressed(int button) {
        if(button >= MAX_BUTTON || button <= 0) return false;
        if(isButtonDown(button)) {
            if(!flags[button]) 
                return (flags[button] = true);
            else return false;
        }
        else return (flags[button] = false);
    }
    
    /**
     * Use instead of getRawAxis
     * @param axis
     * @return some sort of information about the axis
     */
    public double getAxis(int axis) {
        if(axis >= MAX_AXIS || axis <= 0) return -1;
        return getRawAxis(axis);
    }
    
    
    /**
     * @return position of the throttle
	 * @Override
     */
    public double getThrottle() {
        return (throttle = super.getThrottle());
    }
    /**
     * @return the difference between the current throttle and the value 
     *  recorded the last time getThrottle or getDeltaThrottle were called
     */
    public double getDeltaThrottle() {
        double delta = super.getThrottle() - throttle;
        this.getThrottle();
        return delta;
    }
    
    /**
     * @return true if the trigger has been pressed, 
     *  but not again until after it is released.
     */
    public boolean getTriggerPressed() {
        if(getTrigger()) {
            if(!flag_trigger) 
                return (flag_trigger = true);
            else return false;
        }
        else return (flag_trigger = false);
    }
    
}
