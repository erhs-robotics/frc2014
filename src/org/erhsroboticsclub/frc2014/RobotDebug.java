package org.erhsroboticsclub.frc2014;

/**
 * A class to unit test the various subsystems of the robot.
 * @author nick
 */
public class RobotDebug {
    
    private Robot robot;
    
    public RobotDebug(Robot robot) {
        this.robot = robot;
    }
    
    public void drive() {
        if (!robot.stick.getRawButton(1)) {
            robot.drive.mecanumDrive_Cartesian(robot.stick.getX(), robot.stick.getY(), 
                                               0, robot.gyro.getAngle());
        } else {
            robot.drive.mecanumDrive_Cartesian(robot.stick.getX(), robot.stick.getY(), 
                                               robot.stick.getZ(), robot.gyro.getAngle());
        }
    }
    
    public void winch() {
        
    }
    
    public void release() {
        
    }
    
    public void collector() {
        
    }
    
}
