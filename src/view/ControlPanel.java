package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import controller.Controller;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel
{
    private static final String TITLE = "Control";
    private static final Dimension SIZE = new Dimension(900, 75);
    private static final Dimension OPTION_PANEL_SIZE = new Dimension(900, 39);
    
    private Controller myController = Controller.getInstance();
    private JButton myRunButton;
    private JButton myResetButton;
    
    protected ControlPanel ()
    {        
        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                                                              TITLE);
        title.setTitleJustification(TitledBorder.CENTER);
        
        setBorder(title);
        setLayout(new BorderLayout());
        setPreferredSize(SIZE);

        add(makeOptionPanel(), BorderLayout.CENTER);
        
        enableButtons();
    }

    protected void enableButtons ()
    {
        myRunButton.setEnabled(myController.canRun());
        myResetButton.setEnabled(myController.canReset());
    }
    
    private JComponent makeOptionPanel ()
    {
        JPanel panel = new JPanel();
        panel.setPreferredSize(OPTION_PANEL_SIZE);

        myRunButton = new JButton("Run");
        myRunButton.addActionListener(new RunAction());
        panel.add(myRunButton);
        
        myResetButton = new JButton("Reset");
        myResetButton.addActionListener(new ResetAction());
        panel.add(myResetButton);
        
        return panel;
    }
    
    private void showRunDialog ()
    {
        StringBuilder message = new StringBuilder();
        message.append("TiVOO will now load the selected files, apply the selected filters, ");
        message.append("and output the selected views in a new window.\n");
        message.append("This will take a few moments. Press OK to acknowledge and begin.");
        JOptionPane.showMessageDialog(this, message.toString(), "Run-Time Information",
                                      JOptionPane.INFORMATION_MESSAGE);
    }
    
    private class RunAction implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            try
            {
                showRunDialog();
                myController.run();
            }
            catch (SAXException e1)
            {
                e1.printStackTrace();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            catch (ParserConfigurationException e1)
            {
                e1.printStackTrace();
            }
            enableButtons();       
        }
    } 
    
    private class ResetAction implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            myController.reset();
            enableButtons();
        }
    }

}
