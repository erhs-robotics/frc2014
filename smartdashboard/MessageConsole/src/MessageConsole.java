
import edu.wpi.first.smartdashboard.gui.Widget;
import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.types.DataType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author michael
 */
public class MessageConsole extends Widget {

    public static final String NAME = "Dashboard";
    public static final DataType[] TYPES = {DataType.STRING};
    private JTextArea log;

    private String data = "";

    @Override
    public void setValue(Object value) {
       
        data += ((String) value) + "\n";
        log.setText(data);
        
    }

    @Override
    public void init() {        
        setPreferredSize(new Dimension(100, 200));
        log = new JTextArea();
        log.setSize(new Dimension(100, 200));
        DefaultCaret caret = (DefaultCaret)log.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        add(log);
    }

    @Override
    public void propertyChanged(Property property) {
    }

}
