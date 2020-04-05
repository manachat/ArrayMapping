package arraymapping.src;

import arraymapping.util.Utilities;

/**
 * Class represents filter function
 */
public class FilterCall extends Call {

    /**
     * Constructor. Parses raw string and creates expression.
     * If expression type is incompatible, throws TYPE_ERROR
     * @param rawString
     */
    public FilterCall(String rawString){
        super(rawString);
        if (expression.returnType != Utilities.ReturnType.LOGICAL){ //checks type of parsed expression
            throw new TYPE_ERROR("Type error. \"filter\" cannot be applied to arithmetic expression.");
        }
    }

    @Override
    public String toString(){
        return "filter{" + expression.toString() + "}";
    }
}
