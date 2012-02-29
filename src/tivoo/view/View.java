package tivoo.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import tivoo.TivooSystem;


public class View extends JFrame
{
	private final static String TITLE = "Tivoo";
	private TivooSystem system;
    private JEditorPane page;
	
	public View (TivooSystem _system, Dimension size) throws IOException
	{
		system = _system;
		setTitle(TITLE);
        setPreferredSize(size);
        getContentPane().setLayout(new BorderLayout());
		setUpPage();
		setUpControls();
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}


	private void setUpPage() throws IOException
	{
		page = new JEditorPane();
        page.setEditable(false);
        page.addHyperlinkListener(new HyperlinkListener()
			{
				public void hyperlinkUpdate (HyperlinkEvent evt)
				{
					if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
					{
						try
						{
							page.setPage((evt.getURL().toString()));
						}
						catch (Exception e)
						{
							String s = evt.getURL().toString();
							JOptionPane.showMessageDialog(View.this,
									"Loading problem for " + s + " " + e,
									"Load Problem", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		);

        JScrollPane pageScroll = new JScrollPane(page);
        getContentPane().add(pageScroll, BorderLayout.CENTER);
	}
    
    
	private void setUpControls()
	{
		/*
	TODO: add XML file, clear events
    loadFile (String fileName)
    clearEvents ()

	TODO: filter
    filterByKeyword (String keyword, boolean inEvent)
    filterByKeyword (String attribute, String keyword)
    filterByTimeFrame (Calendar startTime, Calendar endTime)
    
	TODO: sort
    sortByTitle (boolean reversed)
    sortByStartTime (boolean reversed)
    sortByEndTime (boolean reversed)

	TODO: preview HTML

	TODO: output HTML
    outputHorizontalView (String summaryPageFileName)
    outputVerticalView (String summaryPageFileName)
    outputSortedView (String summaryPageFileName)
    outputConflictView (String summaryPageFileName)
    outputCalendarView (String summaryPageFileName)
    
    public static void main(String[] args) throws IOException {
		JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            HTMLExample foo = new HTMLExample(file.toURI().toString());
        } 
*/
    }
}

/*

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;


public class Frame extends JFrame
{
    // state
    private Model myModel;
    private JLabel myDisplay;
    private JTextField myInput;


    public Frame (String title, Dimension size)
    {
        // create GUI components
        myDisplay = makeDisplay(size);
        myInput = makeInput();

        // add containers to Frame and show it
        getContentPane().add(myDisplay, BorderLayout.CENTER);
        getContentPane().add(new JScrollPane(myInput), BorderLayout.SOUTH);
        setTitle(title);
        pack();
    }


    // Return input area where ENTER evaluates expression.
    protected JTextField makeInput ()
    {
        JTextField result = new JTextField();
        result.setBorder(BorderFactory.createLoweredBevelBorder());
        result.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent evt)
            {
                animateExpression(myInput.getText());
            }
        });
        return result;
    }


    // Return display area for results of expression
    private JLabel makeDisplay (Dimension size)
    {
        JLabel result = new JLabel(new Pixmap(size).toIcon());
        result.setBorder(BorderFactory.createLoweredBevelBorder());
        return result;
    }


    // Evaluate the given input repeatedly to produce an animation
    private void animateExpression (final String text)
    {
        myModel.load(text);
        // generate new pictures to animate
        TimerTask task = new TimerTask()
        {
            private int index = 0;


            @Override
            public void run ()
            {
                try
                {
                    if (index <= Model.NUM_FRAMES)
                    {
                        myDisplay.setIcon(myModel.evaluate(myDisplay.getSize())
                                                 .toIcon());
                        myModel.nextFrame();
                        index++;
                    }
                    else
                    {
                        endAnimation();
                    }
                }
                catch (Exception e)
                {
                    endAnimation();
                    JOptionPane.showMessageDialog(Frame.this,
                                                  e.getMessage(),
                                                  "Input Error",
                                                  JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        // end previous animation if still running
        endAnimation();
        // start new animation
        myTimer = new Timer();
        myTimer.schedule(task, 0, ANIMATION_DELAY);
        myModel.reset();
    }
}
*/
