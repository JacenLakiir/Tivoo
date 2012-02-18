package tivoo.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import tivoo.Event;
import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.elements.*;


public class VerticalWeekHTMLBuilder extends WeekHTMLBuilder
{

    public VerticalWeekHTMLBuilder (String summaryPageFileName, String detailPageDirectory)
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
        doc.head.appendChild(insertCSS("../css/verticalWeekStyle.css"));
        
        writeHeader(doc);    
        writeSummaryPageContent(doc, eventList);
        writeFooter(doc);
        
        out.write(doc.write());
        out.close();
    }

    private void writeSummaryPageContent (Document doc, List<Event> eventList)
    {
        Div content = new Div().setCSSClass("content");     
        content.appendChild(new H3().appendText("Vertical Week View"));
        
        Div weekView = buildWeekHeadings(eventList);
        content.appendChild(weekView);
        
        doc.body.appendChild(content);
    }

    private Div buildWeekHeadings (List<Event> eventList)
    {
        Div weekView = new Div().setId("Week View");
        
        Map<String, List<Event>> sortedEvents = sortByDayOfWeek(eventList);
        for (String day : daysList)
        {
            List<Event> eventsOnThisDay = sortedEvents.get(day);
            Div dayInfo = constructDayDiv(day, eventsOnThisDay);
            weekView.appendChild(dayInfo);
        }
        
        return weekView;
    }

    private Div constructDayDiv (String day, List<Event> eventsOnThisDay)
    {
        Div dayInfo = new Div().setCSSClass("day");
        dayInfo.appendChild(new Hr());
        dayInfo.appendChild(new H4().appendText(day));
        if (eventsOnThisDay != null)
        {
            for (Event currentEvent : eventsOnThisDay)
            {
                Div eventInfo = constructEventDiv(dayInfo, currentEvent);
                dayInfo.appendChild(eventInfo);
            }
        }
        else
        {
            dayInfo.appendText("None");
        }
        return dayInfo;
    }
    
}
