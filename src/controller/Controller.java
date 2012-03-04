package controller;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import tivoo.TivooSystem;
import view.*;

public class Controller
{
    // single instance of controller
    private static Controller myController;  
    private static TivooSystem myModel;
    private static TivooGUI myView;
    private static HTMLViewer myBrowser;
    
    private Controller ()
    {}

    public static Controller getInstance ()
    {
        if (myController == null)
        {
            myController = new Controller();
            myModel = new TivooSystem();
            myView = new TivooGUI();
            myBrowser = null;
        }
        return myController;
    }
    
    public void run () throws SAXException, IOException, ParserConfigurationException
    {
        loadInput();
        applyFilters();
        createViews();
        showViews();
    }
    
    public void reset ()
    {
        myView.setVisible(false);        
        myModel.clearEvents();
        myView = new TivooGUI();
    }
    
    public void restart ()
    {
        myBrowser.setVisible(false);
        myBrowser = null;
        myModel.clearEvents();
        myView = new TivooGUI();
    }
    
    public boolean canRun ()
    {
        if (myView == null)
            return false;     
        List<String> input = myView.getInput();
        List<String> filters = myView.getFilters();
        List<String> views = myView.getOutput();
        return (input.size() > 0 && filters.size() > 0 && views.size() > 0);
    }
    
    public boolean canReset ()
    {
        if (myView == null)
            return false;
        List<String> input = myView.getInput();
        List<String> filters = myView.getFilters();
        List<String> views = myView.getOutput();
        return (input.size() > 0 || filters.size() > 0 || views.size() > 0);
    }
    
    private void loadInput () throws SAXException, IOException, ParserConfigurationException
    {
        List<String> input = myView.getInput();
        for (String xmlFile : input)
        {
            myModel.loadFile(xmlFile);
        }
    }
    
    private void applyFilters ()
    {
        List<String> filters = myView.getFilters();
        for (String filter : filters)
        {
            if (filter.startsWith("filter by keyword"))
            {   
                String keyword = filter.split("\\s+")[3];
                myModel.filterByKeyword(keyword, true);
            }
            if (filter.startsWith("filter by nonkeyword"))
            {
                String nonkeyword = filter.split("\\s+")[3];
                myModel.filterByKeyword(nonkeyword, false);
            }
            if (filter.equals("sort by title"))
                myModel.sortByTitle(false);
            if (filter.equals("sort by start time"))
                myModel.sortByStartTime(false);
            if (filter.equals("sort by end time"))
                myModel.sortByEndTime(false);
            if (filter.equals("reverse sort by title"))
                myModel.sortByTitle(true);
            if (filter.equals("reverse sort by start time"))
                myModel.sortByStartTime(true);
            if (filter.equals("reverse sort by end time"))
                myModel.sortByEndTime(true);
        }
    }
    
    private void createViews () throws IOException
    {
        List<String> views = myView.getOutput();
        for (String view : views)
        {
            String viewURL = "output/" + view;
            if (view.equals("horizontal.html"))
                myModel.outputHorizontalView(viewURL);
            if (view.equals("vertical.html"))
                myModel.outputVerticalView(viewURL);
            if (view.equals("sorted.html"))
                myModel.outputSortedView(viewURL);
            if (view.equals("conflict.html"))
                myModel.outputConflictView(viewURL);
            if (view.equals("calendar.html"))
                myModel.outputCalendarView(viewURL);
        }
    }
    
    private void showViews ()
    {
        List<String> views = myView.getOutput();
        myBrowser = new HTMLViewer(views, views.get(0));
    }
}
