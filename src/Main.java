import java.awt.Dimension;
import tivoo.TivooSystem;
import tivoo.view.View;


public class Main
{
    private final static Dimension SIZE = new Dimension(640, 480);


    public static void main (String[] args) throws Exception
    {
        TivooSystem system = new TivooSystem();
        View view = new View(system, SIZE);
        view.setVisible(true);
    }
}
