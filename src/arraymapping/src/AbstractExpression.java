package arraymapping.src;

import arraymapping.util.Utilities;

public abstract class AbstractExpression {

    protected String content;
    protected Utilities.ReturnType returnType;

    public AbstractExpression(String content){
        this.content = content;
    }

    public Utilities.ReturnType getReturnType(){
        return returnType;
    }

    protected abstract void parseContent();
}