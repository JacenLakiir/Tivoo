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
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


import tivoo.TivooSystem;

@SuppressWarnings("serial")
public class TivooViewer extends JPanel{
    public static final Dimension SIZE = new Dimension(800, 600);
    
	private JEditorPane page;
	private JLabel myStatus;
	private JButton myLoad;
	private JButton myPreview;
	private JComboBox myFilter;
	private JButton myDisplay;
	private JButton myAdd;
	private String[] filters = {"Please select a filter","FilterByKeyWord", "SortByStartTime", "SortByTitle"};
	private TivooSystem myModel;
	
	public TivooViewer(TivooSystem model){
		myModel = model;
		setLayout(new BorderLayout());
	    add(function(), BorderLayout.NORTH);
	    add(display(), BorderLayout.CENTER);
	    myLoad.setEnabled(true);
	    myPreview.setEnabled(true);
	}
	
	private JComponent function(){
		JPanel panel = new JPanel();
		myLoad = new JButton("Load Files");
		myAdd = new JButton("Add");
		myAdd.setEnabled(false);
		myLoad.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
		        JFileChooser fc = new JFileChooser();
		        int returnVal = fc.showOpenDialog(null);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            try {
						myModel.loadFile(file.getPath());
						myAdd.setEnabled(true);
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
					myModel.outputCalendarView("output/calendar.html");
					showURL();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
 			
		});
		panel.add(myPreview);
		myFilter = new JComboBox(filters);
		myFilter.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				
					String str = e.getItem().toString();
					if(str.equals(filters[1])){
						String inputValue = JOptionPane.showInputDialog("Please input a keyword");
						myModel.filterByKeyword(inputValue);
					}
					else if(str.equals(filters[2]))
						myModel.sortByStartTime(true);
					else if(str.equals(filters[3]))
						myModel.sortByTitle(true);
				
			}
			
		});
		panel.add(myFilter);
		return panel;
	}

	public void showURL(){
		try {
			page.setPage("file:///home/ChristD/workspace/Tivoo/output/calendar.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private JComponent display ()
	{
		
        // displays the web page
		page = new JEditorPane();
		page.setPreferredSize(SIZE);
	
        // allow editor to respond to link-clicks/mouse-overs
        page.setEditable(false);
		return new JScrollPane(page);
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
