package tivoo.input.typeChecker;

import java.util.HashMap;
import java.util.HashSet;
import org.xml.sax.SAXException;
import tivoo.input.parserHandler.ElementHandler;


public class DukeCalTypeCheckHandler extends TypeCheckHandler
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

    private HashSet<String> seen = new HashSet<String>();
    private boolean valid = false;

    protected class EventElementHandler extends ElementHandler
    {
        public void endElement () throws SAXException
        {
            if (seen.contains("title") && seen.contains("start") &&
                seen.contains("end") && seen.contains("location") &&
                seen.contains("description"))
            {
                valid = true;
                throw new SAXException();
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

    protected class StartElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            if (seen.contains("time"))
            {
                seen.add("start");
                seen.remove("time");
            }
        }
    }

    protected class EndElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            if (seen.contains("time"))
            {
                seen.add("end");
                seen.remove("time");
            }
        }
    }

    protected class TimeElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            seen.add("time");
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


    public boolean isValid ()
    {
        return valid;
    }
}
