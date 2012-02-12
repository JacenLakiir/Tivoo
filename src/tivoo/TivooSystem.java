package tivoo;

import java.util.List;
import tivoo.filtering.Filter;
import tivoo.filtering.FilterFactory;
import tivoo.input.XMLParser;
import tivoo.input.XMLParserFactory;

public class TivooSystem
{
    private List<Event> myEvents;
    
    public void loadFile (String fileName) { 
        XMLParser parser = XMLParserFactory.getParser();
        myEvents = parser.parse(fileName);
    }
    
    public void filterByKeyword(String keyword)
    {
        Filter filter = FilterFactory.getFilter();
        myEvents = filter.filterByKeyword(keyword, myEvents);
    }
    
    public void outputSummaryAndDetailsPages(String summaryFileName, String detailPageDirectory)
    {
        return;
    }
    
}
