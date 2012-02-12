package tivoo.filtering;

import tivoo.Event;
import java.util.List;

public interface Filter
{
    
    List<Event> filterByKeyword (String keyword, List<Event> current);
        
}
