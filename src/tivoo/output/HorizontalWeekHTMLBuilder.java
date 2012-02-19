package tivoo.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import tivoo.Event;
import com.hp.gagawa.java.*;
import com.hp.gagawa.java.elements.*;


public class HorizontalWeekHTMLBuilder extends WeekHTMLBuilder
{

    public HorizontalWeekHTMLBuilder (String summaryPageFileName, String detailPageDirectory)
    {
        super(summaryPageFileName, detailPageDirectory);
    }
    
    @Override
    protected void writeSummaryPageHTML (File summaryPage,
                                         List<Event> eventList) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(summaryPage);
        OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");

        Document doc = initializeHTMLDocument("Summary", "");
        doc.head.appendChild(insertCSS("../css/horizontalWeekStyle.css"));

        writeHeader(doc);    
        writeSummaryPageContent(eventList, doc);   
        writeFooter(doc);
        
        out.write(doc.write());
        out.close();
    }

    private void writeSummaryPageContent (List<Event> eventList, Document doc)
    {
        Div content = new Div().setCSSClass("content");     
        content.appendChild(new H3().appendText("Horizontal Week View"));
        
        Table weekView = buildWeekCalendar(eventList);   
        content.appendChild(weekView);
        
        doc.body.appendChild(content);
    }

    private Table buildWeekCalendar (List<Event> eventList)
    {
        Table weekView = new Table();
        weekView.setTitle("Week View");
        weekView.setCellspacing("0");
        
        Tr tableHeading = buildTableHeading();
        weekView.appendChild(tableHeading);
        
        Map<String, List<Event>> sortedEvents = sortByDayOfWeek(eventList);
        Tr tableRow = buildTableRow(sortedEvents);
        weekView.appendChild(tableRow);
        return weekView;
    }

    private Tr buildTableHeading ()
    {
        Tr headingRow = new Tr();
        for (String day : daysList)
        {
            Th heading = new Th().setCSSClass("day").appendText(day);
            headingRow.appendChild(heading);
        }
        return headingRow;
    }
    
    private Tr buildTableRow (Map<String, List<Event>> sortedEvents)
    {
        Tr tableRow = new Tr();
        for (String day : daysList)
        {
            List<Event> eventsOnThisDay = sortedEvents.get(day);
            Td dayCal = buildDayCalendar(day, eventsOnThisDay);
            tableRow.appendChild(dayCal);
        }
        return tableRow;
    }

    private Td buildDayCalendar (String day, List<Event> eventsOnThisDay)
    {
        boolean columnClass = daysList.indexOf(day) % 2 == 0;
        Td dayCal = columnClass ? new Td().setCSSClass("gr1") : new Td().setCSSClass("gr1alt");
        Div dayDiv = constructDayDiv(eventsOnThisDay);
        dayCal.appendChild(dayDiv);
        return dayCal;
    }

    private Div constructDayDiv (List<Event> eventsOnThisDay)
    {
        Div dayEvents = new Div();
        dayEvents.setId("dayEvents");
        if (eventsOnThisDay != null)
        {
            for (Event currentEvent : eventsOnThisDay)
            {
                Div eventInfo = constructEventDiv(currentEvent);
                dayEvents.appendChild(eventInfo);
            }
        }
        return dayEvents;
    }
    
}
