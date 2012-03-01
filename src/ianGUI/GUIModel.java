package ianGUI;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import tivoo.Event;
import tivoo.filtering.*;
import tivoo.input.CalendarParser;
import tivoo.output.*;

public class GUIModel {
	private List<Event> events = new LinkedList<Event>();

	public void loadFile(String fileName) throws SAXException, IOException,
			ParserConfigurationException, InstantiationException, IllegalAccessException {
		events.addAll(CalendarParser.parse(fileName));
	}

	public void clearEvents() {
		events.clear();
	}

	public void filterByKeyword(String keyword, boolean inEvent) {
		Filter filter = new Filter();
		events = filter.filterByKeyword(keyword, events, inEvent);
	}

	public void filterByKeyword(String attribute, String keyword) {
		Filter filter = new Filter();
		events = filter.filterByKeyword(attribute, keyword, events);
	}

	public void filterByTimeFrame(Calendar startTime, Calendar endTime) {
		Filter filter = new Filter();
		events = filter.filterByTimeFrame(startTime, endTime, events);
	}

	public void sortByTitle(boolean reversed) {
		Filter filter = new Filter();
		filter.sort(events, new TitleComparator(reversed));
	}

	public void sortByStartTime(boolean reversed) {
		Filter filter = new Filter();
		filter.sort(events, new StartTimeComparator(reversed));
	}

	public void sortByEndTime(boolean reversed) {
		Filter filter = new Filter();
		filter.sort(events, new EndTimeComparator(reversed));
	}

	public void outputHorizontalWeekView(String summaryPageFileName,
			String detailPageDirectory) throws IOException {
		HTMLBuilder output = new HorizontalViewHTMLBuilder(summaryPageFileName);
		output.buildHTML(events);
	}

	public void outputVerticalWeekView(String summaryPageFileName,
			String detailPageDirectory) throws IOException {
		HTMLBuilder output = new VerticalViewHTMLBuilder(summaryPageFileName);
		output.buildHTML(events);
	}

	public void outputSortedView(String pageFileName) throws IOException {
		HTMLBuilder output = new SortedViewHTMLBuilder(pageFileName);
		output.buildHTML(events);
	}

	public void outputConflictView(String pageFileName) throws IOException {
		HTMLBuilder output = new ConflictViewHTMLBuilder(pageFileName);
		output.buildHTML(events);
	}

	public void outputCalendarView(String pageFileName) throws IOException {
		HTMLBuilder output = new CalendarViewHTMLBuilder(pageFileName);
		output.buildHTML(events);
	}
	
	public int size(){
		return events.size();
	}

}
