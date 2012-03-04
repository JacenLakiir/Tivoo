package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class OutputPanel extends JPanel
{
    private static final String TITLE = "Output";
    private static final Dimension SIZE = new Dimension(300, 250);
    private static final String[] views = { "horizontal", "vertical", "sorted", "conflict", "calendar" };

    private JTextPane mySelectedViews;
    private JButton myAddViewButton;
    private JButton myClearButton;
    
    private List<String> myOutput;
    
    protected OutputPanel ()
    {
        myOutput = new ArrayList<String>();
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), TITLE));
        setPreferredSize(SIZE);

        add(makeOutputPanel(), BorderLayout.CENTER);
        add(makeOptionPanel(), BorderLayout.SOUTH);
        
        enableButtons();
    }
    
    protected List<String> getOutput ()
    {
        return Collections.unmodifiableList(myOutput);
    }  
        
    private JComponent makeOutputPanel ()
    {
        mySelectedViews = new JTextPane();
        mySelectedViews.setEditable(false);
        return new JScrollPane(mySelectedViews);
    }
    
    private JComponent makeOptionPanel ()
    {
        JPanel panel = new JPanel();
        
        myAddViewButton = new JButton("Add View");
        myAddViewButton.addActionListener(new AddViewAction());
        panel.add(myAddViewButton);
        
        myClearButton = new JButton("Clear");
        myClearButton.addActionListener(new ClearAction());
        panel.add(myClearButton);
        
        return panel;
    }

    private void enableButtons ()
    {
        myAddViewButton.setEnabled(myOutput.size() < 5);
        myClearButton.setEnabled(myOutput.size() > 0);
    }
    
    private void selectView ()
    {
        String view = (String) JOptionPane.showInputDialog(this, "Select a view:",
                                                           "Add View",
                                                           JOptionPane.PLAIN_MESSAGE,
                                                           null,
                                                           views,
                                                           "horizontal");
        if (view != null)
            updateSelectedOutput(view);
    }
    
    private void updateSelectedOutput (String view)
    {
        String viewURL = view + ".html";
        if (!myOutput.contains(viewURL))
        {
            myOutput.add(viewURL);
            mySelectedViews.setText(mySelectedViews.getText() + "\n" + view);
        }
    }
    
    private class AddViewAction implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            selectView();
            enableButtons();
        }
    } 
    
    private class ClearAction implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            myOutput.clear();
            mySelectedViews.setText("");
            enableButtons();
        }
    }  
    
}
