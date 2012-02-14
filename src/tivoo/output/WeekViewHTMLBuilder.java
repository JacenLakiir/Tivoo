package tivoo.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.List;
import tivoo.Event;
import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.DocumentType;
import com.hp.gagawa.java.elements.A;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.P;
import com.hp.gagawa.java.elements.Title;
import com.hp.gagawa.java.elements.U;


public class WeekViewHTMLBuilder implements HTMLBuilder
{

    @Override
    public void buildSummaryPage (String summaryPageFileName,
                                  String detailPageDirectory,
                                  List<Event> eventList) throws IOException
    {
        if (summaryPageFileName.contains("/"))
        {
            File outputDirectory =
                new File(summaryPageFileName.substring(0, summaryPageFileName.lastIndexOf("/")));
            outputDirectory.mkdirs();
        }
        File summaryPage = new File(summaryPageFileName);
        summaryPage.createNewFile();
        String detailPageFolder =
            detailPageDirectory.substring(detailPageDirectory.lastIndexOf("/") + 1);
        writeSummaryPageHTML(summaryPage, detailPageFolder, eventList);
    }

    
    @Override
    public void buildDetailsPages (String detailPageDirectory, List<Event> eventList)
        throws IOException
    {
        File detailsDirectory = new File(detailPageDirectory);
        detailsDirectory.mkdirs();
        for (Event currentEvent : eventList)
        {
            String detailPageURL = createDetailsPageURL(currentEvent);
            File detailPage = new File(detailPageDirectory + "/" + detailPageURL);
            detailPage.createNewFile();
            writeDetailsPageHTML(currentEvent, detailPage);
        }
    }

    
    private void writeSummaryPageHTML (File summaryPage,
                                       String detailPageFolder,
                                       List<Event> eventList) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(summaryPage);
        OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");

        Document doc = new Document(DocumentType.HTMLTransitional);
        doc.head.appendChild(new Title().appendText("Summary"));

        Div allEventInfo = new Div();
        for (Event currentEvent : eventList)
        {
            Div eventInfo = new Div();
            eventInfo.setId("eventInfo");
            P eventP = new P();
            eventP.appendChild(linkToDetailsPage(detailPageFolder, currentEvent));
            eventP.appendChild(new Br());
            eventP.appendText(formatTime(currentEvent));
            eventInfo.appendChild(eventP);
            allEventInfo.appendChild(eventInfo);
        }
        doc.body.appendChild(allEventInfo);
        out.write(doc.write());
        out.close();
    }

    
    private void writeDetailsPageHTML (Event currentEvent, File detailPage) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(detailPage);
        OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");

        Document doc = new Document(DocumentType.HTMLTransitional);
        doc.head.appendChild(new Title().appendText("Details"));

        P titleP = new P();
        titleP.appendChild(new U().appendText(currentEvent.getTitle()));
        doc.body.appendChild(titleP);

        createParagraphTag(doc, "Time: " + formatTime(currentEvent));
        createParagraphTag(doc, "Location: " + currentEvent.getLocation());
        createParagraphTag(doc, currentEvent.getDescription());

        out.write(doc.write());
        out.close();
    }

    
    private A linkToDetailsPage (String detailPageFolder, Event currentEvent)
    {
        StringBuilder link = new StringBuilder();
        link.append(detailPageFolder + "/");
        link.append(createDetailsPageURL(currentEvent));

        A detailsLink = new A();
        detailsLink.appendText(currentEvent.getTitle());
        detailsLink.setHref(link.toString());
        return detailsLink;
    }

    
    private String createDetailsPageURL (Event currentEvent)
    {
        StringBuilder url = new StringBuilder();
        url.append(currentEvent.getTitle()
                               .replaceAll("\\s+", "_")
                               .replaceAll("[^A-z_0-9]", "")
                               .trim());
        url.append(".html");
        return url.toString();
    }

    
    private void createParagraphTag (Document doc, String contents)
    {
        P paragraph = new P();
        paragraph.appendText(contents);
        doc.body.appendChild(paragraph);
    }

    
    private String formatTime (Event currentEvent)
    {
        StringBuilder eventTime = new StringBuilder();

        Calendar start = currentEvent.getStartTime();
        eventTime.append(start.get(Calendar.HOUR));
        eventTime.append(":");
        eventTime.append(start.get(Calendar.MINUTE));
        eventTime.append(" - ");

        Calendar end = currentEvent.getEndTime();
        eventTime.append(end.get(Calendar.HOUR));
        eventTime.append(":");
        eventTime.append(end.get(Calendar.MINUTE));

        return eventTime.toString();
    }

}
