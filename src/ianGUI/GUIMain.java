package ianGUI;

import javax.swing.JFrame;

public class GUIMain {

	public static void main (String[] args){
		GUIModel model = new GUIModel();
		GUIViewer display = new GUIViewer(model);
		// create container that will work with Window manager
        JFrame frame = new JFrame("Tivoo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add our user interface components to Frame and show it
        frame.getContentPane().add(display);
        frame.pack();
        frame.setVisible(true);
	}
}
