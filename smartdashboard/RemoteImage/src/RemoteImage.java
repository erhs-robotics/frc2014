
import edu.wpi.first.smartdashboard.gui.StaticWidget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.properties.StringProperty;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author michael
 */
public class RemoteImage extends StaticWidget {

    BufferedImage image;
    Timer timer;       
    private boolean connected = false;
<<<<<<< HEAD
    
=======
    public final StringProperty fileName = new StringProperty(this, "Image Name", "ftp://10.0.53.2/ni-rt/system/BinaryImage.png");
>>>>>>> 0967d09f4c086595f3e8366fe23c6886d2348c4f

    @Override
    public void init() {        
        setPreferredSize(new Dimension(200, 200));       

        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                
                    try {
<<<<<<< HEAD
                        image = (BufferedImage) ImageIO.read(new URL(url));
=======
                        image = ImageIO.read(new URL(fileName.getValue()));
>>>>>>> 0967d09f4c086595f3e8366fe23c6886d2348c4f
                        connected = true;
                    } catch (MalformedURLException ex) {
                        System.out.println("FATAL ERROR! Bad url");
                        connected = false;
                    } catch (IOException ex) {
                        Logger.getLogger(RemoteImage.class.getName()).log(Level.SEVERE, null, ex);
                        connected = false;
                    }
                      
                
                repaint();
            }
        });
        timer.start();
        
    }

    @Override
    public void propertyChanged(Property prprt) {

    }

    
    @Override
    public void paint(Graphics g) {
        Dimension size = getSize();
        
<<<<<<< HEAD
        
        
        if (connected) {
            synchronized(lock) {
                g.drawImage(image, 0, 0, size.width / size.height * image.getHeight(), size.height / size.width * image.getWidth(), this);        
            }
=======
        if (connected) {            
                g.drawImage(image, 0, 0,size.width, size.height, this);        
>>>>>>> 0967d09f4c086595f3e8366fe23c6886d2348c4f
        } else {
            g.setColor(Color.PINK);
            g.setFont(new Font("Dialog", Font.PLAIN, 20));
            g.drawString("NO CONNECTION!!", 20, 20);
        }
    }

}
