import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tivoo.TivooSystem;


public class Main
{
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException
    {
        TivooSystem s = new TivooSystem();
        s.loadFile("data/dukecal.xml");
        //s.filterByKeyword("Lemur");
        s.outputSummaryAndDetailsPages("data/summary.html", "data/details_dir");
    }
}
