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
    public List<Event> filterByKeyword (String keyword, List<Event> current){
    	List<Event> newList = new ArrayList<Event>();
    	for(Event event : current){
    		if(event.getTitle().contains(keyword)){
    			newList.add(event);
    		}
    	}
    	return newList;
    }
        
    /**
     * Finds all events for a given week.
     */
    public List<Event> filterByTimeFrame (Calendar starttime, Calendar endtime, List<Event> current){
    	return null;
    }
}
