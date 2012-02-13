package tivoo;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import tivoo.filtering.Filter;
import tivoo.input.CalendarParser;


public class TivooSystem
{
    private List<Event> events;


    public void loadFile (String fileName)
        throws SAXException,
            IOException,
            ParserConfigurationException
    {
        events = CalendarParser.parse(fileName);
    }


    public void filterByKeyword (String keyword)
    {
        Filter filter = new Filter();
        events = filter.filterByKeyword(keyword, events);
    }


    public void filterByTimeFrame (Calendar startTime, Calendar endTime)
    {
        Filter filter = new Filter();
        events = filter.filterByTimeFrame(startTime, endTime, events);
    }


    // TODO: Implement actual output. (Currently only outputs event titles.)
    public void outputSummaryAndDetailsPages (String summaryFileName,
                                              String detailPageDirectory)
    {
        for (Event currentEvent : events)
        {
            Calendar start = currentEvent.getStartTime();
            Calendar end = currentEvent.getEndTime();
            System.out.println(start.get(Calendar.HOUR) + ":" +
                               start.get(Calendar.MINUTE) + " " +
                               end.get(Calendar.HOUR) + ":" +
                               end.get(Calendar.MINUTE));
        }
    }

}
