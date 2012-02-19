package tivoo.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.elements.*;
import tivoo.Event;

public abstract class WeekHTMLBuilder extends HTMLBuilder
{
    
    protected String mySummaryPageFileName;
    protected String myDetailPageDirectory;
    protected String myDetailPageFolder;
        
    protected WeekHTMLBuilder(String summaryPageFileName, String detailPageDirectory)
    {
        mySummaryPageFileName = summaryPageFileName;
        myDetailPageDirectory = detailPageDirectory;
        myDetailPageFolder = detailPageDirectory.substring(myDetailPageDirectory.lastIndexOf("/") + 1);
    }
    
    public void buildHTML(List<Event> eventList) throws IOException
    {
        buildSummaryPage(eventList);
        buildDetailsPages(eventList);
    }
   
    public void buildSummaryPage (List<Event> eventList) throws IOException
    {
        if (mySummaryPageFileName.contains("/"))
        {
            File outputDirectory =
                new File(mySummaryPageFileName.substring(0, mySummaryPageFileName.lastIndexOf("/")));
            outputDirectory.mkdirs();
        }
        File summaryPage = new File(mySummaryPageFileName);
        summaryPage.createNewFile();
        writeSummaryPageHTML(summaryPage, eventList);
    }

    public void buildDetailsPages (List<Event> eventList) throws IOException
    {
        File detailsDirectory = new File(myDetailPageDirectory);
        detailsDirectory.mkdirs();
        for (Event currentEvent : eventList)
        {
            String detailPageURL = createDetailsPageURL(currentEvent);
            File detailPage = new File(myDetailPageDirectory + "/" + detailPageURL);
            detailPage.createNewFile();
            writeDetailsPageHTML(currentEvent, detailPage);
        }
    }
    
    protected abstract void writeSummaryPageHTML (File summaryPage,
                                                  List<Event> eventList) throws IOException;
    
    protected void writeDetailsPageHTML (Event currentEvent, File detailPage) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(detailPage);
        OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");

        Document doc = initializeHTMLDocument("Details", "../");
        writeHeader(doc);
        writeDetailPageContent(currentEvent, doc);
        writeFooter(doc);
        
        out.write(doc.write());
        out.close();
    }
        
    protected void writeDetailPageContent (Event currentEvent, Document doc)
    {
        Div content = new Div().setCSSClass("content");
        content.appendChild(new H4().appendText(currentEvent.getTitle()));

        createParagraphTag(content, "Time", getEventTimespan(currentEvent));
        createParagraphTag(content, "Location", currentEvent.getLocation());
        createParagraphTag(content, "Description", currentEvent.getDescription());
        
        doc.body.appendChild(content);
    }
    
    protected Div constructEventDiv (Event currentEvent)
    {
        Div eventInfo = new Div().setId("event");
        
        P eventP = new P();
        eventP.appendChild(linkToDetailsPage(myDetailPageFolder, currentEvent));
        eventP.appendChild(new Br());
        eventP.appendText(getEventTimespan(currentEvent));
        
        eventInfo.appendChild(eventP);
        return eventInfo;
    }

    protected Map<String, List<Event>> sortByDayOfWeek (List<Event> eventList)
    {
        Map<String, List<Event>> sortedEvents = new TreeMap<String, List<Event>>();
        for (Event currentEvent : eventList)
        {
            String eventDay = getDayOfWeek(currentEvent);
            if (!sortedEvents.containsKey(eventDay))
                sortedEvents.put(eventDay, new ArrayList<Event>());
            sortedEvents.get(eventDay).add(currentEvent);
        }
        return sortedEvents;
    }
}
