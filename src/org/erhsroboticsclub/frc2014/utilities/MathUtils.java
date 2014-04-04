package org.erhsroboticsclub.frc2014.utilities;

public class MathUtils {
    public static double map(double x, double inMin, double inMax, double outMin, double outMax) {
        return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }
    
    public static int sign(double x) {
        return (int) (Math.abs(x) / x);
    }
}
