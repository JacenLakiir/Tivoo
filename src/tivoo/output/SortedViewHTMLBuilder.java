package tivoo.output;

import java.util.List;
import com.hp.gagawa.java.elements.*;
import tivoo.Event;

public class SortedViewHTMLBuilder extends HTMLBuilder
{
    
    private static final String TITLE = "Sorted View";
    private static final String UNIQUE_CSS = "../css/sortedStyle.css";
    
    public SortedViewHTMLBuilder (String summaryPageFileName)
    {
        super(summaryPageFileName);
    }

    @Override
    protected Div buildView (List<Event> eventList)
    {
        Div sortedView = new Div().setCSSClass("sortedView");
        for (Event e : eventList)
        {
            sortedView.appendChild(new Hr());
            Div eventInfo = constructEventDiv(e);
            sortedView.appendChild(eventInfo);
        }
        return sortedView;
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
