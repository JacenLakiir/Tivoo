package tivoo.output;

import java.io.IOException;
import java.util.List;
import tivoo.Event;

public class CalendarViewHTMLBuilder extends HTMLBuilder
{

    private String myPageFileName;
    
    public CalendarViewHTMLBuilder (String pageFileName)
    {
        myPageFileName = pageFileName;
    }
    
    @Override
    public void buildHTML (List<Event> eventList) throws IOException
    {
        // TODO Auto-generated method stub
        
    }

}