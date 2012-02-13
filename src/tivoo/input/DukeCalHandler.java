package tivoo.input;

import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import tivoo.EventImpl;


public class DukeCalHandler extends CalendarParserHandler
{
    private boolean isEvent = false;
    private boolean isTitle = false;
    private boolean isStart = false;
    private boolean isEnd = false;
    private boolean isTime = false;
    private boolean isLocation = false;
    private boolean isDescription = false;
    private EventImpl currentEvent;
    private Calendar cal;

    StringBuilder builder = new StringBuilder();


    @Override
    public void startElement (String uri,
                              String localName,
                              String qName,
                              Attributes attributes) throws SAXException
    {
        if (qName.equals("event"))
        {
            isEvent = true;
            currentEvent = new EventImpl();
        }
        if (qName.equals("start"))
        {
            cal = Calendar.getInstance();
            isStart = true;
        }
        if (qName.equals("end"))
        {
            cal = Calendar.getInstance();
            isEnd = true;
        }
        if (qName.equals("address")) isLocation = true;
        if (qName.equals("summary")) isTitle = true;
        if (qName.equals("utcdate")) isTime = true;
        if (qName.equals("description")) isDescription = true;
    }


    @Override
    public void characters (char[] ch, int start, int length)
        throws SAXException
    {
        builder.append(ch, start, length);

        if (isTitle)
        {
            currentEvent.setTitle(builder.toString());
            isTitle = false;
        }
        if (isLocation)
        {
            currentEvent.setLocation(builder.toString());
            isLocation = false;
        }
        if (isDescription)
        {
            currentEvent.setDescription(builder.toString());
            isDescription = false;
        }
        if (isTime)
        {
            try
            {
                cal = parseTime(builder.toString());
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            isTime = false;
            if (isStart)
            {
                currentEvent.setStartTime(cal);
                isStart = false;
            }
            else if (isEnd)
            {
                currentEvent.setEndTime(cal);
                isEnd = false;
            }
        }
    }


    @Override
    public void endElement (String uri, String localName, String qName)
        throws SAXException
    {
        if (isEvent)
        {
            events.add(currentEvent);
            isEvent = false;
        }
        builder.setLength(0);
    }


    private Calendar parseTime (String utcDate) throws ParseException
    {
        int year = Integer.parseInt(utcDate.substring(0, 4));
        int month = Integer.parseInt(utcDate.substring(4, 6));
        int day = Integer.parseInt(utcDate.substring(6, 8));
        int hour = Integer.parseInt(utcDate.substring(9, 11));
        int minute = Integer.parseInt(utcDate.substring(11, 13));
        int second = Integer.parseInt(utcDate.substring(13, 15));
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hour, minute, second);
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        return cal;
    }
}
