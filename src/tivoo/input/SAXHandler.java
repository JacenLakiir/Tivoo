package tivoo.input;

import java.util.LinkedList;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


public abstract class SAXHandler extends DefaultHandler
{
    private LinkedList<ElementHandler> elementStack =
        new LinkedList<ElementHandler>();


    public abstract ElementHandler getElementHandler (String namespace,
                                                      String localName,
                                                      String qualifiedName);


    public void startElement (String namespace,
                              String localName,
                              String qualifiedName,
                              Attributes attributes)
    {
        ElementHandler currentHandler =
            getElementHandler(namespace, localName, qualifiedName);
		currentHandler.startElement(attributes);
		elementStack.push(currentHandler);
    }


    public void characters (char[] ch, int start, int length)
    {
        ElementHandler currentHandler = elementStack.peek();
        currentHandler.characters(ch, start, length);
    }


    public void endElement (String namespace,
                            String localName,
                            String qualifiedName)
    {
        ElementHandler currentHandler = elementStack.poll();
        currentHandler.endElement();
    }
}
