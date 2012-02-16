package tivoo;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import tivoo.filtering.Filter;
import tivoo.input.CalendarParser;
import tivoo.output.HTMLBuilder;
import tivoo.output.WeekViewHTMLBuilder;


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


    public void outputSummaryAndDetailsPages (String summaryPageFileName,
                                              String detailPageDirectory)
        throws IOException
    {
        HTMLBuilder output = new WeekViewHTMLBuilder();
        output.buildSummaryPage(summaryPageFileName,
                                detailPageDirectory,
                                events);
        output.buildDetailsPages(detailPageDirectory, events);
    }

}
