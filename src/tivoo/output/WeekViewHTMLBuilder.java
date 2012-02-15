package tivoo.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import tivoo.Event;
import com.hp.gagawa.java.Document;
import com.hp.gagawa.java.DocumentType;
import com.hp.gagawa.java.elements.A;
import com.hp.gagawa.java.elements.B;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.P;
import com.hp.gagawa.java.elements.Title;


public class WeekViewHTMLBuilder implements HTMLBuilder
{

    private final List<String> daysList = initializeDayList();

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

        Map<String, List<Event>> sortedEvents = sortByDayOfWeek(eventList);

        Div weekInfo = new Div();
        for (String day : daysList)
        {
            Div dayInfo = new Div();
            dayInfo.setId("dayInfo");

            P titleP = new P();
            titleP.appendChild(new B().appendText(day));
            dayInfo.appendChild(titleP);

            List<Event> eventsOnThisDay = sortedEvents.get(day);
            if (eventsOnThisDay != null)
            {
                for (Event currentEvent : eventsOnThisDay)
                {
                    Div eventInfo = new Div();
                    eventInfo.setId("eventInfo");
                    P eventP = new P();
                    eventP.appendChild(linkToDetailsPage(detailPageFolder, currentEvent));
                    eventP.appendChild(new Br());
                    eventP.appendText(getEventTimespan(currentEvent));
                    eventInfo.appendChild(eventP);
                    dayInfo.appendChild(eventInfo);
                }
            }
            weekInfo.appendChild(dayInfo);
        }
        doc.body.appendChild(weekInfo);
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
        titleP.appendChild(new B().appendText(currentEvent.getTitle()));
        doc.body.appendChild(titleP);

        createParagraphTag(doc, "Time: " + getEventTimespan(currentEvent));
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

    private String getEventTimespan (Event currentEvent)
    {
        StringBuilder eventTimeSpan = new StringBuilder();
        eventTimeSpan.append(getClockTime(currentEvent.getStartTime()));
        eventTimeSpan.append(" - ");
        eventTimeSpan.append(getClockTime(currentEvent.getEndTime()));
        return eventTimeSpan.toString();
    }
    
    private String getClockTime (Calendar cal)
    {
        return String.format("%1$tl:%<tM %<Tp", cal);
    }

    private Map<String, List<Event>> sortByDayOfWeek (List<Event> eventList)
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

    private String getDayOfWeek (Event currentEvent)
    {
        StringBuilder eventDay = new StringBuilder();
        Calendar start = currentEvent.getStartTime();
        eventDay.append(daysList.get(start.get(Calendar.DAY_OF_WEEK) - 1));
        return (eventDay.toString());
    }

    private static List<String> initializeDayList ()
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
