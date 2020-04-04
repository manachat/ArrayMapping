package arraymapping.src;

import arraymapping.util.Utilities;

public class MapCall extends Call {

    public MapCall(String content){
        super(content);
        if (expression.returnType != Utilities.ReturnType.ARITHMETIC){
            throw new TYPE_ERROR();
        }
    }

    @Override
    public String toString(){
        return "map{" + expression.toString() + "}";
    }
}
