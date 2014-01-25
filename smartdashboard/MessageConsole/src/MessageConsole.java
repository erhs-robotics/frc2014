
import edu.wpi.first.smartdashboard.gui.DashboardFrame;
import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.connection.NetworkTableConnection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;
import org.jfree.ui.action.ActionButton;

/**
 *
 * @author michael
 */
public class MessageConsole extends Widget {

    public static final String NAME = "Dashboard";
    public static final DataType[] TYPES = {DataType.STRING};
    private JTextArea log;
    private JLabel label;
    private JScrollPane scrollPane;
    private JButton saveButton;
    private JButton clearButton;
    private NetworkTable table;
    private String data = "";
    private String lastData = "";
    private String saveFile = "output-%s.txt";

    @Override
    public void setValue(Object value) {
        String newData = (String) value;
        if(!newData.equals("") && !lastData.equals(newData)) {
            data += newData;
            log.setText(data);
            log.setCaretPosition(log.getSelectionEnd());   
           
            lastData = newData;
            table.putString("dashboard", "");
        }

        
        
        
        
    }

    @Override
    public void init() {
        table = NetworkTable.getTable("SmartDashboard");
        
        this.setLayout(new BorderLayout());
        
        
        log = new JTextArea();   
        scrollPane = new JScrollPane(log);
        label = new JLabel("Message Console");
        label.setFont(new Font("Dialog", Font.PLAIN, 16));
        saveButton = new ActionButton("Save Output");
        clearButton = new ActionButton("Clear Console");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String name = String.format(saveFile, System.currentTimeMillis());
                BufferedWriter writer;
                try {
                    writer = new BufferedWriter(new FileWriter(new File(name)));
                    writer.write(data);
                    writer.close();
                } catch (IOException ex){}               
            }
        });
        
       
        
        
        add(label, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
        
        this.setMinimumSize(new Dimension(50, 50));
        this.setPreferredSize(new Dimension(500, 100));
        
        
    }   

    @Override
    public void propertyChanged(Property prprt) {
        
    }
    
    

}
