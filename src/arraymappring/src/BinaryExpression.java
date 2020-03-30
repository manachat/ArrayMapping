package arraymappring.src;

public class BinaryExpression extends Expression {
    private Utilities.Operations operation;
    private Expression left;
    private Expression right;

    public BinaryExpression(Expression leftOperand, Expression rightOperand, Utilities.Operations op){
        left = leftOperand;
        right = rightOperand;
        operation = op;
    }
}
