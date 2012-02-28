package tivoo.input.parserHandler;

import java.util.HashMap;
import java.util.List;
import tivoo.Event;


public abstract class ParserHandler extends SAXHandler
{

	public abstract List<Event> getEvents ();
	
	public abstract HashMap<String, Class<? extends ElementHandler>> getElementHandlerMap();

	@Override
	public ElementHandler getElementHandler(String namespace, String localName, String qualifiedName) {
	    ElementHandler handler = null;
	    try
	    {
	        Class<? extends ElementHandler> handlerClass =
	            getElementHandlerMap().get(qualifiedName);
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
