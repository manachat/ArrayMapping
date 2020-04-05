package arraymapping.src;

import arraymapping.util.Utilities;

/**
 * Class represents Map function
 */
public class MapCall extends Call {

    /**
     * Constructor. Parses raw string and creates expression
     * If expression type is incompatible, throws TYPE_ERROR
     * @param rawString
     */
    public MapCall(String rawString){
        super(rawString);
        if (expression.returnType != Utilities.ReturnType.ARITHMETIC){//checks type of parsed expression
            throw new TYPE_ERROR("Type error. \"map\" cannot be applied to logical expression.");
        }
    }

    @Override
    public String toString(){
        return "map{" + expression.toString() + "}";
    }
}
