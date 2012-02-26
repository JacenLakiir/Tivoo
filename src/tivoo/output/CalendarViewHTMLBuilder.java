package tivoo.output;

import java.util.List;
import com.hp.gagawa.java.Document;
import tivoo.Event;

public class CalendarViewHTMLBuilder extends HTMLBuilder
{
    
    private static final String TITLE = "Calendar View";
    private static final String UNIQUE_CSS = "";
    
    public CalendarViewHTMLBuilder (String summaryPageFileName)
    {
        super(summaryPageFileName);
    }
    
    @Override
    protected void writeSummaryPageContent (Document doc, List<Event> eventList)
    {
        // TODO Auto-generated method stub
        
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

}
