/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Michael Stevens
 */
public class Messenger {
    static {
        SmartDashboard.putString("dashboard", "");
    }
    public static void println(String str) {        
        String data = SmartDashboard.getString("dashboard");
        String time = "[" + (int)Timer.getFPGATimestamp() + "]: ";
        SmartDashboard.putString("dashboard", data + time + str + "\n");
    }
}
