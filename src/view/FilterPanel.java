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
public class FilterPanel extends JPanel
{
    private static final String TITLE = "Filtering";
    private static final Dimension SIZE = new Dimension(300, 250);
    private static final String[] filtersAndSorts = initializeFiltersAndSorts();
    
    private JTextPane mySelectedFilters;
    private JButton myProcessButton; 
    private JButton myClearButton;
    
    private List<String> myFilters;

    protected FilterPanel ()
    {
        myFilters = new ArrayList<String>();
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), TITLE));
        setPreferredSize(SIZE);
        
        add(makeFilterPanel(), BorderLayout.CENTER);
        add(makeOptionPanel(), BorderLayout.SOUTH);
        
        enableButtons();
    }

    protected List<String> getFilters ()
    {
        return Collections.unmodifiableList(myFilters);
    }  

    private JComponent makeFilterPanel ()
    {
        mySelectedFilters = new JTextPane();
        mySelectedFilters.setEditable(false);
        return new JScrollPane(mySelectedFilters);
    }
    
    private JComponent makeOptionPanel ()
    {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 39));

        myProcessButton = new JButton("Add Filter/Sort");
        myProcessButton.addActionListener(new ProcessAction());
        panel.add(myProcessButton);
        
        myClearButton = new JButton("Clear");
        myClearButton.addActionListener(new ClearAction());
        panel.add(myClearButton);
                
        return panel;
    }
    
    private void enableButtons ()
    {
        myProcessButton.setEnabled(true);
        myClearButton.setEnabled(myFilters.size() > 0);
    }
    
    private void selectFilter ()
    {       
        String filter =
            (String) JOptionPane.showInputDialog(this, "Select a filter or sort:", "Add Filter/Sort",
                                                 JOptionPane.PLAIN_MESSAGE, null, filtersAndSorts,
                                                 "filter by keyword");
        if (filter == null) return;
        if (filter.contains("nonkeyword"))
        {
            String nonkeyword = promptInput("nonkeyword");
            if (nonkeyword == null) return;
            filter += " " + nonkeyword;
        }
        else if (filter.contains("keyword"))
        {
            String keyword = promptInput("keyword");
            if (keyword == null) return;
            filter += " " + keyword;
        }
        updateSelectedFilters(filter);
    }
    
    private String promptInput (String type)
    {
        String defaultMessage = "Type in the " + type + " you wish to filter by:";
        String errorMessage = "Input must be one word in length (no spaces).\n\n" + defaultMessage;
        String title = "Filter by " + type.substring(0, 1).toUpperCase() + type.substring(1);
        String input = "";
        while (isEmpty(input) || isTooLong(input))
        {
            if (isTooLong(input)) 
                input =
                    (String) JOptionPane.showInputDialog(this, errorMessage, title, 
                                                         JOptionPane.PLAIN_MESSAGE,
                                                         null, null, null);
            else
            {
                input = 
                    (String) JOptionPane.showInputDialog(this, defaultMessage, title, 
                                                         JOptionPane.PLAIN_MESSAGE,
                                                         null, null, null);
            }
            if (input == null) return null;
        }
        return input;
    }

    private boolean isTooLong (String input)
    {
        return input.split("\\s+").length > 1;
    }

    private boolean isEmpty (String input)
    {
        return input.length() == 0;
    }
    
    private void updateSelectedFilters (String filter)
    {
        if (!myFilters.contains(filter))
        {
            myFilters.add(filter);
            mySelectedFilters.setText(mySelectedFilters.getText() + "\n" + filter);
        }
    }
    
    private static String[] initializeFiltersAndSorts ()
    {
        String[] filtersAndSorts = new String[]
                { "filter by keyword",
                  "filter by nonkeyword",
                  "sort by title",
                  "sort by start time",
                  "sort by end time",
                  "reverse sort by title",
                  "reverse sort by start time",
                  "reverse sort by end time" };
        return filtersAndSorts;
    }
    
    private class ProcessAction implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            selectFilter();
            enableButtons();
        }
    } 
    
    private class ClearAction implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            myFilters.clear();
            mySelectedFilters.setText("");
            enableButtons();
        }
    }

}
