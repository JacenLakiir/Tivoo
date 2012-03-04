package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import controller.Controller;

@SuppressWarnings("serial")
public class HTMLViewer extends JFrame
{
    private static final String TITLE = "TiVOO Viewer";
    private static final Dimension SIZE = new Dimension(1200, 900);
    private static final Dimension PAGE_DISPLAY_SIZE = new Dimension(800, 600);
    private static final String BLANK = " ";
    private static final String USER_DIR = System.getProperties().getProperty("user.dir");
    private static final String OUTPUT_DIR = "output";

    private JEditorPane myPage;
    private JLabel myStatus;
    private JTextField myURLDisplay;
    private JButton myBackButton;
    private JButton myNextButton;
    private JButton myGoButton;
    private JButton myResetButton;
    private JComboBox mySwitchViewComboBox;
    
    private Controller myController = Controller.getInstance();
    private HTMLModel myModel;
    private List<String> myViews;
    
    public HTMLViewer (List<String> views, String startURL)
    {
        myModel = new HTMLModel();
        myViews = views;
        
        setTitle(TITLE);
        setPreferredSize(SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(makePageDisplay(), BorderLayout.CENTER);
        add(makeInputPanel(), BorderLayout.NORTH);
        add(makeInformationPanel(), BorderLayout.SOUTH);

        enableButtons();
        showPage(startURL);
        
        pack();
        setVisible(true);
    }

    private void showPage (String url)
    {
        try
        {
            if (!url.startsWith("file:/"))
            {
                url = "file://" + USER_DIR + "/" +  OUTPUT_DIR + "/" + url;
            }
            new URL(url);
            myModel.go(url);
            update(url);
        }
        catch (MalformedURLException e)
        {
            showError("Could not load " + url);
        }
    }

    private void showError (String message)
    {
        JOptionPane.showMessageDialog(this, message, "Browser Error",
                                      JOptionPane.ERROR_MESSAGE);
    }

    private void showStatus (String message)
    {
        myStatus.setText(message);
    }

    private void next ()
    {
        String url = myModel.next();
        if (url != null)
            update(url);
    }

    private void back ()
    {
        String url = myModel.back();
        if (url != null)
            update(url);
    }

    private void update (String url)
    {
        try
        {
            myPage.setPage(url);
            myURLDisplay.setText(url);
            enableButtons();
        }
        catch (IOException e)
        {
            showError("Could not load " + url);
        }
    }

    private void enableButtons ()
    {
        myBackButton.setEnabled(myModel.hasPrevious());
        myNextButton.setEnabled(myModel.hasNext());
        mySwitchViewComboBox.setEnabled(myViews.size() > 1);
    }

    private JComponent makePageDisplay ()
    {
        myPage = new JEditorPane();
        myPage.setPreferredSize(PAGE_DISPLAY_SIZE);
        myPage.setEditable(false);
        myPage.addHyperlinkListener(new LinkFollower());
        return new JScrollPane(myPage);
    }

    private JComponent makeInputPanel ()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(makeNavigationPanel(), BorderLayout.NORTH);
        return panel;
    }

    private JComponent makeInformationPanel ()
    {
        myStatus = new JLabel(BLANK);
        return myStatus;
    }

    private JComponent makeNavigationPanel ()
    {
        JPanel panel = new JPanel();
        
        myBackButton = new JButton("Back");
        myBackButton.addActionListener(new BackAction());
        panel.add(myBackButton);

        myNextButton = new JButton("Next");
        myNextButton.addActionListener(new NextAction());
        panel.add(myNextButton);

        myURLDisplay = new JTextField(50);
        myURLDisplay.addActionListener(new ShowPageAction());
        panel.add(myURLDisplay);

        myGoButton = new JButton("Go");
        myGoButton.addActionListener(new ShowPageAction());
        panel.add(myGoButton);
        
        mySwitchViewComboBox = new JComboBox(myViews.toArray());
        mySwitchViewComboBox.setSelectedIndex(0);
        mySwitchViewComboBox.addActionListener(new SwitchViewAction());
        panel.add(mySwitchViewComboBox);
        
        myResetButton = new JButton("Reset");
        myResetButton.addActionListener(new RestartAction());
        panel.add(myResetButton);

        return panel;
    }

    private int showRestartDialog ()
    {
        StringBuilder message = new StringBuilder();
        message.append("Are you sure you want to restart?\n");
        message.append("This will return you to the main TiVOO screen ");
        message.append("and reset all of your selections.");
        return JOptionPane.showConfirmDialog(this, message.toString(), "Restart Warning",
                                             JOptionPane.YES_NO_OPTION);
    }
    
    private class ShowPageAction implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            showPage(myURLDisplay.getText());
        }
    }
        
    private class BackAction implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            back();
        }
    }
     
    private class NextAction implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            next();
        }
    }

    private class LinkFollower implements HyperlinkListener
    {
        @Override
        public void hyperlinkUpdate (HyperlinkEvent evt)
        {
            if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
            {
                showPage(evt.getURL().toString());
            }
            else if (evt.getEventType() == HyperlinkEvent.EventType.ENTERED)
            {
                showStatus(evt.getURL().toString());
            }
            else if (evt.getEventType() == HyperlinkEvent.EventType.EXITED)
            {
                showStatus(BLANK);
            }
        }
    }
    
    private class SwitchViewAction implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            JComboBox cb = (JComboBox) e.getSource();
            String selectedView = (String) cb.getSelectedItem();
            showPage(selectedView);
        }
    }
    
    private class RestartAction implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            int choice = showRestartDialog();
            if (choice == JOptionPane.YES_OPTION)
                myController.restart();
        }
    }
}
