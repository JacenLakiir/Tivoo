package tivoo.input.typeChecker;

import tivoo.input.parserHandler.SAXHandler;


public abstract class TypeCheckHandler extends SAXHandler
{
    public abstract boolean isValid ();
}
