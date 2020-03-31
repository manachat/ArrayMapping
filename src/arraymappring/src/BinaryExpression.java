package arraymappring.src;

public class BinaryExpression extends Expression {
    private Utilities.Operations operation;
    private Expression left;
    private Expression right;

    public BinaryExpression(){

    }

    public BinaryExpression(Expression leftOperand, Expression rightOperand, Utilities.Operations op){
        left = leftOperand;
        right = rightOperand;
        operation = op;
    }

    public void setLeftOp(Expression left){
        this.left = left;
    }

    public void setRightOp(Expression right){
        this.right = right;
    }

    public void setOperation(Utilities.Operations op){
        operation = op;
    }

    public Expression getLeftOp(){
        return left;
    }

    public Expression getRightOp(){
        return right;
    }

    public Utilities.Operations getOperation(){
        return operation;
    }
}
