package view;

import java.util.ArrayList;
import java.util.List;

public class HTMLModel
{
    @SuppressWarnings("unused")
    private String myCurrentURL;
    private int myCurrentIndex;
    private List<String> myHistory;

    protected HTMLModel ()
    {
        myCurrentURL = "";
        myCurrentIndex = -1;
        myHistory = new ArrayList<String>();
    }

    protected String next ()
    {
        if (hasNext())
        {
            myCurrentIndex++;
            return myHistory.get(myCurrentIndex);
        }
        return null;
    }

    protected String back ()
    {
        if (hasPrevious())
        {
            myCurrentIndex--;
            return myHistory.get(myCurrentIndex);
        }
        return null;
    }

    protected void go (String url)
    {
        myCurrentURL = url;
        if (hasNext())
        {
            myHistory = myHistory.subList(0, myCurrentIndex + 1); 
        }
        myHistory.add(url);
        myCurrentIndex++;
    }

    protected boolean hasNext ()
    {
        return myCurrentIndex < (myHistory.size() - 1);
    }

    protected boolean hasPrevious ()
    {
        return myCurrentIndex > 0;
    }

}
