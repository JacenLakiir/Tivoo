package tivoo.output;

import java.util.List;
import com.hp.gagawa.java.*;
import com.hp.gagawa.java.elements.*;
import tivoo.Event;

public class SortedViewHTMLBuilder extends HTMLBuilder
{
    
    private static final String TITLE = "Sorted View";
    private static final String UNIQUE_CSS = "../css/sortedViewStyle.css";
    
    public SortedViewHTMLBuilder (String summaryPageFileName)
    {
        super(summaryPageFileName);
    }

    @Override
    protected void writeSummaryPageContent (Document doc, List<Event> eventList)
    {
        Div content = new Div().setCSSClass("content");     
        content.appendChild(new H3().appendText(TITLE));
        
        Div sortedView = constructSortedDiv(eventList);
        content.appendChild(sortedView);
        
        doc.body.appendChild(content);
    }
    
    @Override
    protected String getTitle ()
    {
        return TITLE;
    }
    
    @Override
    protected String getUniqueCSS ()
    {
        return UNIQUE_CSS;
    }

    private Div constructSortedDiv (List<Event> eventList)
    {
        Div sortedView = new Div().setCSSClass("Sorted View");
        for (Event e : eventList)
        {
            Div eventInfo = constructSortedEventDiv(e);
            sortedView.appendChild(eventInfo);
        }
        return sortedView;
    }
    
    private Div constructSortedEventDiv (Event currentEvent)
    {
        Div eventInfo = new Div().setCSSClass("event");
        eventInfo.appendChild(new Hr());
        eventInfo.appendChild(new H4().appendText(currentEvent.getTitle()));
        
        createParagraphTag(eventInfo, "Time", formatDateTimespan(currentEvent));
        createParagraphTag(eventInfo, "Location", currentEvent.getLocation());
        createParagraphTag(eventInfo, "Description", currentEvent.getDescription());
        
        return eventInfo;
    }

}
