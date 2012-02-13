package tivoo.input;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import tivoo.Event;

public interface XMLParser
{

    List<Event> parse (String fileName) throws SAXException, IOException, ParserConfigurationException;
    
}
