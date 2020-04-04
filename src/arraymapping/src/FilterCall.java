package arraymapping.src;

import arraymapping.util.Utilities;

public class FilterCall extends Call {

    public FilterCall(String content){
        super(content);
        if (expression.returnType != Utilities.ReturnType.LOGICAL){
            throw new TYPE_ERROR();
        }
    }

    @Override
    public String toString(){
        return "filter{" + expression.toString() + "}";
    }
}
