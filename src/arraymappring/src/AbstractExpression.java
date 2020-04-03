package arraymappring.src;

public abstract class AbstractExpression {

    protected String content;
    protected Utilities.ReturnType returnType;

    public AbstractExpression(String content){
        this.content = content;
    }

    protected abstract void parseContent();
}
