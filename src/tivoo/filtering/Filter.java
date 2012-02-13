package tivoo.filtering;

import tivoo.Event;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Filter
{

    /**
     * Finds all events with the given keyword in its title.
     */
    public List<Event> filterByKeyword (String keyword, List<Event> eventList)
    {
        List<Event> newEventList = new ArrayList<Event>();
        for (Event event : eventList)
        {
            if (event.getTitle().contains(keyword))
            {
                newEventList.add(event);
            }
        }
        return newEventList;
    }


    /**
     * Finds all events for a given timeframe.
     */
    public List<Event> filterByTimeFrame (Calendar startTime,
                                          Calendar endTime,
                                          List<Event> eventList)
    {
        List<Event> newEventList = new ArrayList<Event>();
        for (Event event : eventList)
        {
            if (event.getStartTime().after(startTime) &&
                event.getEndTime().before(endTime))
            {
                newEventList.add(event);
            }
        }
        return newEventList;
    }
}
