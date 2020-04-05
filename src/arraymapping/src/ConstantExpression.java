package arraymapping.src;

import arraymapping.util.Utilities;

/**
 * Class represents constant integer value in queries
 */
public class ConstantExpression extends AbstractExpression {

    /**
     * Constructor. Parses raw string.
     * @param rawString
     */
    public ConstantExpression(String rawString){
        super(rawString);
        parseContent();
    }

    private int number; //integer value of constant

    /**
     * Tries to parse integer value.
     * Throws SYNTAX_ERROR in case of failure.
     * @see SYNTAX_ERROR
     */
    @Override
    protected void parseContent(){
        try{
            number = Integer.parseInt(rawString);
        }
        catch (NumberFormatException ex){
            throw new SYNTAX_ERROR("Syntax error. Wrong number format, cannot resolve \"" + rawString + "\".");
        }
        returnType = Utilities.ReturnType.ARITHMETIC;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString(){
        return "" + number;
    }
}