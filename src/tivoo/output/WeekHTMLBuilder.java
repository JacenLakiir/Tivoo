package tivoo.output;

import java.util.List;
import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.*;
import tivoo.Event;

public abstract class WeekHTMLBuilder extends HTMLBuilder
{
    
    private static final String TITLE = "Week View";
    private static final String UNIQUE_CSS = "";

    protected String mySummaryPageFileName;
    protected String myDetailPageDirectory;
            
    protected WeekHTMLBuilder(String summaryPageFileName)
    {
        super(summaryPageFileName);
    }
    
    @Override
    protected void writeSummaryPageContent (Document doc, List<Event> eventList)
    {
        Div content = new Div().setCSSClass("content");     
        content.appendChild(new H3().appendText(getTitle()));
        content.appendChild(buildWeekCalendar(eventList));
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
    
    protected abstract Node buildWeekCalendar (List<Event> eventList);
   
}
