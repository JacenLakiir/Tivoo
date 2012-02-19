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
    
    protected String createDetailsPageURL (Event currentEvent)
    {
        StringBuilder url = new StringBuilder();
        url.append(currentEvent.getTitle()
                               .replaceAll("\\s+", "_")
                               .replaceAll("[^A-z_0-9]", "")
                               .trim());
        url.append(".html");
        return url.toString();
    }
    
    protected A linkToDetailsPage (String detailPageFolder, Event currentEvent)
    {
        StringBuilder link = new StringBuilder();
        link.append(detailPageFolder + "/");
        link.append(createDetailsPageURL(currentEvent));

        A detailsLink = new A();
        detailsLink.appendText(currentEvent.getTitle());
        detailsLink.setHref(link.toString());
        return detailsLink;
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
    
    
    protected String getEventTimespan (Event currentEvent)
    {
        StringBuilder eventTimeSpan = new StringBuilder();
        eventTimeSpan.append(getClockTime(currentEvent.getStartTime()));
        eventTimeSpan.append(" - ");
        eventTimeSpan.append(getClockTime(currentEvent.getEndTime()));
        return eventTimeSpan.toString();
    }
    
    
    protected String getClockTime (Calendar cal)
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
