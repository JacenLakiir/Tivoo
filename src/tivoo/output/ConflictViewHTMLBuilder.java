package tivoo.output;

import java.util.LinkedList;
import java.util.List;
import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.elements.*;
import tivoo.Event;

public class ConflictViewHTMLBuilder extends HTMLBuilder
{
    
    private static final String TITLE = "Conflict View";
    private static final String UNIQUE_CSS = "../css/conflictStyle.css";
    
    public ConflictViewHTMLBuilder (String summaryPageFileName)
    {
       super(summaryPageFileName);
    }
    
    @Override
    protected void writeSummaryPageContent (Document doc, List<Event> eventList)
    {
        Div content = new Div().setCSSClass("content");     
        content.appendChild(new H3().appendText(TITLE));
        
        Table conflictView = constructConflictView(eventList);
        content.appendChild(conflictView);
        
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
    
    private Table constructConflictView (List<Event> eventList)
    {
        Table conflictView = new Table();
        conflictView.setTitle("conflictView");
        conflictView.setCellspacing("0");
        
        Tr tableHeading = buildTableHeading();
        conflictView.appendChild(tableHeading);
        
        findAllConflictingEvents(conflictView, eventList);
        
        return conflictView;
    }
    
    private void findAllConflictingEvents (Table table, List<Event> eventList)
    {
        for (Event a : eventList)
        {
            List<Event> conflicts = new LinkedList<Event>();
            for (Event b : eventList)
            {
                if (isConflicting(a, b)) conflicts.add(b);
            }
            Tr tableRow = buildTableRow(a, conflicts);
            table.appendChild(tableRow);
        }
    }
        
    private boolean isConflicting (Event a, Event b)
    {
        if (a == b) return false;
        return (a.getEndTime().getTimeInMillis() > b.getStartTime().getTimeInMillis()
                &&
                a.getStartTime().getTimeInMillis() < b.getEndTime().getTimeInMillis());
    }
    
    private Tr buildTableHeading ()
    {
        Tr headingRow = new Tr().setCSSClass("row");
        headingRow.appendChild(new Th().setCSSClass("heading").appendText("Event"));
        headingRow.appendChild(new Th().setCSSClass("heading").appendText("Time"));
        headingRow.appendChild(new Th().setCSSClass("heading").appendText("Conflicts With..."));
        return headingRow;
    }
    
    private Tr buildTableRow (Event a, List<Event> conflicts)
    {
        Tr tableRow = new Tr();
        
        A detailsLink = linkToDetailsPage(a);
        tableRow.appendChild(new Td().setCSSClass("event").appendChild(detailsLink));
        
        String eventTime = formatDateTimespan(a);
        tableRow.appendChild(new Td().setCSSClass("time").appendText(eventTime));
        
        Div conflictingEvents = constructConflictingEventsDiv(conflicts);
        tableRow.appendChild(new Td().setCSSClass("conflictsWith").appendChild(conflictingEvents));
           
        return tableRow;
    }

    private Div constructConflictingEventsDiv (List<Event> conflicts)
    {
        Div conflictingEvents = new Div();
        for (Event e : conflicts)
        {
            A detailsLink = linkToDetailsPage(e);
            conflictingEvents.appendChild(detailsLink);
            conflictingEvents.appendChild(new Br());
        }
        return conflictingEvents;
    }

}
