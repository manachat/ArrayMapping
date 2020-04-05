package arraymapping.src;

import arraymapping.util.Utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Class represents call chain of functions
 */
public class CallChain extends AbstractCall{
    private List<Call> callChain = new ArrayList<>();

    //conditions for raw string parsing
    private enum Condition {
        WORD,
        CONTENT,
        INTERMEDIATE
    }

    //function types for parser
    private enum FunctionType{
        FILTER,
        MAP,
        SEPARATOR
    }

    /**
     * For test purposes mostly
     * @return list of calls
     */
    public List<Call> getCallChain(){
        return callChain;
    }


    /**
     * Constructor. Parses call-chain query
     * @param rawString raw string containing call chain
     */
    public CallChain(String rawString) {
        if (rawString.isEmpty()){
            throw new SYNTAX_ERROR("Syntax error. Query is empty");
        }
        char[] input = rawString.toCharArray();
        Call currentCall;
        Condition condition = Condition.WORD;
        FunctionType functionType = FunctionType.SEPARATOR;
        StringBuilder function = new StringBuilder(7); //map: 3 letters, filter: 6 letters, %>%: 3 letters
        StringBuilder content = new StringBuilder();


        for (char currChar : input){
            switch (condition){
                case WORD: //function word is undefined
                    if (currChar == '{'){ //we met open brace of function
                        //function type is used to pass the content to constructor later
                        if (function.toString().equals("map")){
                            functionType = FunctionType.MAP;
                        }
                        else if (function.toString().equals("filter")){
                            functionType = FunctionType.FILTER;
                        }
                        else {
                            throw new SYNTAX_ERROR("Syntax error. Unknown function \"" + function.toString() + "\"");
                        }
                        condition = Condition.CONTENT;
                        function = new StringBuilder(7);
                    }
                    else {
                        function.append(currChar);
                    }
                    break;
                case CONTENT: //copy content inside { }
                    if (currChar == '}'){ //end of func. body
                        if (functionType == FunctionType.MAP){
                            currentCall = new MapCall(content.toString());
                        }
                        else{
                            currentCall = new FilterCall(content.toString());
                        }
                        callChain.add(currentCall);
                        content = new StringBuilder();
                        condition = Condition.INTERMEDIATE;
                        functionType = FunctionType.SEPARATOR;
                    }
                    else {
                        content.append(currChar);
                    }
                    break;
                case INTERMEDIATE: //symbols between functions
                    if (currChar == '%' || currChar == '>'){
                        function.append(currChar);
                    }
                    else {
                        if (function.toString().equals("%>%")){
                            function = new StringBuilder(7);
                            function.append(currChar); //first letter of func name
                            condition = Condition.WORD;
                        }
                        else {
                            throw new SYNTAX_ERROR("Syntax error. Cannot resolve \"" + function.toString() + "\"");
                        }
                    }
                    break;
            }
        }

        if (condition == Condition.INTERMEDIATE){ //check if nothing left after last }
            if (!function.toString().isEmpty()){
                throw new SYNTAX_ERROR("Syntax error. Cannot resolve \"" + function.toString() + "\".");
            }
        }
        else{ //parsing should end correctly on INTERMEDIATE condition
            throw new SYNTAX_ERROR("Syntax error.");
        }
    }

    @Override
    public String toString(){
        Iterator<Call> chainIterator = callChain.iterator();
        AbstractCall current;
        StringBuilder result = new StringBuilder();
        while (chainIterator.hasNext()){
            current = chainIterator.next();
            result.append(current.toString());
            if (chainIterator.hasNext()){
                result.append("%>%");
            }
        }
        return result.toString();
    }

    /**
     * Turns call-chain sequence into chain of two functions filter%>%map
     */
    public void refactor(){
        Iterator<Call> chainIterator = callChain.iterator();
        Call current;
        MapCall mapAggregation = null; //all the mappings composed until current moment
        FilterCall filterAggregation = null; //conjunction of all previous filters
        while (chainIterator.hasNext()){
            current = chainIterator.next();
            if (current instanceof MapCall){
                if (mapAggregation == null){
                    mapAggregation = (MapCall)current; //first map function
                }
                else{//replace "element" with aggregated map calls (composition of functions)
                    replaceElements(current, mapAggregation);
                    mapAggregation = (MapCall)current;
                }
            }
            else {
                if (filterAggregation == null){ //first filter function
                    filterAggregation = (FilterCall)current;
                    if (mapAggregation != null){
                        replaceElements(current, mapAggregation);
                    }
                }
                else{ //replace elements and make conjunction with previous filters
                    if (mapAggregation != null) {
                        replaceElements(current, mapAggregation);
                    }
                    BinaryExpression conjunction = new BinaryExpression(filterAggregation.expression, Utilities.Operation.AND, current.expression);
                    filterAggregation.setExpression(conjunction);
                }
            }
        }
        if (filterAggregation == null){ //no filter calls
            filterAggregation = new FilterCall("(0<1)");
        }
        if(mapAggregation == null){ //no element calls
            mapAggregation = new MapCall("element");
        }
        callChain.clear();
        callChain.add(filterAggregation);
        callChain.add(mapAggregation);
    }

    /**
     * Helper method. Replaces all elements in current expression with mapAgregation expression
     * @param current call with replaced elements
     * @param aggregation expression to be placed
     */
    private void replaceElements(Call current, Call aggregation){
        if (!(current.getExpression() instanceof BinaryExpression)){
            if (current.getExpression() instanceof Element){
                current.setExpression(aggregation.getExpression()); //expression in current IS element
            }
            return;
        }
        BinaryExpression traversal = (BinaryExpression)current.getExpression(); //current element in traversal of expressions tree
        Stack<BinaryExpression> traversalStack = new Stack<>();
        boolean leftCheck = false; //left child checked
        boolean rightCheck = false; //rigth child checked
        while (!traversalStack.empty() || !leftCheck || !rightCheck){
            if (!leftCheck) { //check left child
                if (traversal.getLeftOp() instanceof BinaryExpression) { //going down
                    traversalStack.push(traversal);
                    traversal = (BinaryExpression) traversal.getLeftOp();
                }
                else{
                    if (traversal.getLeftOp() instanceof Element){ //replace element
                        traversal.setLeftOp(aggregation.getExpression());
                    }
                    leftCheck = true;
                }
            }
            else if (!rightCheck){ //check right child
                if (traversal.getRightOp() instanceof BinaryExpression) { //going down
                    traversalStack.push(traversal);
                    traversal = (BinaryExpression) traversal.getRightOp();
                }
                else{
                    if (traversal.getRightOp() instanceof Element){
                        traversal.setRightOp(aggregation.getExpression());
                    }
                    rightCheck = true;
                }
            }
            else { //both children checked
                BinaryExpression temp = traversalStack.peek();
                if (temp.getLeftOp() == traversal){ //we returned from left child
                    leftCheck = false;
                    rightCheck = false;
                    if (temp.getRightOp() instanceof BinaryExpression) {
                        traversal = (BinaryExpression) temp.getRightOp(); //traverse to right
                    }
                    else { //going up and set right to unchecked
                        leftCheck = true;
                        rightCheck = false;
                        traversal = traversalStack.pop();
                    }
                }
                else { //we returned from right child
                    leftCheck = rightCheck = true;
                    traversal = traversalStack.pop(); //going up
                }
            }
        }
    }
}