import javax.swing.JFrame;

import tivoo.TivooSystem;
import tivoo.view.TivooViewer;


public class GUIMain {
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
