package tivoo;

import java.util.Calendar;


public class EventImpl implements Event
{
    private String title;
    private String description;
    private Calendar startTime;
    private Calendar endTime;
    private String location;


    @Override
    public String getTitle ()
    {
        return title;
    }


    @Override
    public String getDescription ()
    {
        return description;
    }


    @Override
    public Calendar getStartTime ()
    {
        return startTime;
    }


    @Override
    public Calendar getEndTime ()
    {
        return endTime;
    }


    @Override
    public String getLocation ()
    {
        return location;
    }


    public void setTitle (String title)
    {
        this.title = title;
    }


    public void setDescription (String description)
    {
        this.description = description;
    }


    public void setStartTime (Calendar startTime)
    {
        this.startTime = startTime;
    }


    public void setEndTime (Calendar endTime)
    {
        this.endTime = endTime;
    }


    public void setLocation (String location)
    {
        this.location = location;
    }

}
