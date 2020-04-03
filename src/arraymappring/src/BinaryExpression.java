package arraymappring.src;

public class BinaryExpression extends AbstractExpression {
    private Utilities.Operation operation;
    private AbstractExpression left;
    private AbstractExpression right;

    public BinaryExpression(String content){
        super(content);
        parseContent();
    }

    private enum Condition {
        DEFAULT,
        LEFT_ELEMENT,
        LEFT_CONSTANT,
        LEFT_BINARY,
        RIGHT
    }

    @Override
    protected void parseContent(){
        Condition condition = Condition.DEFAULT;
        char[] input = content.toCharArray();
        int ignoreBrackets = 0;
        int currIndex = 0;
        for (char currChar : input){
            switch (condition){
                case DEFAULT:
                    if (currChar == 'e'){
                        condition = Condition.LEFT_ELEMENT;
                    }
                    else if (Utilities.isDigit(currChar)){
                        condition = Condition.LEFT_CONSTANT;
                    }
                    else if (currChar == '('){
                        condition = Condition.LEFT_BINARY;
                        ignoreBrackets++;
                    }
                case LEFT_ELEMENT:
                    if (Utilities.isOperation(currChar)){
                        left = new Element(content.substring(0, currIndex));
                        operation = Utilities.charToOperation(currChar);
                       
                    }
            }
            currIndex++;
        }

    }

    public AbstractExpression getRightOp(){
        return right;
    }

    public Utilities.Operation getOperation(){
        return operation;
    }

    @Override
    public String toString(){
        return "(" + left.toString() + Utilities.operationToString(operation) + right.toString() + ")";
    }

}
