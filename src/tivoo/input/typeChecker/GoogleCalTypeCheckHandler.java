package tivoo.input.typeChecker;

import java.util.HashMap;
import java.util.HashSet;
import tivoo.input.parserHandler.ElementHandler;


public class GoogleCalTypeCheckHandler extends TypeCheckHandler
{
    private static HashMap<String, Class<? extends ElementHandler>> elementHandlerMap =
        new HashMap<String, Class<? extends ElementHandler>>();
    static
    {
        elementHandlerMap.put("entry", EventElementHandler.class);
        elementHandlerMap.put("title", TitleElementHandler.class);
        elementHandlerMap.put("content", ContentElementHandler.class);
        elementHandlerMap.put("name", AuthorElementHanlder.class);
    }

    private HashSet<String> seen = new HashSet<String>();

    
    protected class EventElementHandler extends ElementHandler
    {
        public void endElement () throws TypeMatchedException
        {
            if (seen.contains("title") && seen.contains("author") &&
                seen.contains("content"))
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

    protected class AuthorElementHanlder extends ElementHandler
    {
        public void endElement ()
        {
            seen.add("author");
        }
    }

    protected class ContentElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            seen.add("content");
        }
    }

	@Override
	public HashMap<String, Class<? extends ElementHandler>> getElementHandlerMap() {
		// TODO Auto-generated method stub
		return elementHandlerMap;
	}
}
