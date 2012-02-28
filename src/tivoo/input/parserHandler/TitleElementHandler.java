package tivoo.input.parserHandler;

public class TitleElementHandler extends ElementHandler
{
	private ParserHandler myParserHandler;
	public TitleElementHandler(ParserHandler parserHandler){
		myParserHandler = parserHandler;
	}
    @Override
    public void characters (char[] ch, int start, int length)
    {
    	
//        if (currentEvent != null) currentEvent.setTitle(new String(ch,
//                                                                   start,
//                                                                   length));
    	myParserHandler.setTitle(new String(ch, start, length).trim());
    }
}
