package arraymapping.src;

import arraymapping.util.Utilities;

public class Element extends AbstractExpression {

    public Element(String content){
        super(content);
        parseContent();
    }

    @Override
    protected void parseContent(){
        if (!content.equals("element")){
            throw new SYNTAX_ERROR();
        }
        returnType = Utilities.ReturnType.ARITHMETIC;
    }

    @Override
    public String toString(){
        return "element";
    }
}