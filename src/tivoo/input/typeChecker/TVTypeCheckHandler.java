package tivoo.input.typeChecker;

import java.util.HashMap;
import java.util.HashSet;

import tivoo.input.parserHandler.ElementHandler;


public class TVTypeCheckHandler extends TypeCheckHandler
{
    private static HashMap<String, Class<? extends ElementHandler>> elementHandlerMap =
        new HashMap<String, Class<? extends ElementHandler>>();
    static
    {
        elementHandlerMap.put("programme", EventElementHandler.class);
        elementHandlerMap.put("title", TitleElementHandler.class);
        elementHandlerMap.put("desc", DescriptionElementHandler.class);
        elementHandlerMap.put("sub-title", SubTitleElementHandler.class);
    }

    private boolean valid = false;
    private HashSet<String> seen = new HashSet<String>();


    public boolean isValid ()
    {
        return valid;
    }


    public ElementHandler getElementHandler (String namespace,
                                             String localName,
                                             String qualifiedName)
    {
        ElementHandler handler = null;
        if (!valid)
        {
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
        }
        else
        {
            handler = new ElementHandler();
        }
        return handler;
    }

    protected class EventElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            if (seen.contains("title") && seen.contains("subtitle") &&
                seen.contains("description"))
            {
                valid = true;
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

    protected class SubTitleElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            seen.add("subtitle");
        }
    }

    protected class DescriptionElementHandler extends ElementHandler
    {
        public void endElement ()
        {
            seen.add("description");
        }
    }
}
