package tivoo;

import java.util.Calendar;

public interface Event
{

    public String getTitle();
    public void setTitle(String title);

    public String getDescription ();
    public void setDescription (String description);
    
    
    public Calendar getStartTime ();
    public void setStartTime (Calendar startTime);

    
    public Calendar getEndTime ();
    public void setEndTime (Calendar endTime);

    
    public String getLocation ();
    public void setLocation (String location);
    
    
}
