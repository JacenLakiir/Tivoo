package tivoo.input.typeChecker;

import java.util.HashMap;

import tivoo.input.parserHandler.ElementHandler;
import tivoo.input.parserHandler.SAXHandler;


public abstract class TypeCheckHandler extends SAXHandler
{
	public abstract HashMap<String, Class<? extends ElementHandler>> getElementHandlerMap();
	
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
