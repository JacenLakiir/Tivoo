package tivoo.input;

import java.util.LinkedList;
import java.util.List;
import org.xml.sax.helpers.DefaultHandler;
import tivoo.Event;


public abstract class CalendarParserHandler extends DefaultHandler
{
    protected List<Event> events = new LinkedList<Event>();


    public List<Event> getEvents ()
    {
        return events;
    }
}
