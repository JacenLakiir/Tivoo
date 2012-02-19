package tivoo.output;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import com.hp.gagawa.java.*;
import com.hp.gagawa.java.elements.*;
import tivoo.Event;

public class SortedViewHTMLBuilder extends HTMLBuilder
{

    private String myPageFileName;
    
    public SortedViewHTMLBuilder (String pageFileName)
    {
        myPageFileName = pageFileName;
    }

    @Override
    public void buildHTML (List<Event> eventList) throws IOException
    {
        writeSortedPageHTML(eventList);
    }
    
    private void writeSortedPageHTML (List<Event> eventList) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(myPageFileName);
        OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");

        Document doc = initializeHTMLDocument("Sorted View", "");
        doc.head.appendChild(insertCSS("../css/sortedViewStyle.css"));
        
        writeHeader(doc);    
        writeContent(doc, eventList);
        writeFooter(doc);
        
        out.write(doc.write());
        out.close();
    }

    private void writeContent (Document doc, List<Event> eventList)
    {
        Div content = new Div().setCSSClass("content");     
        content.appendChild(new H3().appendText("Sorted View"));
        
        Div sortedView = constructSortedDiv(eventList);
        content.appendChild(sortedView);
        
        doc.body.appendChild(content);
    }

    private Div constructSortedDiv (List<Event> eventList)
    {
        Div sortedView = new Div().setCSSClass("Sorted View");
        for (Event e : eventList)
        {
            Div eventInfo = constructEventDiv(e);
            sortedView.appendChild(eventInfo);
        }
        return sortedView;
    }
    
    private Div constructEventDiv (Event currentEvent)
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
