package arraymapping.src;

import arraymapping.util.Utilities;

/**
 * Class represents binary expression used in queries.
 */
public class BinaryExpression extends AbstractExpression {
    private Utilities.Operation operation; //binary operation
    private AbstractExpression left; //left operand
    private AbstractExpression right; //right operand

    /**
     * Constructor. Parses raw string.
     * @param rawString binary expression WITHOUT outer parentheses. (e.g. "2*element")
     */
    public BinaryExpression(String rawString){
        super(rawString);
        parseContent();
        checkOperandTypes();
    }

    /**
     * Constructor. Creates binary expression from existing expressions.
     * @param left left operand
     * @param op binary operation
     * @param right right operand
     */
    public BinaryExpression(AbstractExpression left, Utilities.Operation op, AbstractExpression right){
        this.left = left;
        this.operation = op;
        this.right = right;
        checkOperandTypes();
    }

    //conditions for parser
    private enum Condition {
        DEFAULT,
        LEFT_ELEMENT,
        LEFT_CONSTANT,
        LEFT_BINARY,
        RIGHT
    }

    /**
     * Parses binary expression
     * throws SYNTAX_ERROR
     * @see SYNTAX_ERROR
     */
    @Override
    protected void parseContent(){
        Condition condition = Condition.DEFAULT;
        char[] input = rawString.toCharArray();
        int ignoreBrackets = 0; //helps to count brackets of left operand
        int currIndex = 0; //character index
        for (char currChar : input){
            switch (condition){
                case DEFAULT: //start condition when left operand is undefined
                    if (currChar == 'e'){ //left op is element
                        condition = Condition.LEFT_ELEMENT;
                    }
                    else if (Utilities.isDigit(currChar)){ //left op is const
                        condition = Condition.LEFT_CONSTANT;
                    }
                    else if (currChar == '('){ //left op is binary
                        condition = Condition.LEFT_BINARY;
                        ignoreBrackets++;
                    }
                    else { //no match
                        throw new SYNTAX_ERROR("Syntax error. Unexpected symbol \"" + currChar + "\".");
                    }
                    break;
                case LEFT_ELEMENT: //content of left op until operation met
                    if (Utilities.isOperation(currChar)){
                        left = new Element(rawString.substring(0, currIndex));
                        operation = Utilities.charToOperation(currChar);
                        condition = Condition.RIGHT;
                    }
                    break;
                case LEFT_CONSTANT: //same as LEFT_ELEMENT
                    if (Utilities.isOperation(currChar)){
                        left = new ConstantExpression(rawString.substring(0, currIndex));
                        operation = Utilities.charToOperation(currChar);
                        condition = Condition.RIGHT;
                    }
                    break;
                case LEFT_BINARY: //content of binary expression
                    if (ignoreBrackets == 0){ //our operation is met when all the brackets have pairs
                        left = new BinaryExpression(rawString.substring(1, currIndex - 1));
                        operation = Utilities.charToOperation(currChar);
                        condition = Condition.RIGHT;
                    }
                    else if (currChar == '('){ //increase brackets number
                        ignoreBrackets++;
                    }
                    else if (currChar == ')'){ //decrease brackets number
                        ignoreBrackets--;
                    }
                    break;
                case RIGHT: //everything else belongs to right op, exit condition
                    if (currChar == 'e'){
                        right = new Element(rawString.substring(currIndex));
                    }
                    else if (Utilities.isDigit(currChar)){
                        right = new ConstantExpression(rawString.substring(currIndex));
                    }
                    else if (currChar == '('){
                        right = new BinaryExpression(rawString.substring(currIndex + 1, rawString.length() - 1));
                    }
                    else {
                        throw new SYNTAX_ERROR("Syntax error. Unexpected symbol \"" + currChar + "\".");
                    }
                    returnType = setReturnType(operation);
                    return;
            }
            currIndex++;
        }
        if (condition != Condition.RIGHT){
            throw new SYNTAX_ERROR("Syntax error.");
        }
    }

    /**
     * Checks if operand types are compatible with operation
     * Otherwise, throws TYPE_ERROR
     * @see TYPE_ERROR
     */
    private void checkOperandTypes(){
        if (operation.ordinal() < 5){ //arithmetic operations
            if (left.getReturnType() != Utilities.ReturnType.ARITHMETIC || right.getReturnType() != Utilities.ReturnType.ARITHMETIC){
                throw new TYPE_ERROR("Type error. Incompatible types for " + Utilities.operationToString(operation) + " operation.");
            }
        }
        else if (operation.ordinal() > 5){ //boolean operations
            if (left.getReturnType() != Utilities.ReturnType.LOGICAL || right.getReturnType() != Utilities.ReturnType.LOGICAL){
                throw new TYPE_ERROR("Type error. Incompatible types for " + Utilities.operationToString(operation) + " operation.");
            }
        }
        else { // "=" operation, I assumed that "true=false" is legal option
            if (left.getReturnType() != right.getReturnType()){
                throw new TYPE_ERROR("Type error. Incompatible types for " + Utilities.operationToString(operation) + " operation.");
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

    public void setLeftOp(AbstractExpression newLeft){
        left = newLeft;
    }

    public void setRightOp(AbstractExpression newRight){
        right = newRight;
    }

    private Utilities.ReturnType setReturnType(Utilities.Operation operation){
        return operation.ordinal() < 3 ? Utilities.ReturnType.ARITHMETIC : Utilities.ReturnType.LOGICAL;
    }

    @Override
    public String toString(){
        return "(" + left.toString() + Utilities.operationToString(operation) + right.toString() + ")";
    }

}