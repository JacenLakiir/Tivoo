package tivoo.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import tivoo.Event;


public class CalendarParser
{
    private static SAXParserFactory SAX_PARSER_FACTORY =
        SAXParserFactory.newInstance();

    private final static Class<? extends TypeCheckHandler>[] TYPE_CHECKERS =
        (Class<? extends TypeCheckHandler>[]) (new Class[] { DukeCalTypeCheckHandler.class });

    private final static Class<? extends ParserHandler>[] PARSERS =
        (Class<? extends ParserHandler>[]) (new Class[] {
                DukeCalParserHandler.class,
                TVParserHandler.class });


    private static InputSource getInputSource (String fileName)
        throws IOException
    {
        File fileHandle = new File(fileName);
        InputStream inputStream = new FileInputStream(fileHandle);
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        InputSource inputSource = new InputSource(reader);
        inputSource.setEncoding("UTF-8");
        return inputSource;
    }


    public static List<Event> parse (String fileName)
        throws SAXException,
            IOException,
            ParserConfigurationException
    {

        SAXParser parser = SAX_PARSER_FACTORY.newSAXParser();
        for (int i = 0; i < TYPE_CHECKERS.length; i++)
        {
            try
            {
                TypeCheckHandler typeCheckHandler =
                    TYPE_CHECKERS[i].newInstance();
                parser.parse(getInputSource(fileName), typeCheckHandler);
                parser.reset();
                if (typeCheckHandler.isValid())
                {
                    ParserHandler parserHandler = PARSERS[i].newInstance();
                    parser.parse(getInputSource(fileName), parserHandler);
                    return parserHandler.getEvents();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
}
