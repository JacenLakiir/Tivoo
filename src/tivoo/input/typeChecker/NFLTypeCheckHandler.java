package tivoo.input.typeChecker;

import java.util.HashMap;
import java.util.HashSet;
import org.xml.sax.SAXException;
import tivoo.input.parserHandler.ElementHandler;


public class NFLTypeCheckHandler extends TypeCheckHandler
{
    private static HashMap<String, Class<? extends ElementHandler>> elementHandlerMap =
        new HashMap<String, Class<? extends ElementHandler>>();
    static
    {
        elementHandlerMap.put("row", EventElementHandler.class);
        elementHandlerMap.put("Col1", TitleElementHandler.class);
        elementHandlerMap.put("Col8", StartElementHandler.class);
        elementHandlerMap.put("Col9", EndElementHandler.class);
        elementHandlerMap.put("Col15", LocationElementHandler.class);
    }

    private HashSet<String> seen = new HashSet<String>();

    protected class EventElementHandler extends ElementHandler
    {
        public void endElement () throws SAXException
        {
            if (seen.contains("title") && seen.contains("start") &&
                seen.contains("end") && seen.contains("location"))
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

    protected class StartElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            seen.add("start");
        }
    }

    protected class EndElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            seen.add("end");
        }
    }

    protected class LocationElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            seen.add("location");
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
}
