package tivoo.input;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import tivoo.Event;
import tivoo.EventImpl;



public class TVParserHandler extends ParserHandler{
	private List<Event> events = new ArrayList<Event>();
	private EventImpl currentEvent = new EventImpl();
    private Calendar currentCalendar;
    private HashMap<String, String> channelMap = new HashMap<String, String>();
    
    
    private static HashMap<String, Class<? extends ElementHandler>> elementHandlerMap =
            new HashMap<String, Class<? extends ElementHandler>>();
    public TVParserHandler(){
        elementHandlerMap.put("programme", EventElementHandler.class);
        elementHandlerMap.put("title", TitleElementHandler.class);
        elementHandlerMap.put("desc", DescriptionElementHandler.class);  
        elementHandlerMap.put("sub-title", SubTitleElementHandler.class);
        elementHandlerMap.put("channel", ChannelElementHandler.class);
        elementHandlerMap.put("display-name", ProgrammeNameElementHandler.class);
        }
	public List<Event> getEvents() {

		return events;
	}

	@Override
	public ElementHandler getElementHandler(String namespace, String localName,
			String qualifiedName) {
		ElementHandler handler = null;
        try
        {
            Class<? extends ElementHandler> handlerClass =
                elementHandlerMap.get(qualifiedName);
            if (handlerClass != null)
            {
                handler =
                    handlerClass.getDeclaredConstructor(TVParserHandler.class)
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
        return handler;	}
	
	
	
    protected class EventElementHandler extends ElementHandler
    {
        public void startElement (Attributes attributes)
        {
            currentEvent = new EventImpl();
            
            String startTime = attributes.getValue("start");
            currentCalendar = Calendar.getInstance();
            setTime(currentCalendar,startTime);
            currentEvent.setStartTime(currentCalendar);
            
            currentCalendar = Calendar.getInstance();
            String endTime = attributes.getValue("stop");
            setTime(currentCalendar,endTime);
            currentEvent.setEndTime(currentCalendar);
            
            String channelId = attributes.getValue("channel");
            currentEvent.setDescription("Channel: " + channelMap.get(channelId) + "<br>");
            currentEvent.setLocation("NA");
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
    
    protected class SubTitleElementHandler extends ElementHandler
    {
        public void characters (char[] ch, int start, int length)
        {
        	String title = currentEvent.getTitle();
        	String currentTitle = title + "--" +new String(ch,start,length);
            currentEvent.setTitle(currentTitle);
        }    
    }

    protected class DescriptionElementHandler extends ElementHandler
    {
        public void characters (char[] ch, int start, int length)
        {
        	
            currentEvent.setDescription(currentEvent.getDescription() + new String(ch, start, length));
        }
    }
     
    @Override
	public InputSource resolveEntity(String publicId, String systemId)
			throws IOException, SAXException 
	{
    	if(systemId.contains("xmltv.dtd")){
    		return new InputSource(new StringReader(""));
    	}else
    		return null;
	}
    
    
	private  String channelId = "";
	private int count = 0;
	
    protected class ChannelElementHandler extends ElementHandler
    {
		public void startElement(Attributes attributes){
			channelId = attributes.getValue("id");
			count = 0;
		}
		public void endElement(Attributes attributes){
			count = 0;
		}
    }
    
    protected class ProgrammeNameElementHandler extends ElementHandler
    {
    	public void characters(char[] ch, int start, int length){
    		if(count == 4){
    			channelMap.put(channelId, new String(ch,start, length));
    			count++;
    		}else
    			count++;
    	}
    }
    
    
	private void setTime (Calendar cal, String gmtDate)
    {
        int year = Integer.parseInt(gmtDate.substring(0, 4));
        int month = Integer.parseInt(gmtDate.substring(4, 6));
        int day = Integer.parseInt(gmtDate.substring(6, 8));
        int hour = Integer.parseInt(gmtDate.substring(8, 10));
        int minute = Integer.parseInt(gmtDate.substring(10, 12));
        int second = Integer.parseInt(gmtDate.substring(12, 14));
        String sign = gmtDate.substring(15,16);
        int timezone = Integer.parseInt(gmtDate.substring(16,18));
        cal.set(year, month, day, hour, minute, second);
        cal.setTimeZone(TimeZone.getTimeZone("GMT" + sign + Integer.toString(timezone)));
    }
}
