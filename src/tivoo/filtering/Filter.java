package tivoo.filtering;

import tivoo.Event;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Filter {

	/**
	 * Finds all events with the given keyword in any attribute.
	 */
	public List<Event> filterByKeyword(String keyword, List<Event> eventList) {
		List<Event> newEventList = new LinkedList<Event>();
		for (Event event : eventList) {
			if (event.getDescription().contains(keyword)
					|| event.getTitle().contains(keyword)) {
				newEventList.add(event);
				continue;
			}
			for (String value : event.values()) {
				if (value.contains(keyword)) {
					newEventList.add(event);
					break;
				}
			}
		}
		return newEventList;
	}
	
	/**
	 * Finds all events without the given keyword.
	 */
	public List<Event> filterByNonKeyword(String keyword, List<Event> eventList) {
		List<Event> newEventList = new LinkedList<Event>();
		for (Event event : eventList) {
			boolean inEvent = false;
			if (event.getDescription().contains(keyword)
					|| event.getTitle().contains(keyword)) {
				inEvent = true;
			}
			for (String value : event.values()) {
				if (value.contains(keyword)) {
					inEvent = true;
				}
			}
			if (!inEvent) newEventList.add(event);
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
		}
		return newEventList;
	}

	/**
	 * Sorts events by name in ascending order
	 */
	public void sortByTitle(List<Event> eventList, boolean reversed) {
		final int coeff = reversed ? -1 : 1;
		Collections.sort(eventList, new Comparator<Event>() {

			public int compare(Event event1, Event event2) {
				return coeff*event1.getTitle().compareTo(event2.getTitle());
			}

		});

	}
	
	/**
	 * Sorts events by start time in ascending order 
	 */
	public void sortByStartTime(List<Event> eventList, boolean reversed){
		final int coeff = reversed ? -1 : 1;
		Collections.sort(eventList, new Comparator<Event>() {

			public int compare(Event event1, Event event2) {
				return coeff*event1.getStartTime().compareTo(event2.getStartTime());
			}
		});
	}
	
	/**
	 * Sorts events by start time in ascending order 
	 */
	public void sortByEndTime(List<Event> eventList, boolean reversed){
		final int coeff = reversed ? -1 : 1;
		Collections.sort(eventList, new Comparator<Event>() {

			public int compare(Event event1, Event event2) {
				return coeff*event1.getEndTime().compareTo(event2.getEndTime());
			}
		});
	}
}
