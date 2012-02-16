package tivoo.output;

import java.io.IOException;
import java.util.List;
import tivoo.Event;


public interface HTMLBuilder
{
    public void buildSummaryPage (String summaryPageFileName,
                                  String detailPageDirectory,
                                  List<Event> eventList) throws IOException;


    public void buildDetailsPages (String detailPageDirectory,
                                   List<Event> eventList) throws IOException;

}
