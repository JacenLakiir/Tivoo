package tivoo.output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.DocumentType;
import com.hp.gagawa.java.elements.*;
import tivoo.Event;


public abstract class HTMLBuilder
{
    
    protected static final List<String> daysList = initializeDayList();
    
    public abstract void buildHTML(List<Event> eventList) throws IOException; 

    protected Document initializeHTMLDocument (String title, String cssFilePathExtender)
    {
        Document doc = new Document(DocumentType.HTMLTransitional);
        doc.head.appendChild(new Title().appendText(title));
        
        StringBuilder cssFilePath = new StringBuilder(cssFilePathExtender);
        cssFilePath.append("../css/tivooStyle.css");
        doc.head.appendChild(insertCSS(cssFilePath.toString()));
        
        return doc;
    }
    
    protected void writeHeader (Document doc)
    {
        Div header = new Div().setCSSClass("header");
        Table caption = new Table();
        Tr title = new Tr().appendChild(new Td().appendText("TiVOO"));
        caption.appendChild(title);
        header.appendChild(caption);
        doc.body.appendChild(header);
    }
    
    protected void writeFooter (Document doc)
    {
        Div footer = new Div().setCSSClass("footer");
        footer.appendText("Designed by Siyang, Hui, Ian, & Eric");
        footer.appendChild(new Br());
        footer.appendText("&copy; 2012");
        doc.body.appendChild(footer);
    }
    
    protected Link insertCSS (String filePath)
    {
        Link tivooStyle = new Link();
        tivooStyle.setRel("stylesheet");
        tivooStyle.setType("text/css");
        tivooStyle.setHref(filePath);
        return tivooStyle;
    }
    
    protected void createParagraphTag (Div div, String category, String contents)
    {
        P paragraph = new P();
        if (category != null)
        {
            paragraph.appendChild(new U().appendText(category));
            paragraph.appendText(": ");
        }
        paragraph.appendText(contents);
        div.appendChild(paragraph);
    }
    
    protected boolean isAllOnOneDay(Calendar start, Calendar end)
    {
        return (start.get(Calendar.DAY_OF_WEEK) == end.get(Calendar.DAY_OF_WEEK));
    }
    
    protected String formatDateTimespan (Event currentEvent)
    {
        StringBuilder timespan = new StringBuilder();
        
        Calendar start = currentEvent.getStartTime();
        timespan.append(formatDate(start));
        timespan.append(" - ");
       
        Calendar end = currentEvent.getEndTime();
        if (isAllOnOneDay(start, end))
        {
            timespan.append(formatClockTime(end));
        }
        else
        {
            timespan.append(formatDate(end));
        }
            
        return timespan.toString();
    }
    
    
    protected String formatClockTimespan (Event currentEvent)
    {
        StringBuilder clockTimespan = new StringBuilder();
        clockTimespan.append(formatClockTime(currentEvent.getStartTime()));
        clockTimespan.append(" - ");
        clockTimespan.append(formatClockTime(currentEvent.getEndTime()));
        return clockTimespan.toString();
    }    
    
    protected String formatDate (Calendar cal)
    {
        return String.format("%1$ta %<tm/%<te at %<tl:%<tM %<Tp", cal);
    }
    
    protected String formatClockTime (Calendar cal)
    {
        return String.format("%1$tl:%<tM %<Tp", cal);
    }
     
    protected String getDayOfWeek (Event currentEvent)
    {
        StringBuilder eventDay = new StringBuilder();
        Calendar start = currentEvent.getStartTime();
        eventDay.append(daysList.get(start.get(Calendar.DAY_OF_WEEK) - 1));
        return (eventDay.toString());
    }

    
    protected static List<String> initializeDayList ()
    {
        String[] days =
            new String[] {
                    "Sunday",
                    "Monday",
                    "Tuesday",
                    "Wednesday",
                    "Thursday",
                    "Friday",
                    "Saturday" };
        List<String> dayList = new ArrayList<String>();
        for (String d : days)
            dayList.add(d);
        return dayList;
    }
    
}
