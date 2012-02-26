package tivoo.output;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.elements.*;
import tivoo.Event;

public class CalendarViewHTMLBuilder extends HTMLBuilder
{
    
    private static final String TITLE = "Calendar View";
    private static final String UNIQUE_CSS = "../css/calendarStyle.css";
    
    private static final long dayLength = 86400000L;
    private static final long weekLength = 604800000L;
    
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
//        Div content = new Div().setCSSClass("content");     
//        content.appendChild(new H3().appendText(getTitle()));
//        content.appendChild(buildCalendar(eventList));
//        doc.body.appendChild(content);
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
    
//    private Table buildCalendar (List<Event> eventList)
//    {
//        Table view = initializeCalendar();
//        Type calendarType = determineCalendarType(eventList);
//        Map<String, List<Event>> sortedEvents = sortByDate(eventList);
//        if (calendarType.equals(Type.DAY))
//        {
//            String day = getDay(eventList.get(0));
//            view.appendChild(buildDayCalendar(day, sortedEvents));
//        }
//        else if (calendarType.equals(Type.WEEK))
//        {
//            buildWeekCalendar(view, sortedEvents);
//        }
//        else
//        {
//            buildMonthCalendar(view, sortedEvents);
//        }
//        return view;
//    }
//
//    private Table initializeCalendar ()
//    {
//        Table view = new Table();
//        view.setTitle("Calendar View");
//        view.setCellspacing("0");
//        
//        Tr tableHeading = buildTableHeading();
//        view.appendChild(tableHeading);
//        return view;
//    }
//
//    private Type determineCalendarType (List<Event> eventList)
//    {
//        Event first = eventList.get(0);
//        Event last = eventList.get(0); 
//        for (Event e : eventList)
//        {
//            Calendar start = e.getStartTime();
//            first = (start.getTimeInMillis() < first.getStartTime().getTimeInMillis()) ? e : first;
//            
//            Calendar end = e.getEndTime();
//            last = (end.getTimeInMillis() > last.getEndTime().getTimeInMillis()) ? e : last;
//        }
//        long timeframe = last.getEndTime().getTimeInMillis() - first.getStartTime().getTimeInMillis();
//        if (timeframe <= dayLength)
//        {
//            return CalendarViewHTMLBuilder.Type.DAY;
//        }
//        if (timeframe <= weekLength)
//        {
//            return CalendarViewHTMLBuilder.Type.WEEK;
//        }
//        return CalendarViewHTMLBuilder.Type.MONTH;
//    }
//    
//    private Tr buildTableHeading ()
//    {
//        Tr headingRow = new Tr();
//        for (String day : daysList)
//        {
//            Th heading = new Th().setCSSClass("heading").appendText(day);
//            headingRow.appendChild(heading);
//        }
//        return headingRow;
//    }
//    
//    private Tr buildTableRow (Map<String, List<Event>> sortedEvents)
//    {
//        Tr tableRow = new Tr();
//        for (String day : daysList)
//        {
//            List<Event> eventsOnThisDay = sortedEvents.get(day);
//            Td dayCal = buildDayCalendar(day, eventsOnThisDay);
//            tableRow.appendChild(dayCal);
//        }
//        return tableRow;
//    }
//    
//    private void buildMonthCalendar (Table view, Map<String, List<Event>> sortedEvents)
//    {
//        for (int i = 0; i < 5; i++)
//        {
//            Tr tableRow = buildTableRow(sortedEvents);
//            view.appendChild(tableRow);
//        }
//    }
//    
//    private void buildWeekCalendar (Table view, Map<String, List<Event>> sortedEvents2)
//    {        
//        Map<String, List<Event>> sortedEvents = sortByDayOfWeek(sortedEvents2);
//        Tr tableRow = buildTableRow(sortedEvents);
//        view.appendChild(tableRow);
//    }
//
//    private Td buildDayCalendar (String day, Map<String, List<Event>> sortedEvents)
//    {
//        boolean columnClass = (daysList.indexOf(day) % 2 == 0);
//        Td dayCal = columnClass ? new Td().setCSSClass("gr1") : new Td().setCSSClass("gr1alt");
//        Div dayDiv = constructDayDiv(sortedEvents);
//        dayCal.appendChild(dayDiv);
//        return dayCal;
//    }
//
//    private Div constructDayDiv (List<Event> eventsOnThisDay)
//    {
//        Div dayEvents = new Div();
//        dayEvents.setId("dayEvents");
//        if (eventsOnThisDay != null)
//        {
//            for (Event currentEvent : eventsOnThisDay)
//            {
//                Div eventInfo = constructEventDiv(currentEvent);
//                dayEvents.appendChild(eventInfo);
//            }
//        }
//        return dayEvents;
//    }
//    
}
