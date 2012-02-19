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
        s.loadFile("data/tv.xml");

        System.out.println("Filtering events...");
        s.filterByKeyword("Hollywood");
        //s.sortByTitle(false);
        s.sortByStartTime(false);

        System.out.println("Creating HTML output...");
        s.outputHorizontalWeekView("output/horiz_summary.html", "output/horiz_dir");
        s.outputVerticalWeekView("output/vert_summary.html", "output/vert_dir");
        s.outputSortedView("output/sorted.html");

        System.out.println("Done!");
    }
}
