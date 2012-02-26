import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import tivoo.TivooSystem;


public class Main
{
    public static void main (String[] args)
        throws SAXException,
            IOException,
            ParserConfigurationException
    {
        TivooSystem s = new TivooSystem();

        System.out.println("Loading XML input...");
        s.loadFile("data/DukeBasketBall.xml");

        System.out.println("Filtering events...");
        s.filterByKeyword("Duke");
        s.sortByStartTime(false);

        System.out.println("Creating HTML output...");
        s.outputHorizontalWeekView("output/horiz_summary.html");
        s.outputVerticalWeekView("output/vert_summary.html");
        s.outputSortedView("output/sorted.html");

        System.out.println("Done!");
    }
}
