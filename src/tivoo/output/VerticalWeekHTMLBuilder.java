package tivoo.output;

import java.util.List;
import java.util.Map;
import tivoo.Event;
import com.hp.gagawa.java.elements.*;


public class VerticalWeekHTMLBuilder extends WeekHTMLBuilder
{

    private static final String TITLE = "Vertical Week View";
    private static final String UNIQUE_CSS = "../css/verticalWeekStyle.css";
    
    public VerticalWeekHTMLBuilder (String summaryPageFileName)
    {
        super(summaryPageFileName);
    }

    @Override
    protected Div buildWeekCalendar (List<Event> eventList)
    {
        Div weekView = new Div().setId("verticalView");
        Map<String, List<Event>> sortedEvents = sortByDayOfWeek(eventList);
        for (String day : daysList)
        {
            List<Event> eventsOnThisDay = sortedEvents.get(day);
            Div dayInfo = constructDayDiv(day, eventsOnThisDay);
            weekView.appendChild(dayInfo);
        }
        
        return weekView;
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

    private Div constructDayDiv (String day, List<Event> eventsOnThisDay)
    {
        Div dayInfo = new Div().setCSSClass("day");
        dayInfo.appendChild(new Hr());
        dayInfo.appendChild(new H4().appendText(day));
        if (eventsOnThisDay != null)
        {
            for (Event currentEvent : eventsOnThisDay)
            {
                Div eventInfo = constructEventDiv(currentEvent);
                dayInfo.appendChild(eventInfo);
            }
        }
        else
        {
            dayInfo.appendChild(new P().appendText("None"));
        }
        return dayInfo;
    }
    
}
