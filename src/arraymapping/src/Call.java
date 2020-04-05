package arraymapping.src;

import arraymapping.util.Utilities;

public abstract class Call extends AbstractCall {
    protected AbstractExpression expression;
    protected String rawContent;


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
        else {
            throw new SYNTAX_ERROR();
        }
    }

}