package tivoo.input;

import java.util.List;
import tivoo.Event;

public interface XMLParser
{

    List<Event> parse (String fileName);
    
}
