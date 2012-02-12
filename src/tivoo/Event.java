package tivoo;

import java.util.Calendar;

public interface Event
{

    public String getTitle();
    
    public String getDescription ();
    
    public Calendar getStartTime ();
    
    public Calendar getEndTime ();
    
    public String getLocation ();
    
}
