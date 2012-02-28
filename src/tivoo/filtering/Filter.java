package tivoo.filtering;

import tivoo.Event;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Filter {

	
	
	/**
	 * Finds all events without the given keyword.
	 */
	public List<Event> filterByKeyword(String keyword, List<Event> eventList, boolean inEvent) {
		List<Event> newEventList = new LinkedList<Event>();
		for (Event event : eventList) {
			boolean checkEvent = false;
			if (event.getDescription().contains(keyword)
					|| event.getTitle().contains(keyword)) {
				checkEvent = true;
			}
			for (String value : event.values()) {
				if (value.contains(keyword)) {
					checkEvent= true;
				}
			}
			if (checkEvent==inEvent) newEventList.add(event);
		}
		return newEventList;
	}

	/**
	 * Finds all events for a given timeframe.
	 */
	public List<Event> filterByTimeFrame(Calendar startTime, Calendar endTime,
			List<Event> eventList) {
		List<Event> newEventList = new LinkedList<Event>();
		for (Event event : eventList) {
			if (event.getStartTime().after(startTime)
					&& event.getEndTime().before(endTime)) {
				newEventList.add(event);
			}
		}
		return newEventList;
	}

	/**
	 * Finds all events with a given keyword in a specific attribute (Doesn't
	 * include title, description, or location).
	 */
	public List<Event> filterByKeyword(String attribute, String keyword,
			List<Event> eventList) {
		List<Event> newEventList = new LinkedList<Event>();
		for (Event event : eventList) {
			if (event.containsKey(attribute)) {
				if (event.get(attribute).contains(keyword)) {
					newEventList.add(event);
				}
			}
			else{
				newEventList.add(event);
			}
		}
		return newEventList;
	}

	/**
	 * Sorts events by name in ascending order
	 */
	public void sortByTitle(List<Event> eventList, boolean reversed) {
		AttributeComparator comp = new AttributeComparator("title",reversed);
		Collections.sort(eventList, comp);

	}
	
	/**
	 * Sorts events by start time in ascending order 
	 */
	public void sortByStartTime(List<Event> eventList, boolean reversed){
		AttributeComparator comp = new AttributeComparator("startTime",reversed);
		Collections.sort(eventList, comp);
	}
	
	/**
	 * Sorts events by start time in ascending order 
	 */
	public void sortByEndTime(List<Event> eventList, boolean reversed){
		AttributeComparator comp = new AttributeComparator("endTime",reversed);
		Collections.sort(eventList, comp);
	}
	
	private class AttributeComparator implements Comparator<Event>{
		private final int coeff;
		private String attribute;
		
		public AttributeComparator(String attribute, boolean reversed){
			this.coeff = reversed ? -1 : 1;
			this.attribute = attribute;
		}

		@Override
		public int compare(Event o1, Event o2) {
			Comparable attribute1 = o1.getTitle();
			Comparable attribute2 = o2.getTitle();
			if(attribute.equals("startTime")){
				attribute1 = o1.getStartTime();
				attribute2 = o2.getStartTime();
			}
			if(attribute.equals("endTime")){
				attribute1 = o1.getEndTime();
				attribute2 = o2.getEndTime();
			}
			return coeff*attribute1.compareTo(attribute2);
		}
		
		
	}
}
