package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class InputPanel extends JPanel
{
    private static final String TITLE = "Input";
    private static final Dimension SIZE = new Dimension(300, 250);
    private static final String INPUT_DIR = System.getProperties().getProperty("user.dir") + "/data";

    private JTextPane mySelectedFiles;
    private JButton myChooseFileButton;
    private JButton myClearButton;
    
    private List<String> myInput;
    
    protected InputPanel ()
    {
        myInput = new ArrayList<String>();
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), TITLE));
        setPreferredSize(SIZE);

        add(makeInputPanel(), BorderLayout.CENTER);
        add(makeOptionPanel(), BorderLayout.SOUTH);
        
        enableButtons();
    }
    
    protected List<String> getInput ()
    {
        return Collections.unmodifiableList(myInput);
    }    
    
    private JComponent makeInputPanel ()
    {
        mySelectedFiles = new JTextPane();
        mySelectedFiles.setEditable(false);
        return new JScrollPane(mySelectedFiles);
    }
    
    private JComponent makeOptionPanel ()
    {
        JPanel panel = new JPanel();

        myChooseFileButton = new JButton("Load XML File");
        myChooseFileButton.addActionListener(new ChooseFileAction());
        panel.add(myChooseFileButton);
        
        myClearButton = new JButton("Clear");
        myClearButton.addActionListener(new ClearAction());
        panel.add(myClearButton);
        return panel;
    }

    private void enableButtons ()
    {
        myChooseFileButton.setEnabled(true);
        myClearButton.setEnabled(myInput.size() > 0);
    }

    private void chooseFile ()
    {
        FileFilter xmlFilter =
                new FileNameExtensionFilter("XML file", "xml", "XML", "Xml", "xMl", "xmL", "XMl", "XmL", "xML");
        JFileChooser fileChooser = new JFileChooser(INPUT_DIR);
        fileChooser.setFileFilter(xmlFilter);
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION)
        {
            updateSelectedInput(fileChooser.getSelectedFile());
        }
    }
    
    private void updateSelectedInput (File file)
    {
        String filepath = file.getAbsolutePath();
        if (!myInput.contains(filepath))
        {
            myInput.add(filepath);
            mySelectedFiles.setText(mySelectedFiles.getText() + "\n" + file.getName());
        }
    }

    private class ChooseFileAction implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            chooseFile();
            enableButtons();
        }
    }   
    
    private class ClearAction implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            myInput.clear();
            mySelectedFiles.setText("");
            enableButtons();
        }
    }  
    
}
