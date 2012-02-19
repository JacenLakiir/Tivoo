package tivoo;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import tivoo.filtering.Filter;
import tivoo.input.CalendarParser;
import tivoo.output.CalendarViewHTMLBuilder;
import tivoo.output.ConflictViewHTMLBuilder;
import tivoo.output.HTMLBuilder;
import tivoo.output.HorizontalWeekHTMLBuilder;
import tivoo.output.SortedViewHTMLBuilder;
import tivoo.output.VerticalWeekHTMLBuilder;


public class TivooSystem
{
    private List<Event> events = new LinkedList<Event>();


    public void loadFile (String fileName)
        throws SAXException,
            IOException,
            ParserConfigurationException
    {
        events.addAll(CalendarParser.parse(fileName));
    }


    public void clearEvents ()
    {
        events.clear();
    }


    public void filterByKeyword (String keyword)
    {
        Filter filter = new Filter();
        events = filter.filterByKeyword(keyword, events);
    }
    
    public void filterByKeyword (String attribute, String keyword)
    {
    	Filter filter = new Filter();
    	events = filter.filterByKeyword(attribute, keyword, events);
    }

    public void filterByNonKeyword (String keyword)
    {
    	Filter filter = new Filter();
    	events = filter.filterByNonKeyword(keyword, events);
    }

    public void filterByTimeFrame (Calendar startTime, Calendar endTime)
    {
        Filter filter = new Filter();
        events = filter.filterByTimeFrame(startTime, endTime, events);
    }
    
    public void sortByTitle (boolean reversed)
    {
    	Filter filter = new Filter();
    	int revValue = 1;
    	if (reversed) revValue = -1;
    	filter.sortByTitle(events, revValue);
    }
    
    public void sortByStartTime (boolean reversed)
    {
    	Filter filter = new Filter();
    	int revValue = 1;
    	if (reversed) revValue = -1;
    	filter.sortByStartTime(events, revValue);
    }
    
    public void sortByEndTime (boolean reversed)
    {
    	Filter filter = new Filter();
    	int revValue = 1;
    	if(reversed) revValue = -1;
    	filter.sortByEndTime(events, revValue);
    }


    public void outputHorizontalWeekView (String summaryPageFileName,
                                          String detailPageDirectory)
        throws IOException
    {
        HTMLBuilder output = new HorizontalWeekHTMLBuilder(summaryPageFileName,
                                                           detailPageDirectory);
        output.buildHTML(events);
    }
    
    public void outputVerticalWeekView (String summaryPageFileName,
                                        String detailPageDirectory)
        throws IOException
    {
        HTMLBuilder output = new VerticalWeekHTMLBuilder(summaryPageFileName,
                                                         detailPageDirectory);
        output.buildHTML(events);
    }
    
    public void outputSortedView (String pageFileName)
        throws IOException
    {
        HTMLBuilder output = new SortedViewHTMLBuilder(pageFileName);
        output.buildHTML(events);
    }
    
    public void outputConflictView (String pageFileName)
        throws IOException
    {
        HTMLBuilder output = new ConflictViewHTMLBuilder(pageFileName);
        output.buildHTML(events);
    }
    
    public void outputCalendarView (String pageFileName)
        throws IOException
    {
        HTMLBuilder output = new CalendarViewHTMLBuilder(pageFileName);
        output.buildHTML(events);
    }

}
