package arraymapping.src;

import arraymapping.util.Utilities;

/**
 * Represents element keyword in queries
 */
public class Element extends AbstractExpression {

    /**
     * Constructor. Parses raw string
     * @param rawString
     */
    public Element(String rawString){
        super(rawString);
        parseContent();
    }

    /**
     * Checks if the keyword is spelled rights
     * Otherwise, throws SYNTAX_ERROR
     * @see SYNTAX_ERROR
     */
    @Override
    protected void parseContent(){
        if (!rawString.equals("element")){
            throw new SYNTAX_ERROR("Syntax error. Cannot resolve \"" + rawString + "\".");
        }
        returnType = Utilities.ReturnType.ARITHMETIC;
    }

    @Override
    public String toString(){
        return "element";
    }
}