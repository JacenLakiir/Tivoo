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
        buildDetailPages(eventList);
    }
   
    protected void buildSummaryPage (List<Event> eventList) throws IOException
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

    protected void buildDetailPages (List<Event> eventList) throws IOException
    {
        File detailsDirectory = new File(myDetailPageDirectory);
        detailsDirectory.mkdirs();
        for (Event currentEvent : eventList)
        {
            String detailPageURL = createDetailPageURL(currentEvent);
            File detailPage = new File(myDetailPageDirectory + "/" + detailPageURL);
            detailPage.createNewFile();
            writeDetailPageHTML(currentEvent, detailPage);
        }
    }
    
    protected abstract void writeSummaryPageHTML (File summaryPage,
                                                  List<Event> eventList) throws IOException;
    
    protected void writeDetailPageHTML (Event currentEvent, File detailPage) throws IOException
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

        createParagraphTag(content, "Time", formatDateTimespan(currentEvent));
        createParagraphTag(content, "Location", currentEvent.getLocation());
        createParagraphTag(content, "Description", currentEvent.getDescription());
        
        doc.body.appendChild(content);
    }
    
    protected String createDetailPageURL (Event currentEvent)
    {
        StringBuilder url = new StringBuilder();
        url.append(currentEvent.getTitle()
                               .replaceAll("\\s+", "_")
                               .replaceAll("[^A-z_0-9]", "")
                               .trim());
        url.append(".html");
        return url.toString();
    }
    
    protected A linkToDetailPage (String detailPageFolder, Event currentEvent)
    {
        StringBuilder link = new StringBuilder();
        link.append(detailPageFolder + "/");
        link.append(createDetailPageURL(currentEvent));

        A detailsLink = new A();
        detailsLink.appendText(currentEvent.getTitle());
        detailsLink.setHref(link.toString());
        return detailsLink;
    }
    
    protected Div constructEventDiv (Event currentEvent)
    {
        Div eventInfo = new Div().setId("event");
        
        P eventP = new P();
        eventP.appendChild(linkToDetailPage(myDetailPageFolder, currentEvent));
        eventP.appendChild(new Br());
        eventP.appendText(formatClockTimespan(currentEvent));
        
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
