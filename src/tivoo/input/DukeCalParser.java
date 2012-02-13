package tivoo.input;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import tivoo.DukeEvent;
import tivoo.Event;

public class DukeCalParser implements XMLParser{
	private List<Event> events = new ArrayList<Event>();
	
	private boolean isEvent = false;
	private boolean isTitle = false;
	private boolean isStart = false;
	private boolean isEnd = false;
	private boolean isTime = false;
	private boolean isLocation = false;
	private boolean isDescription = false;
	private Event event;
	private Calendar cal;
 	@Override
	public List<Event> parse(String fileName) throws SAXException, IOException, ParserConfigurationException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		DukeCalParserHandler handler = new DukeCalParserHandler();
		parser.parse("data/dukecal.xml", handler);		
		return events;		
	}
 	
 	private class DukeCalParserHandler extends DefaultHandler{
 		StringBuilder builder = new StringBuilder();
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			builder.append(ch, start, length);
		}
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if(qName.equals("event")){
				isEvent =  true;
				event = new DukeEvent();
			}
			if(qName.equals("start")){
				cal = Calendar.getInstance();
				isStart = true;
			}
			if(qName.equals("end")){
				cal = Calendar.getInstance();
				isEnd = true;
			}
			if(qName.equals("address"))   isLocation = true;
			if(qName.equals("summary"))   isTitle = true;
			if(qName.equals("utcdate"))      isTime = true;	
			if(qName.equals("description")) isDescription = true;
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if(isEvent){
				events.add(event);
				isEvent = false;
			}
			if(isTitle){
				event.setTitle(builder.toString());
				isTitle = false;
			}
			if(isLocation) {
				event.setLocation(builder.toString());
				isLocation = false;
			}
			if(isDescription){
				event.setDescription(builder.toString());
				isDescription = false;
			}
			if(isTime){
				try {
					cal = getCalendar(builder.toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isTime = false;
				if(isStart){
					event.setStartTime(cal);
					isStart = false;
				}else if(isEnd){
					event.setEndTime(cal);
					isEnd = false;
				}
			}
			builder.setLength(0);
		}
 		private Calendar getCalendar(String utcDate) throws ParseException{
 			String year = utcDate.substring(0,4);
 			String month = utcDate.substring(4,6);
 			String day = utcDate.substring(6,8);
 			String hour = utcDate.substring(9,11);
 			String minute = utcDate.substring(11,13);
 			String seconds = utcDate.substring(13,15);
 			String time = day + "-" + month + "-" + year + " " + hour + ":" + minute + ":" + seconds; 
 			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
 			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
 			Date d = sdf.parse(time);
 			d.getTime();
 			Calendar cal = Calendar.getInstance();
 			cal.setTime(d);

 			return cal;
 		}
 	}

}
