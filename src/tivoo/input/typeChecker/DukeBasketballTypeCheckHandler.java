package tivoo.input.typeChecker;

import java.util.HashMap;
import java.util.HashSet;
import org.xml.sax.SAXException;
import tivoo.input.parserHandler.ElementHandler;


public class DukeBasketballTypeCheckHandler extends TypeCheckHandler
{
    private static HashMap<String, Class<? extends ElementHandler>> elementHandlerMap =
        new HashMap<String, Class<? extends ElementHandler>>();
    static
    {
        elementHandlerMap.put("Calendar", EventElementHandler.class);
        elementHandlerMap.put("Subject", TitleElementHandler.class);
        elementHandlerMap.put("StartDate", StartDateElementHandler.class);
        elementHandlerMap.put("StartTime", StartTimeElementHandler.class);
        elementHandlerMap.put("EndDate", EndDateElementHandler.class);
        elementHandlerMap.put("EndTime", EndTimeElementHandler.class);
        elementHandlerMap.put("Location", LocationElementHandler.class);
        elementHandlerMap.put("Description", DescriptionElementHandler.class);
    }

    private HashSet<String> seen = new HashSet<String>();

    protected class EventElementHandler extends ElementHandler
    {
        public void endElement () throws SAXException
        {
            if (seen.contains("title") && seen.contains("start date") &&
                seen.contains("start time") && seen.contains("end date") &&
                seen.contains("end time") && seen.contains("location") &&
                seen.contains("description"))
            {
                throw new TypeMatchedException();
            }
        }
    }

    protected class TitleElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            seen.add("title");
        }
    }

    protected class StartDateElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            seen.add("start date");
        }
    }

    protected class StartTimeElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            seen.add("start time");
        }
    }

    protected class EndDateElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            seen.add("end date");
        }
    }

    protected class EndTimeElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            seen.add("end time");
        }
    }

    protected class LocationElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            seen.add("location");
        }
    }

    protected class DescriptionElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            seen.add("description");
        }
    }

	@Override
	public HashMap<String, Class<? extends ElementHandler>> getElementHandlerMap() {
	
		return elementHandlerMap;
	}

}
