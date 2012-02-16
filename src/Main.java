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

        System.out.println("Filtering XML...");
        s.filterByKeyword("Lecture");

        System.out.println("Creating HTML output...");
        s.outputSummaryAndDetailsPages("output/summary.html",
                                       "output/details_dir");

        System.out.println("Done!");
    }
}
