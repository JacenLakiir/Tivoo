import javax.swing.JFrame;

import tivoo.view.TivooViewer;


public class GUIMain {
	public static void main(String[] args){
		TivooViewer display = new TivooViewer();
        JFrame frame = new JFrame("Tivoo");
        frame.getContentPane().add(display);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
