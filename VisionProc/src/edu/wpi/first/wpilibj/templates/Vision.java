/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Vision extends SimpleRobot {

    public int rmin = 0, rmax = 70, gmin = 70, gmax = 255, bmin = 170, bmax = 255;
    public int erosionCount = 2;
    public boolean useConnectivity8 = false;
    public CriteriaCollection criteria = new CriteriaCollection();
    private AxisCamera camera;

    protected void robotInit() {
        Messenger.println("Starting up");
        Watchdog.getInstance().kill();
        camera = AxisCamera.getInstance();
        Messenger.println("Done.");
        
        Messenger.println("Welcome!");
        
    }

    public ParticleAnalysisReport[] getParticles(ColorImage img) throws NIVisionException {
        
      

        BinaryImage binaryImage;
        BinaryImage cleanImage;
        BinaryImage filteredImage;
        BinaryImage convexHullImage;

        ParticleAnalysisReport[] particles;

        binaryImage = img.thresholdRGB(rmin, rmax, gmin, gmax, bmin, bmax);
        cleanImage = binaryImage.removeSmallObjects(useConnectivity8, erosionCount);
        convexHullImage = cleanImage.convexHull(useConnectivity8);
        filteredImage = convexHullImage.particleFilter(criteria);
        particles = filteredImage.getOrderedParticleAnalysisReports();

        binaryImage.write("BinaryImage.png");
        filteredImage.write("ConvesHull.png");

        img.free();
        binaryImage.free();
        cleanImage.free();
        convexHullImage.free();
        filteredImage.free();

        return particles;
    }

    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
        try {
            while (isAutonomous() && isEnabled()) {
                Messenger.println("Searching...");
                ParticleAnalysisReport[] parts = getParticles(camera.getImage());
                
                Messenger.println("Found " + parts.length + " targets");
            }
        } catch (AxisCameraException ex) {
            ex.printStackTrace();
        } catch (NIVisionException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {

    }

    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {

    }
}
