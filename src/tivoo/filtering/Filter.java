package tivoo.filtering;

import tivoo.Event;
import java.util.List;

public interface Filter
{
    
    /**
     * Finds all events with the given keyword in its title.
     */
    List<Event> filterByKeyword (String keyword, List<Event> current);
        
    /**
     * Finds all events for a given week.
     */
    List<Event> filterByWeek (String week, List<Event> current);
}
