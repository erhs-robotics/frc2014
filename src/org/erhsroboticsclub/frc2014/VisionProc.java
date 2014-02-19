package org.erhsroboticsclub.frc2014;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

public class VisionProc {

    static public int rmin = 0, rmax = 90, gmin = 50, gmax = 255, bmin = 150, bmax = 255;
    static public int erosionCount = 2;
    static public boolean useConnectivity8 = false;
    static public final CriteriaCollection criteria = new CriteriaCollection();
    static private final AxisCamera camera = AxisCamera.getInstance();

    static {
        criteria.addCriteria(NIVision.MeasurementType.IMAQ_MT_AREA, 1000, 3000, false);
    }

    static public ParticleAnalysisReport[] getParticles(ColorImage img) throws NIVisionException {
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

    static public boolean facingHotTarget() {
        try {
            ParticleAnalysisReport[] particles = getParticles(camera.getImage());
            if (particles.length < 2) {
                return false;
            }
            return true;

        } catch (AxisCameraException ex) {
        } catch (NIVisionException ex) {
        }

        return false;
    }
}
