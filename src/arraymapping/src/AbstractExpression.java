package arraymapping.src;

import arraymapping.util.Utilities;

/**
 * Represents superclass for expression types used in functions
 */
public abstract class AbstractExpression {

    protected String rawString; //Raw string with expression passed in constructor
    protected Utilities.ReturnType returnType; //return type of expression

    /**
     * Initializes rawString field
     * @param rawString expression string
     */
    protected AbstractExpression(String rawString){
        this.rawString = rawString;
    }

    protected AbstractExpression(){

    }

    public Utilities.ReturnType getReturnType(){
        return returnType;
    }

    /**
     * Parses raw expression string into expression object
     */
    protected abstract void parseContent();
}