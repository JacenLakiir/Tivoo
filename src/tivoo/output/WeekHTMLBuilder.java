package tivoo.output;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.*;
import tivoo.Event;

public abstract class WeekHTMLBuilder extends HTMLBuilder
{
    
    private static final String TITLE = "Week View";
    private static final String UNIQUE_CSS = "";

    protected String mySummaryPageFileName;
    protected String myDetailPageDirectory;
            
    protected WeekHTMLBuilder(String summaryPageFileName)
    {
        super(summaryPageFileName);
    }
    
    @Override
    protected void writeSummaryPageContent (Document doc, List<Event> eventList)
    {
        Div content = new Div().setCSSClass("content");     
        content.appendChild(new H3().appendText(getTitle()));
        content.appendChild(buildWeekCalendar(eventList));
        doc.body.appendChild(content);
    }
    
    @Override
    protected String getTitle ()
    {
        return TITLE;
    }
    
    @Override
    protected String getUniqueCSS ()
    {
        return UNIQUE_CSS;
    }
    
    protected abstract Node buildWeekCalendar (List<Event> eventList);
   
    protected Div constructWeekEventDiv (Event currentEvent)
    {
        Div eventInfo = new Div().setId("event");
        
        P eventP = new P();
        eventP.appendChild(linkToDetailsPage(DETAIL_PAGE_FOLDER, currentEvent));
        eventP.appendChild(new Br());
        eventP.appendText(formatDateTimespan(currentEvent));
        
        eventInfo.appendChild(eventP);
        return eventInfo;
    }

    protected Map<String, List<Event>> sortByDayOfWeek (List<Event> eventList)
    {
        Map<String, List<Event>> sortedEvents = new TreeMap<String, List<Event>>();
        for (Event currentEvent : eventList)
        {
            String eventDay = getDayOfWeek(currentEvent);
            if (!sortedEvents.containsKey(eventDay))
                sortedEvents.put(eventDay, new ArrayList<Event>());
            sortedEvents.get(eventDay).add(currentEvent);
        }
        return sortedEvents;
    }
   
}
