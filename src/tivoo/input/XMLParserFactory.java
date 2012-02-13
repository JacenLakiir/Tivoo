package tivoo.input;

public class XMLParserFactory
{
    public static XMLParser getParser ()
    {
        return new DukeCalParser();
    }    
}
