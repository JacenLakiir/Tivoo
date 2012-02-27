package tivoo.output;

import java.util.Calendar;
import java.util.Map;
import java.util.List;
import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.elements.*;
import tivoo.Event;

public class CalendarViewHTMLBuilder extends HTMLBuilder
{
    
    private static final String TITLE = "Calendar View";
    private static final String UNIQUE_CSS = "../css/calendarStyle.css";    
    private static final long DAY_LENGTH = 86400000L;
    private static final long WEEK_LENGTH = 604800000L;
        
    private enum Type
    {
        DAY, WEEK, MONTH
    }
        
    public CalendarViewHTMLBuilder (String summaryPageFileName)
    {
        super(summaryPageFileName);
    }
    
    @Override
    protected void writeSummaryPageContent (Document doc, List<Event> eventList)
    {
        Div content = new Div().setCSSClass("content");     
        content.appendChild(new H3().appendText(TITLE));
        
        Div calendarView = buildCalendar(eventList);
        content.appendChild(calendarView);
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
    
    private Div buildCalendar (List<Event> eventList)
    {
        Div calendarView = new Div().setId("calendarView");
        Type calendarType = determineCalendarType(eventList);
        if (calendarType.equals(Type.MONTH))
        {
            Ul monthCalendar = buildMonthCalendar(eventList);
            calendarView.appendChild(monthCalendar);
        }
        if (calendarType.equals(Type.WEEK))
        {
            Ul weekCalendar = buildWeekCalendar(eventList);
            calendarView.appendChild(weekCalendar);
        }
        if (calendarType.equals(Type.DAY))
        {
            Ul dayCalendar = buildDayCalendar(eventList);
            calendarView.appendChild(dayCalendar);
        }
        return calendarView;
    }
    
    private Type determineCalendarType (List<Event> eventList)
    {
        Event first = eventList.get(0);
        Event last = eventList.get(0); 
        for (Event e : eventList)
        {
            Calendar start = e.getStartTime();
            first = (start.getTimeInMillis() < first.getStartTime().getTimeInMillis()) ? e : first;
            
            Calendar end = e.getEndTime();
            last = (end.getTimeInMillis() > last.getEndTime().getTimeInMillis()) ? e : last;
        }
        long timeframe = last.getEndTime().getTimeInMillis() - first.getStartTime().getTimeInMillis();
        if (timeframe <= DAY_LENGTH)
            return CalendarViewHTMLBuilder.Type.DAY;
        if (timeframe <= WEEK_LENGTH)
            return CalendarViewHTMLBuilder.Type.WEEK;
        return CalendarViewHTMLBuilder.Type.MONTH;
    }

    private Ul buildDayCalendar (List<Event> eventList)
    {
        Ul dayCalendar = new Ul().setId("dayCal");
        for (Event e : eventList)
        {
            Li eventInfo = constructEventItem(e);
            dayCalendar.appendChild(eventInfo);
        }
        return dayCalendar;
    }
    
    private Ul buildWeekCalendar (List<Event> eventList)
    {
        Ul weekCalendar = new Ul().setId("weekCal");
        Map<String, List<Event>> sortedEvents = sortByDayOfWeek(eventList);
        for (String day : DAYS_LIST)
        {
            Ul dayInfo = new Ul().setId("dayInfo").appendChild(new B().appendText(day));
            List<Event> eventsOnThisDay = sortedEvents.get(day);
            if (eventsOnThisDay != null)
            {
                for (Event e : eventsOnThisDay)
                {
                    Li eventInfo = constructEventItem(e);
                    dayInfo.appendChild(eventInfo);
                }
            }
            else
            {
                dayInfo.appendChild(new Li().appendChild(new P().appendText("None")));
            }
            weekCalendar.appendChild(new Li().appendChild(dayInfo));
        }
        return weekCalendar;
    }
    
    private Ul buildMonthCalendar (List<Event> eventList)
    {
        Ul monthCalendar = new Ul().setId("monthCal");
        Map<String, List<Event>> sortedEvents = sortByDate(eventList);
        for (Event e: eventList)
        {
            String date = getDate(e);
            Ul dateInfo = new Ul().setId("dateInfo").appendChild(new B().appendText(date));
            List<Event> eventsOnThisDate = sortedEvents.get(date);
            if (eventsOnThisDate != null)
            {
                for (Event currentEvent : eventsOnThisDate)
                {
                    Li eventInfo = constructEventItem(currentEvent);
                    dateInfo.appendChild(eventInfo);
                }
            }
            else
            {
                dateInfo.appendChild(new Li().appendChild(new P().appendText("None")));
            }
            monthCalendar.appendChild(new Li().appendChild(dateInfo));
        }
        return monthCalendar;
    }
    
    private Li constructEventItem (Event currentEvent)
    {
        Li eventInfo = new Li().setId("event");
        
        P eventP = new P();
        eventP.appendChild(linkToDetailsPage(currentEvent));
        eventP.appendChild(new Br());
        eventP.appendText(formatClockTimespan(currentEvent));
        
        eventInfo.appendChild(eventP);
        return eventInfo;
    }
    
}
