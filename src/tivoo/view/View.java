package tivoo.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import tivoo.TivooSystem;


public class View extends JFrame
{
    private final static String TITLE = "Tivoo";
    private final static String TMP_DIR = "tmp";
    private final static String TMP_FILE_NAME = TMP_DIR + "/tmp.html";

    private File tmpDir;
    private File tmpFile;
    private TivooSystem model;
    private JEditorPane page;


    public View (TivooSystem _model, Dimension size) throws IOException
    {
        model = _model;
        setTitle(TITLE);
        setPreferredSize(size);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // manage tmp files
        createTmpFiles();
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing (WindowEvent e)
            {
                removeTmpFiles();
            }
        });

        // load GUI elements
        page = makePage();
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(page), BorderLayout.CENTER);
        contentPane.add(makeControls(), BorderLayout.SOUTH);
        pack();
    }


    /*
     * The following methods handle the temporary file directory.
     */

    private void createTmpFiles ()
    {
        tmpDir = new File(TMP_DIR);
        tmpDir.mkdirs();
        tmpFile = new File(TMP_FILE_NAME);
    }


    private void removeTmpFiles ()
    {
        recursiveDelete(tmpDir);
    }


    private void recursiveDelete (File f)
    {
        if (f.isDirectory())
        {
            for (File c : f.listFiles())
                recursiveDelete(c);
        }
        f.delete();
    }


    /*
     * The following methods create the interface.
     */

    private JEditorPane makePage () throws IOException
    {
        final JEditorPane pagePane = new JEditorPane();
        pagePane.setEditable(false);
        pagePane.addHyperlinkListener(new HyperlinkListener()
        {
            @Override
            public void hyperlinkUpdate (HyperlinkEvent e)
            {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
                {
                    try
                    {
                        pagePane.setPage(e.getURL());
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
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
        controls.add(makeFilteringControls());
        controls.add(makeSortingControls());
        controls.add(makeOutputControls());
        return controls;
    }


    private JPanel makeInputControls ()
    {
        JButton inputButton = new JButton("Load events from XML file");
        inputButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = chooser.getSelectedFile();
                    try
                    {
                        model.loadFile(file.getPath());
                        System.out.printf("Loaded events from %s.\n",
                                          file.getPath());
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JButton clearButton = new JButton("Clear events");
        clearButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                model.clearEvents();
                System.out.println("Cleared events.");
            }
        });

        JPanel row = new JPanel();
        row.add(inputButton);
        row.add(clearButton);
        return row;
    }


    private JPanel makeFilteringControls ()
    {
        JButton filterByKeywordButton = new JButton("Keyword filter");
        filterByKeywordButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                JTextField keywordField = new JTextField();
                JOptionPane.showMessageDialog(null,
                                              keywordField,
                                              "Enter keyword",
                                              JOptionPane.PLAIN_MESSAGE);
                model.filterByKeyword(keywordField.getText(), true);
                System.out.printf("Found events containing keyword %s.\n",
                                  keywordField.getText());
            }
        });

        JButton filterByKeywordNegButton =
            new JButton("Negative keyword filter");
        filterByKeywordNegButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                JTextField keywordField = new JTextField();
                JOptionPane.showMessageDialog(null,
                                              keywordField,
                                              "Enter keyword",
                                              JOptionPane.PLAIN_MESSAGE);
                model.filterByKeyword(keywordField.getText(), false);
                System.out.printf("Found events not containing keyword %s.\n",
                                  keywordField.getText());
            }
        });

        JButton filterByAttributeButton = new JButton("Attribute filter");
        filterByAttributeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                JTextField attributeField = new JTextField();
                JTextField valueField = new JTextField();
                Object[] inputOptions =
                    { "Attribute name:", attributeField, "Value:", valueField };
                JOptionPane.showMessageDialog(null,
                                              inputOptions,
                                              "Enter options",
                                              JOptionPane.PLAIN_MESSAGE);
                model.filterByKeyword(attributeField.getText(),
                                      valueField.getText());
                System.out.printf("Found events with attribute %s=%s.\n",
                                  attributeField.getText(),
                                  valueField.getText());
            }
        });

        JButton filterByTimeFrameButton = new JButton("Time filter");
        filterByTimeFrameButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                JTextField year0 = new JTextField();
                JTextField month0 = new JTextField();
                JTextField day0 = new JTextField();
                JTextField hour0 = new JTextField();
                JTextField minute0 = new JTextField();
                JTextField second0 = new JTextField();
                JTextField year1 = new JTextField();
                JTextField month1 = new JTextField();
                JTextField day1 = new JTextField();
                JTextField hour1 = new JTextField();
                JTextField minute1 = new JTextField();
                JTextField second1 = new JTextField();
                Object[] inputOptions =
                    {
                            "Start year:",
                            year0,
                            "Start month:",
                            month0,
                            "Start day:",
                            day0,
                            "Start hour:",
                            hour0,
                            "Start minute:",
                            minute0,
                            "Start second:",
                            second0,
                            "End year:",
                            year1,
                            "End month:",
                            month1,
                            "End day:",
                            day1,
                            "End hour:",
                            hour1,
                            "End minute:",
                            minute1,
                            "End second:",
                            second1 };
                JOptionPane.showMessageDialog(null,
                                              inputOptions,
                                              "Enter options",
                                              JOptionPane.PLAIN_MESSAGE);
                Calendar startTime = Calendar.getInstance();
                startTime.set(Integer.parseInt(year0.getText()),
                              Integer.parseInt(month0.getText()),
                              Integer.parseInt(day0.getText()),
                              Integer.parseInt(hour0.getText()),
                              Integer.parseInt(minute0.getText()),
                              Integer.parseInt(second0.getText()));
                Calendar endTime = Calendar.getInstance();
                endTime.set(Integer.parseInt(year1.getText()),
                            Integer.parseInt(month1.getText()),
                            Integer.parseInt(day1.getText()),
                            Integer.parseInt(hour1.getText()),
                            Integer.parseInt(minute1.getText()),
                            Integer.parseInt(second1.getText()));
                model.filterByTimeFrame(startTime, endTime);
                System.out.printf("Found events between %s and %s.\n",
                                  startTime,
                                  endTime);
            }
        });

        JPanel row = new JPanel();
        row.add(filterByKeywordButton);
        row.add(filterByKeywordNegButton);
        row.add(filterByAttributeButton);
        row.add(filterByTimeFrameButton);
        return row;
    }


    private JPanel makeSortingControls ()
    {
        final JCheckBox reverseSortBox = new JCheckBox("Reverse sort");

        JButton sortByTitleButton = new JButton("Sort by title");
        sortByTitleButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                model.sortByTitle(reverseSortBox.isSelected());
                System.out.println("Sorted by title.");
            }
        });

        JButton sortByStartTimeButton = new JButton("Sort by start time");
        sortByStartTimeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                model.sortByStartTime(reverseSortBox.isSelected());
                System.out.println("Sorted by start time.");
            }
        });

        JButton sortByEndTimeButton = new JButton("Sort by end time");
        sortByEndTimeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                model.sortByEndTime(reverseSortBox.isSelected());
                System.out.println("Sorted by end time.");
            }
        });

        JPanel row = new JPanel();
        row.add(sortByTitleButton);
        row.add(sortByStartTimeButton);
        row.add(sortByEndTimeButton);
        row.add(reverseSortBox);
        return row;
    }

    private abstract class OutputFormatHandler
    {
        String name;


        OutputFormatHandler (String _name)
        {
            name = _name;
        }


        public String toString ()
        {
            return name;
        }


        abstract void writeOutput (String fileName) throws IOException;
    }


    private JPanel makeOutputControls ()
    {
        OutputFormatHandler[] outputChoices =
            { new OutputFormatHandler("Horizontal View")
            {
                @Override
                void writeOutput (String fileName) throws IOException
                {
                    model.outputHorizontalView(fileName);
                }
            }, new OutputFormatHandler("Vertical View")
            {
                @Override
                void writeOutput (String fileName) throws IOException
                {
                    model.outputVerticalView(fileName);
                }
            }, new OutputFormatHandler("Calendar View")
            {
                @Override
                void writeOutput (String fileName) throws IOException
                {
                    model.outputCalendarView(fileName);
                }
            }, new OutputFormatHandler("Sorted View")
            {
                @Override
                void writeOutput (String fileName) throws IOException
                {
                    model.outputSortedView(fileName);
                }
            }, new OutputFormatHandler("Conflict View")
            {
                @Override
                void writeOutput (String fileName) throws IOException
                {
                    model.outputConflictView(fileName);
                }
            } };

        final JComboBox outputComboBox = new JComboBox(outputChoices);

        JButton previewButton = new JButton("Preview");
        previewButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                try
                {
                    OutputFormatHandler handler =
                        (OutputFormatHandler) outputComboBox.getSelectedItem();
                    handler.writeOutput(tmpFile.getAbsolutePath());
                    // According to the JavaDoc, this is the only way to refresh the page.
                    Document doc = page.getDocument();
                    doc.putProperty(Document.StreamDescriptionProperty, null);
                    page.setPage(tmpFile.toURI().toURL());
                    System.out.printf("Previewing %s.\n", handler);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogType(JFileChooser.SAVE_DIALOG);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File outFile = chooser.getSelectedFile();
                    try
                    {
                        ((OutputFormatHandler) outputComboBox.getSelectedItem()).writeOutput(outFile.getAbsolutePath());
                        System.out.printf("Saved output to %s.\n",
                                          outFile.getPath());
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JPanel row = new JPanel();
        row.add(previewButton);
        row.add(saveButton);
        row.add(outputComboBox);
        return row;
    }
}
