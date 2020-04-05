package arraymapping.src;

import arraymapping.util.Utilities;

/**
 * Represents abstract call function.
 */
public abstract class Call extends AbstractCall {
    protected AbstractExpression expression; //expression inside function
    protected String rawContent; //raw string with expression

    /**
     * Constructor. Parses content inside braces
     * @param rawContent raw function content string
     */
    public Call(String rawContent){
        this.rawContent = rawContent;
        parseCall();
    }

    public AbstractExpression getExpression() {
        return expression;
    }

    void setExpression(AbstractExpression expression){
        this.expression = expression;
    }

    /**
     * Defines expression type inside braces and creates expression object.
     */
    private void parseCall(){
        char firstChar = rawContent.charAt(0);
        if (firstChar == 'e'){
            expression = new Element(rawContent);
        }
        else if (Utilities.isDigit(firstChar)){
            expression = new ConstantExpression(rawContent);
        }
        else if (firstChar == '('){
            expression = new BinaryExpression(rawContent.substring(1, rawContent.length() - 1)); //trim ( and )
        }
        else { //if none of expression types met
            throw new SYNTAX_ERROR("Syntax error. Unexpected symbol \"" + firstChar + "\".");
        }
    }

}