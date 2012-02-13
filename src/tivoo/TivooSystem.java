package tivoo;

import java.util.Calendar;
import java.util.List;
import tivoo.filtering.Filter;
import tivoo.input.XMLParser;
import tivoo.input.XMLParserFactory;

public class TivooSystem
{
    private List<Event> myEvents;
    
    public void loadFile (String fileName)
	{ 
        XMLParser parser = XMLParserFactory.getParser();
        myEvents = parser.parse(fileName);
    }
    
    public void filterByKeyword(String keyword)
    {
        Filter filter = new Filter();
        myEvents = filter.filterByKeyword(keyword, myEvents);
    }
    
    public void filterByTimeFrame(Calendar startTime, Calendar endTime)
	{
    	Filter filter = new Filter();
    	myEvents = filter.filterByTimeFrame(startTime, endTime, myEvents);
    }
    
    public void outputSummaryAndDetailsPages(String summaryFileName, String detailPageDirectory)
    {
        return;
    }
    
}
