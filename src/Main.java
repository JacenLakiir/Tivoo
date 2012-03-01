import ianGUI.*;

import javax.swing.JFrame;

public class Main
{
	public static void main (String[] args){
		Model model = new Model();
		Viewer display = new Viewer(model);
		// create container that will work with Window manager
        JFrame frame = new JFrame("Tivoo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add our user interface components to Frame and show it
        frame.getContentPane().add(display);
        frame.pack();
        frame.setVisible(true);
	}
}
