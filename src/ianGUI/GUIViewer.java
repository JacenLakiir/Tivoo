package ianGUI;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.event.*;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class GUIViewer extends JPanel {
	// constants
	public static final Dimension SIZE = new Dimension(800, 600);
	public static final String BLANK = " ";

	// web page
	private JEditorPane myPage;
	// information area
	private JLabel myStatus;
	// File location for output
	private File myFile;
	// filtering and display
	private JButton myLoadButton, myKeywordButton, myTimeButton,
			myAttributeButton, myTitleButton, myStarttimeButton,
			myEndtimeButton, myDisplayButton, myBackButton;
	private boolean myDisplayStatus;
	private boolean myLoadStatus = true;
	// the real worker
	protected GUIModel myModel;

	public GUIViewer(GUIModel model) {
		myModel = model;
		// add components to frame
		setLayout(new BorderLayout());
		// must be first since other panels may refer to page
		add(makePageDisplay(), BorderLayout.CENTER);
		add(makeInputPanel(), BorderLayout.NORTH);
		add(makeInformationPanel(), BorderLayout.SOUTH);
	}

	// organize user's options for controlling/giving input to model
	private JComponent makeInputPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(makeLoadDisplayPanel(), BorderLayout.WEST);
		panel.add(makeFilterPanel(), BorderLayout.EAST);
		enableButtons();
		return panel;
	}

	// only enable buttons when useful to user
	private void enableButtons() {
		if (myModel.size() == 0) {
			myDisplayStatus = false;
		}
		myLoadButton.setEnabled(myLoadStatus);
		myDisplayButton.setEnabled(myDisplayStatus);
		myKeywordButton.setEnabled(myDisplayStatus);
		myAttributeButton.setEnabled(myDisplayStatus);
		myTimeButton.setEnabled(myDisplayStatus);
		myTitleButton.setEnabled(myDisplayStatus);
		myStarttimeButton.setEnabled(myDisplayStatus);
		myEndtimeButton.setEnabled(myDisplayStatus);
		if (myPage.getPage() != null
				&& myPage.getPage().toString().contains("details_dir")) {
			myBackButton.setEnabled(true);
		} else {
			myBackButton.setEnabled(false);
		}

	}

	private JComponent makePageDisplay() {
		// displays the web page
		myPage = new JEditorPane();
		myPage.setPreferredSize(SIZE);
		// allow editor to respond to link-clicks/mouse-overs
		myPage.setEditable(false);
		myPage.addHyperlinkListener(new LinkFollower());
		return new JScrollPane(myPage);
	}

	// make load/display buttons.
	private JComponent makeLoadDisplayPanel() {
		JPanel panel = new JPanel();
		myLoadButton = new JButton("Load");
		myLoadButton.addActionListener(new loadAction());
		panel.add(myLoadButton);

		myDisplayButton = new JButton("Display");
		myDisplayButton.addActionListener(new displayAction());
		panel.add(myDisplayButton);

		return panel;
	}

	// make filter buttons.
	private JComponent makeFilterPanel() {
		JPanel panel = new JPanel();
		myKeywordButton = new JButton("Keyword Filter");
		myKeywordButton.addActionListener(new keywordAction());
		panel.add(myKeywordButton);

		myTimeButton = new JButton("Timeframe Filter");
		myTimeButton.addActionListener(new timeAction());
		panel.add(myTimeButton);

		myAttributeButton = new JButton("Attribute Filter");
		myAttributeButton.addActionListener(new attributeAction());
		panel.add(myAttributeButton);

		myTitleButton = new JButton("Title Sort");
		myTitleButton.addActionListener(new titleSortAction());
		panel.add(myTitleButton);

		myStarttimeButton = new JButton("Start-time Sort");
		myStarttimeButton.addActionListener(new startSortAction());
		panel.add(myStarttimeButton);

		myEndtimeButton = new JButton("End-time Sort");
		myEndtimeButton.addActionListener(new endSortAction());
		panel.add(myEndtimeButton);

		myBackButton = new JButton("Back");
		myBackButton.addActionListener(new backAction());
		panel.add(myBackButton);

		return panel;
	}

	// make the panel where "would-be" clicked URL is displayed
	private JComponent makeInformationPanel() {
		// BLANK must be non-empty or status label will not be displayed in GUI

		myStatus = new JLabel(BLANK);
		return myStatus;
	}

	private void load() {
		JFileChooser fc = new JFileChooser();
		File path = new File("data");
		fc.setCurrentDirectory(path);
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				myModel.loadFile(file.toString());
				myDisplayStatus = true;
				enableButtons();
			} catch (SAXException e) {
				showError("File type not recognized\nPlease pick a different file.");
			} catch (IOException e) {
				showError("File type not recognized\nPlease pick a different file.");
			} catch (ParserConfigurationException e) {
				showError("File type not recognized\nPlease pick a different file.");
			} catch (InstantiationException e) {
				showError("File type not recognized\nPlease pick a different file.");
			} catch (IllegalAccessException e) {
				showError("File type not recognized\nPlease pick a different file.");
			}
		}
	}

	private void display() throws OutOfMemoryError{
		JFrame frame = new JFrame();
		int checkWithUser = JOptionPane.showConfirmDialog(frame, "Are you sure you want to display?\nNo more actions can be taken");
		if(checkWithUser == JOptionPane.YES_OPTION){
			myFile = new File("output/summary.html");
			try {
				myModel.outputHorizontalWeekView("output/summary.html",
					"output/details_dir");
				myPage.setPage(myFile.toURI().toURL());
			} catch (IOException e) {
				showError("could not output file.");
			}	
			myDisplayStatus = false;
			myLoadStatus = false;
			enableButtons();
		}
	}

	private void keywordFilter() {
		String keyword = JOptionPane
				.showInputDialog("Enter keyword to filter by : ");
		JFrame frame = new JFrame();
		int inEvent = JOptionPane.showConfirmDialog(frame,
				"Keyword contained in event?");
		if (inEvent == JOptionPane.YES_OPTION) {
			myModel.filterByKeyword(keyword, true);
		} else if (inEvent == JOptionPane.NO_OPTION) {
			myModel.filterByKeyword(keyword, false);
		}
	}

	private void timeFilter() throws NumberFormatException {
		String time = JOptionPane
				.showInputDialog("Enter start time (mm/dd/yyyy hh:mm) :");
		int month = Integer.parseInt(time.substring(0, 2));
		int date = Integer.parseInt(time.substring(3, 5));
		int year = Integer.parseInt(time.substring(6, 10));
		int hour = Integer.parseInt(time.substring(11, 13));
		int minute = Integer.parseInt(time.substring(14, 16));
		Calendar startTime = Calendar.getInstance();
		startTime.set(year, month, date, hour, minute);
		time = JOptionPane
				.showInputDialog("Enter end time (mm/dd/yyyy hh:mm) :");
		month = Integer.parseInt(time.substring(0, 2));
		date = Integer.parseInt(time.substring(3, 5));
		year = Integer.parseInt(time.substring(6, 10));
		hour = Integer.parseInt(time.substring(11, 13));
		minute = Integer.parseInt(time.substring(14, 16));
		Calendar endTime = Calendar.getInstance();
		endTime.set(year, month, date, hour, minute);
		myModel.filterByTimeFrame(startTime, endTime);
	}

	private void attributeFilter() {
		String attribute = JOptionPane
				.showInputDialog("Enter attribute to filter by : ");
		String keyword = JOptionPane
				.showInputDialog("Enter keyword to filter by : ");
		myModel.filterByKeyword(attribute, keyword);
	}

	private void startSort() {
		JFrame frame = new JFrame();
		int reversed = JOptionPane.showConfirmDialog(frame, "Reversed?");
		if (reversed == JOptionPane.YES_OPTION) {
			myModel.sortByStartTime(true);
		} else if (reversed == JOptionPane.NO_OPTION) {
			myModel.sortByStartTime(false);
		}
	}

	private void endSort() {
		JFrame frame = new JFrame();
		int reversed = JOptionPane.showConfirmDialog(frame, "Reversed?");
		if (reversed == JOptionPane.YES_OPTION) {
			myModel.sortByEndTime(true);
		} else if (reversed == JOptionPane.NO_OPTION) {
			myModel.sortByEndTime(false);
		}
	}

	private void titleSort() {
		JFrame frame = new JFrame();
		int reversed = JOptionPane.showConfirmDialog(frame, "Reversed?");
		if (reversed == JOptionPane.YES_OPTION) {
			myModel.sortByTitle(true);
		} else if (reversed == JOptionPane.NO_OPTION) {
			myModel.sortByTitle(false);
		}
	}

	private void back() {

		try {
			myPage
					.setPage("file:/C:/Users/user/workspace/Tivoo/output/summary.html");
			enableButtons();
		} catch (IOException e) {
			showError("couldn't go back");
		}
	}

	private class loadAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFrame frame = new JFrame();
			int clear = JOptionPane.showConfirmDialog(frame,
					"Clear existing data?");
			if (clear == JOptionPane.YES_OPTION) {
				myModel.clearEvents();
			}
			if (clear != JOptionPane.CANCEL_OPTION) load();
		}
	}

	private class displayAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				display();
			} catch (OutOfMemoryError err) {
				showError("Calendar size too large!");
			}
		}
	}

	private class keywordAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			keywordFilter();
		}
	}

	private class timeAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				timeFilter();
			} catch (NumberFormatException n) {
				showError("Input not recognized, please follow format");
			}
		}
	}

	private class attributeAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			attributeFilter();
		}
	}

	private class titleSortAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			titleSort();
		}
	}

	private class startSortAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			startSort();
		}
	}

	private class endSortAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			endSort();
		}
	}

	private class backAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			back();
		}
	}

	/**
	 * Display given message as an error in the GUI.
	 */
	public void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "Browser Error",
				JOptionPane.ERROR_MESSAGE);
	}

	private class LinkFollower implements HyperlinkListener {
		public void hyperlinkUpdate(HyperlinkEvent evt) {
			if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				// user clicked a link, load it and show it
				try {
					myPage.setPage((evt.getURL().toString()));
					enableButtons();
				} catch (Exception e) {
					String s = evt.getURL().toString();
					JOptionPane.showMessageDialog(GUIViewer.this,
							"loading problem for " + s + " " + e,
							"Load Problem", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
