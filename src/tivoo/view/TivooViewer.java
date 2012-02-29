package tivoo.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


import tivoo.TivooSystem;

@SuppressWarnings("serial")
public class TivooViewer extends JPanel{
    public static final Dimension SIZE = new Dimension(800, 600);
    
	private JEditorPane page;
	private JButton myLoad;
	private JButton myPreview;
	private TivooSystem myModel;
	private static final String filePath = "output/calendar.html";
	private JButton myBack;
	private JButton filterKey;
	private JButton sortTitle;
	
	public TivooViewer(TivooSystem model){
		myModel = model;
		setLayout(new BorderLayout());
	    add(operationPanel(), BorderLayout.NORTH);
	    add(display(), BorderLayout.CENTER);
	    myLoad.setEnabled(true);
	    myPreview.setEnabled(true);
	}
	
	private JComponent operationPanel(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(function(), BorderLayout.NORTH);
		panel.add(filterPanel(), BorderLayout.SOUTH);
		return panel;
	}
	
	private JComponent filterPanel(){
		JPanel panel = new JPanel();
		filterKey = new JButton("FilterByKeyWord");
		filterKey.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String inputValue = JOptionPane.showInputDialog("Please input a keyword");
				myModel.filterByKeyword(inputValue, true);
			}
			
		});
	    sortTitle = new JButton("SortByTitle");
	    sortTitle.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				myModel.sortByTitle(true);
			}
	    	
	    });
		panel.add(sortTitle);
		panel.add(filterKey);
		return panel;
	}
	
	private JComponent function(){
		JPanel panel = new JPanel();
		myLoad = new JButton("Load Files");
		
		myLoad.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
		        JFileChooser fc = new JFileChooser();
		        int returnVal = fc.showOpenDialog(null);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            try {
						myModel.loadFile(file.getPath());
					} catch (SAXException | IOException
							| ParserConfigurationException e1) {
						e1.printStackTrace();
					}
		        }
			}
			
		});
		panel.add(myLoad);
		
		
		myPreview = new JButton("Preview");
		myPreview.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					myModel.outputCalendarView(filePath);
					showURL();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
 			
		});
		panel.add(myPreview);
		myBack = new JButton("Back");
		myBack.setEnabled(false);

		myBack.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				showURL();
			}
			
		});
		panel.add(myBack);
		return panel;
	}

	public void showURL(){
		try {
			File file = new File(filePath);
			URL url = new URL("file://" + file.getAbsolutePath());
			Document doc = page.getDocument();
			doc.putProperty(Document.StreamDescriptionProperty, null);
			page.validate();
			page.setPage(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private JComponent display ()
	{
		
		page = new JEditorPane();
		page.setPreferredSize(SIZE);
		page.addHyperlinkListener(new LinkFollower());
	    page.setEditable(false);
		return new JScrollPane(page);
	}
	
    private class LinkFollower implements HyperlinkListener
    {
        public void hyperlinkUpdate (HyperlinkEvent evt)
        {
            if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
            {
                try
                {	
                	myBack.setEnabled(true);
                    page.setPage((evt.getURL().toString()));
                }
                catch (Exception e)
                {
                    String s = evt.getURL().toString();
                    JOptionPane.showMessageDialog(TivooViewer.this,
                            "loading problem for " + s + " " + e,
                            "Load Problem", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
	
	
	public static void main(String[] args){
		TivooSystem model = new TivooSystem();
		TivooViewer display = new TivooViewer(model);
        JFrame frame = new JFrame("Tivoo");
        frame.getContentPane().add(display);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

}
