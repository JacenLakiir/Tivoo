package tivoo.input.parserHandler;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.xml.sax.Attributes;

import tivoo.Event;


public class NFLCalParserHandler extends ParserHandler{
	private Event currentEvent;
	private List<Event> events = new LinkedList<Event>();
	private Calendar currentCalendar;
	
    private static HashMap<String, Class<? extends ElementHandler>> elementHandlerMap =
            new HashMap<String, Class<? extends ElementHandler>>();
    static{
    	elementHandlerMap.put("row", EventElementHandler.class);
    	elementHandlerMap.put("Col1", TitleElementHandler.class);
    	elementHandlerMap.put("Col8", StartTimeElementHandler.class);
    	elementHandlerMap.put("Col9", EndTimeElementHandler.class);
    	elementHandlerMap.put("Col15", LocationElementHandler.class);
    }
	@Override
	public List<Event> getEvents() {
		// TODO Auto-generated method stub
		return events;
	}

	@Override
	public ElementHandler getElementHandler(String namespace, String localName,
			String qualifiedName) {
        ElementHandler handler = null;
        try
        {
            Class<? extends ElementHandler> handlerClass =
                elementHandlerMap.get(qualifiedName);
            if (handlerClass != null)
            {
                handler =
                    handlerClass.getDeclaredConstructor(this.getClass())
                                .newInstance(this);
            }
            else
            {
                handler = new ElementHandler();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return handler;	
        }
	
	protected class EventElementHandler extends ElementHandler {

		@Override
		public void startElement(Attributes attributes) {
			currentEvent = new Event();
		}
		@Override
		public void endElement() {
			currentEvent.setDescription("N/A");
			events.add(currentEvent);
			
		}
	}
	protected class TitleElementHandler extends ElementHandler {
		@Override
		public void characters(char[] ch, int start, int length) {
			currentEvent.setTitle(new String(ch, start, length));
		}
	}
	protected class StartTimeElementHandler extends ElementHandler{
		
		
		@Override
		public void startElement(Attributes attributes) {
			currentCalendar = Calendar.getInstance();
		}

		@Override
		public void endElement() {
			currentEvent.setStartTime(currentCalendar);
		}

		public void characters(char[] ch, int start, int length){
			setTime(currentCalendar, new String(ch, start, length));
		}
	}
	
	protected class EndTimeElementHandler extends ElementHandler{	
		@Override
		public void startElement(Attributes attributes) {
			currentCalendar = Calendar.getInstance();
		}

		@Override
		public void endElement() {
			currentEvent.setEndTime(currentCalendar);
		}

		public void characters(char[] ch, int start, int length){
			setTime(currentCalendar, new String(ch, start, length));
		}
	}

	protected class LocationElementHandler extends ElementHandler{

		@Override
		public void characters(char[] ch, int start, int length) {
			currentEvent.setLocation(new String(ch, start, length));
		}
		
	}
	
	
    private void setTime (Calendar cal, String date)
    {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        int hour = Integer.parseInt(date.substring(11, 13)) + 12;
        int minute = Integer.parseInt(date.substring(14, 16));
        int second = Integer.parseInt(date.substring(17, 19));
        
        cal.set(year, month, day, hour, minute, second);
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
	

}
