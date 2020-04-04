package arraymapping.src;

import arraymapping.util.Utilities;

public class ConstantExpression extends AbstractExpression {

    public ConstantExpression(String content){
        super(content);
        parseContent();
    }

    private int number;

    @Override
    protected void parseContent(){
        try{
            number = Integer.parseInt(content);
        }
        catch (NumberFormatException ex){
            throw new SYNTAX_ERROR();
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