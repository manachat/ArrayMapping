package arraymapping.src;

import arraymapping.util.Utilities;

public class Call extends AbstractCall {
    protected AbstractExpression expression;
    protected String content;


    public Call(String content){
        this.content = content;
        parseCall();
    }

    private void parseCall(){
        char firstChar = content.charAt(0);
        if (firstChar == 'e'){
            expression = new Element(content);
        }
        else if (Utilities.isDigit(firstChar)){
            expression = new ConstantExpression(content);
        }
        else if (firstChar == '('){
            expression = new BinaryExpression(content.substring(1, content.length() - 1)); //trim ( and )
        }
        else {
            throw new SYNTAX_ERROR();
        }
    }
}