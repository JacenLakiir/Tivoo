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
    	filter.sortByTitle(events, reversed);
    }
    
    public void sortByStartTime (boolean reversed)
    {
    	Filter filter = new Filter();
    	filter.sortByStartTime(events, reversed);
    }
    
    public void sortByEndTime (boolean reversed)
    {
    	Filter filter = new Filter();
    	filter.sortByEndTime(events, reversed);
    }


    public void outputHorizontalWeekView (String summaryPageFileName) throws IOException
    {
        HTMLBuilder output = new HorizontalWeekHTMLBuilder(summaryPageFileName);
        output.buildHTML(events);
    }
    
    public void outputVerticalWeekView (String summaryPageFileName) throws IOException
    {
        HTMLBuilder output = new VerticalWeekHTMLBuilder(summaryPageFileName);
        output.buildHTML(events);
    }
    
    public void outputSortedView (String summaryPageFileName)
        throws IOException
    {
        HTMLBuilder output = new SortedViewHTMLBuilder(summaryPageFileName);
        output.buildHTML(events);
    }
    
    public void outputConflictView (String summaryPageFileName)
        throws IOException
    {
        HTMLBuilder output = new ConflictViewHTMLBuilder(summaryPageFileName);
        output.buildHTML(events);
    }
    
    public void outputCalendarView (String summaryPageFileName)
        throws IOException
    {
        HTMLBuilder output = new CalendarViewHTMLBuilder(summaryPageFileName);
        output.buildHTML(events);
    }

}
