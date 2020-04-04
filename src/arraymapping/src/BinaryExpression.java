package arraymapping.src;

import arraymapping.util.Utilities;

public class BinaryExpression extends AbstractExpression {
    private Utilities.Operation operation;
    private AbstractExpression left;
    private AbstractExpression right;

    public BinaryExpression(String content){
        super(content);
        parseContent();
        checkOperandTypes();
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
                    else {
                        throw new SYNTAX_ERROR();
                    }
                    break;
                case LEFT_ELEMENT:
                    if (Utilities.isOperation(currChar)){
                        left = new Element(content.substring(0, currIndex));
                        operation = Utilities.charToOperation(currChar);
                        condition = Condition.RIGHT;
                    }
                    break;
                case LEFT_CONSTANT:
                    if (Utilities.isOperation(currChar)){
                        left = new ConstantExpression(content.substring(0, currIndex));
                        operation = Utilities.charToOperation(currChar);
                        condition = Condition.RIGHT;
                    }

                    break;
                case LEFT_BINARY:
                    if (ignoreBrackets == 0){
                        left = new BinaryExpression(content.substring(1, currIndex - 1));
                        operation = Utilities.charToOperation(currChar);
                        condition = Condition.RIGHT;
                    }
                    else if (currChar == '('){
                        ignoreBrackets++;
                    }
                    else if (currChar == ')'){
                        ignoreBrackets--;
                    }
                    break;
                case RIGHT:
                    if (currChar == 'e'){
                        right = new Element(content.substring(currIndex));
                    }
                    else if (Utilities.isDigit(currChar)){
                        right = new ConstantExpression(content.substring(currIndex));
                    }
                    else if (currChar == '('){
                        right = new BinaryExpression(content.substring(currIndex + 1, content.length() - 1));
                    }
                    else {
                        throw new SYNTAX_ERROR();
                    }
                    returnType = setReturnType(operation);
                    return;
            }
            currIndex++;
        }
        if (condition != Condition.RIGHT){
            throw new SYNTAX_ERROR();
        }
    }

    private void checkOperandTypes(){
        if (operation.ordinal() < 5){ //arithmetic operations
            if (left.getReturnType() != Utilities.ReturnType.ARITHMETIC || right.getReturnType() != Utilities.ReturnType.ARITHMETIC){
                throw new TYPE_ERROR();
            }
        }
        else if (operation.ordinal() > 5){ //boolean operations
            if (left.getReturnType() != Utilities.ReturnType.LOGICAL || right.getReturnType() != Utilities.ReturnType.LOGICAL){
                throw new TYPE_ERROR();
            }
        }
        else { // "=" operation, I assumed that "true=false" is legal option
            if (left.getReturnType() != right.getReturnType()){
                throw new TYPE_ERROR();
            }
        }
    }

    public AbstractExpression getLeftOp(){
        return left;
    }

    public AbstractExpression getRightOp(){
        return right;
    }

    public Utilities.Operation getOperation(){
        return operation;
    }

    private Utilities.ReturnType setReturnType(Utilities.Operation operation){
        return operation.ordinal() < 3 ? Utilities.ReturnType.ARITHMETIC : Utilities.ReturnType.LOGICAL;
    }

    @Override
    public String toString(){
        return "(" + left.toString() + Utilities.operationToString(operation) + right.toString() + ")";
    }

}