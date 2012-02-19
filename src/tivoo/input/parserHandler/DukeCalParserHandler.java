package tivoo.input.parserHandler;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.TimeZone;
import org.xml.sax.Attributes;
import tivoo.Event;


public class DukeCalParserHandler extends ParserHandler
{
    private static HashMap<String, Class<? extends ElementHandler>> elementHandlerMap =
        new HashMap<String, Class<? extends ElementHandler>>();
    static
    {
        elementHandlerMap.put("event", EventElementHandler.class);
        elementHandlerMap.put("summary", TitleElementHandler.class);
        elementHandlerMap.put("start", StartElementHandler.class);
        elementHandlerMap.put("end", EndElementHandler.class);
        elementHandlerMap.put("utcdate", TimeElementHandler.class);
        elementHandlerMap.put("address", LocationElementHandler.class);
        elementHandlerMap.put("description", DescriptionElementHandler.class);
    }

    private List<Event> events = new LinkedList<Event>();
    private Event currentEvent;
    private Calendar currentCalendar;

    protected class EventElementHandler extends ElementHandler
    {
        public void startElement (Attributes attributes)
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
        public void characters (char[] ch, int start, int length)
        {
            currentEvent.setTitle(new String(ch, start, length));
        }
    }

    protected class StartElementHandler extends ElementHandler
    {
        public void startElement (Attributes attributes)
        {
            currentCalendar = Calendar.getInstance();
        }


        public void endElement ()
        {
            currentEvent.setStartTime(currentCalendar);
        }
    }

    protected class EndElementHandler extends ElementHandler
    {
        public void startElement (Attributes attributes)
        {
            currentCalendar = Calendar.getInstance();
        }


        public void endElement ()
        {
            currentEvent.setEndTime(currentCalendar);
        }
    }

    protected class TimeElementHandler extends ElementHandler
    {
        public void characters (char[] ch, int start, int length)
        {
            setTime(currentCalendar, new String(ch, start, length));
        }
    }

    protected class LocationElementHandler extends ElementHandler
    {
        public void characters (char[] ch, int start, int length)
        {
            currentEvent.setLocation(new String(ch, start, length));
        }
    }

    protected class DescriptionElementHandler extends ElementHandler
    {
        public void characters (char[] ch, int start, int length)
        {
            currentEvent.setDescription(new String(ch, start, length));
        }
    }


    public ElementHandler getElementHandler (String namespace,
                                             String localName,
                                             String qualifiedName)
    {
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


    public List<Event> getEvents ()
    {
        return events;
    }


    private void setTime (Calendar cal, String utcDate)
    {
        int year = Integer.parseInt(utcDate.substring(0, 4));
        int month = Integer.parseInt(utcDate.substring(4, 6));
        int day = Integer.parseInt(utcDate.substring(6, 8));
        int hour = Integer.parseInt(utcDate.substring(9, 11));
        int minute = Integer.parseInt(utcDate.substring(11, 13));
        int second = Integer.parseInt(utcDate.substring(13, 15));
        cal.set(year, month, day, hour, minute, second);
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
}
