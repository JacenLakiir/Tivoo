package tivoo.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import tivoo.TivooSystem;


public class View extends JFrame
{
    private final static String TITLE = "Tivoo";
    private final static String TMP_DIR = "tmp";
    private final static String[] OUTPUT_CHOICES =
        {
                "Horizontal View",
                "Vertical View",
                "Calendar View",
                "Sorted View",
                "Conflict View" };

    private TivooSystem model;
    private JEditorPane page;


    public View (TivooSystem _model, Dimension size) throws IOException
    {
        model = _model;
        setTitle(TITLE);
        setPreferredSize(size);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // load GUI elements
        page = makePage();
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(page), BorderLayout.CENTER);
        contentPane.add(makeControls(), BorderLayout.SOUTH);
        pack();
    }


    private JEditorPane makePage () throws IOException
    {
        final JEditorPane pagePane = new JEditorPane();
        pagePane.setEditable(false);
        pagePane.addHyperlinkListener(new HyperlinkListener()
        {
            @Override
            public void hyperlinkUpdate (HyperlinkEvent evt)
            {
                if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
                {
                    try
                    {
                        pagePane.setPage((evt.getURL().toString()));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        return pagePane;
    }


    private JPanel makeControls ()
    {
        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        controls.add(makeInputControls());
        //controls.add(makeFilteringControls());
        //controls.add(makeSortingControls());
        controls.add(makeOutputControls());
        return controls;
    }


    private JPanel makeInputControls ()
    {
        JPanel row = new JPanel();

        JButton inputButton = new JButton("Load events from XML file");
        inputButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent dummy)
            {
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = chooser.getSelectedFile();
                    try
                    {
                        model.loadFile(file.getPath());
                        System.out.printf("Loaded events from %s\n",
                                          file.getPath());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        row.add(inputButton);

        JButton clearButton = new JButton("Clear events");
        clearButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent dummy)
            {
                model.clearEvents();
                System.out.println("Cleared events");
            }
        });
        row.add(clearButton);

        return row;
    }


    private JPanel makeFilteringControls ()
    {
        //filterByKeyword (String keyword, boolean inEvent)
        //filterByKeyword (String attribute, String keyword)
        //filterByTimeFrame (Calendar startTime, Calendar endTime)
        return null;
    }


    private JPanel makeSortingControls ()
    {
        //TODO: sort
        //sortByTitle (boolean reversed)
        //sortByStartTime (boolean reversed)
        //sortByEndTime (boolean reversed)
        return null;
    }


    private JPanel makeOutputControls ()
    {
        JPanel row = new JPanel();

        JButton previewButton = new JButton("Preview");
        row.add(previewButton);

        JButton saveButton = new JButton("Save");
        row.add(saveButton);

        JComboBox outputComboBox = new JComboBox(OUTPUT_CHOICES);
        //outputHorizontalView (String summaryPageFileName)
        //outputVerticalView (String summaryPageFileName)
        //outputSortedView (String summaryPageFileName)
        //outputConflictView (String summaryPageFileName)
        //outputCalendarView (String summaryPageFileName)

        return row;
    }
}
