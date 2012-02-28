package tivoo.input.parserHandler;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import org.xml.sax.Attributes;
import tivoo.Event;


public class DukeBasketballCalHandler extends ParserHandler
{
    private static HashMap<String, Class<? extends ElementHandler>> elementHandlerMap = new HashMap<String, Class<? extends ElementHandler>>();

    private Event currentEvent;
    private List<Event> events = new LinkedList<Event>();
    private String currentDate;
    private String currentTime;
    private Calendar currentCalendar;

    static
    {
        elementHandlerMap.put("Calendar", EventElementHandler.class);
        elementHandlerMap.put("Subject", TitleElementHandler.class);
        elementHandlerMap.put("StartTime", StartTimeElementHandler.class);
        elementHandlerMap.put("StartDate", StartDateElementHandler.class);
        elementHandlerMap.put("EndTime", EndTimeElementHandler.class);
        elementHandlerMap.put("EndDate", EndDateElementHandler.class);
        elementHandlerMap.put("Location", LocationElementHandler.class);
        elementHandlerMap.put("Description", DescriptionElementHandler.class);
    }


    @Override
    public List<Event> getEvents ()
    {
        return events;
    }

    protected class EventElementHandler extends ElementHandler
    {
        public void startElement (Attributes attribute)
        {
            currentEvent = new Event();
        }


        public void endElement ()
        {
            events.add(currentEvent);
        }
    }

    protected class TitleElementHandler extends ElementHandler
    {

        @Override
        public void characters (char[] ch, int start, int length)
        {
            currentEvent.setTitle(new String(ch, start, length));
        }

    }

    protected class DescriptionElementHandler extends ElementHandler
    {
        public void characters (char[] ch, int start, int length)
        {
            currentEvent.setDescription(new String(ch, start, length));
        }
    }

    protected class StartDateElementHandler extends ElementHandler
    {

        public void startElement (Attributes attributes)
        {
            currentCalendar = Calendar.getInstance();
        }


        public void characters (char[] ch, int start, int length)
        {

            currentDate = new String(ch, start, length);
        }
    }

    protected class StartTimeElementHandler extends ElementHandler
    {
        public void characters (char[] ch, int start, int length)
        {
            currentTime = new String(ch, start, length);
        }


        public void endElement ()
        {
            setTime(currentCalendar, currentTime, currentDate);
            currentEvent.setStartTime(currentCalendar);
        }
    }

    protected class EndDateElementHandler extends ElementHandler
    {

        public void startElement (Attributes attributes)
        {
            currentCalendar = Calendar.getInstance();
        }


        public void characters (char[] ch, int start, int length)
        {

            currentTime = new String(ch, start, length);
        }
    }

    protected class EndTimeElementHandler extends ElementHandler
    {
        public void characters (char[] ch, int start, int length)
        {
            currentTime = new String(ch, start, length);
        }


        public void endElement ()
        {
            setTime(currentCalendar, currentTime, currentDate);
            currentEvent.setEndTime(currentCalendar);
        }
    }

    protected class LocationElementHandler extends ElementHandler
    {
        public void characters (char[] ch, int start, int length)
        {
            currentEvent.setLocation(new String(ch, start, length));
        }
    }


    private void setTime (Calendar cal, String time, String date)
    {
        String[] myDate = date.split("/");
        String[] myTime = time.split(":");
        int year = Integer.parseInt(myDate[2]);
        int month = Integer.parseInt(myDate[0]);
        int day = Integer.parseInt(myDate[1]);
        int hour = Integer.parseInt(myTime[0]);
        int minuete = Integer.parseInt(myTime[1]);
        int second = Integer.parseInt(myTime[2].substring(0, 2));
        String convertTwelve = myTime[2].substring(3, 5);
        if (convertTwelve.equals("PM"))
        {
            hour += 12;
        }
        cal.set(year, month, day, hour, minuete, second);
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
    }


	@Override
	public HashMap<String, Class<? extends ElementHandler>> getElementHandlerMap() {
		// TODO Auto-generated method stub
		return elementHandlerMap;
	}

}
