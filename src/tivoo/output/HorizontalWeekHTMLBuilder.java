package tivoo.output;

import java.util.List;
import java.util.Map;
import tivoo.Event;
import com.hp.gagawa.java.elements.*;


public class HorizontalWeekHTMLBuilder extends WeekHTMLBuilder
{
    
    private static final String TITLE = "Horizontal Week View";
    private static final String UNIQUE_CSS = "../css/horizontalWeekStyle.css";

    public HorizontalWeekHTMLBuilder (String summaryPageFileName)
    {
        super(summaryPageFileName);
    }

    @Override
    protected Table buildWeekCalendar (List<Event> eventList)
    {
        Table weekView = new Table();
        weekView.setTitle("horizontalView");
        weekView.setCellspacing("0");
        
        Tr tableHeading = buildTableHeading();
        weekView.appendChild(tableHeading);
        
        Map<String, List<Event>> sortedEvents = sortByDayOfWeek(eventList);
        Tr tableRow = buildTableRow(sortedEvents);
        weekView.appendChild(tableRow);
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
        boolean columnClass = (daysList.indexOf(day) % 2 == 0);
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
