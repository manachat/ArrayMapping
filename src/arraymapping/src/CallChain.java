package arraymapping.src;

import arraymapping.util.Utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

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

    //TODO: delet
    public void addCall(Call call){
        callChain.add(call);
    }

    /**
     * For test purposes mostly
     * @return list of calls
     */
    public List<Call> getCallChain(){
        return callChain;
    }


    /**
     * Parses call-chain sequence
     * @param rawString raw string containing call chain
     * @return CallChain parsed and reorganized object
     */
    public CallChain(String rawString) {
        if (rawString.isEmpty()){
            throw new SYNTAX_ERROR();
        }
        char[] input = rawString.toCharArray();
        Call currentCall;
        Condition condition = Condition.WORD;
        FunctionType functionType = FunctionType.SEPARATOR;
        StringBuilder function = new StringBuilder(7); //map: 3 letters, filter: 6 letters, %>%: 3 letters
        StringBuilder content = new StringBuilder();


        for (char currChar : input){
            switch (condition){
                case WORD:
                    if (currChar == '{'){
                        if (function.toString().equals("map")){
                            functionType = FunctionType.MAP;
                        }
                        else if (function.toString().equals("filter")){
                            functionType = FunctionType.FILTER;
                        }
                        else {
                            throw new SYNTAX_ERROR();
                        }
                        condition = Condition.CONTENT;
                        function = new StringBuilder(7);
                    }
                    else {
                        function.append(currChar);
                    }
                    break;
                case CONTENT:
                    if (currChar == '}'){
                        if (functionType == FunctionType.MAP){
                            currentCall = new MapCall(content.toString());
                        }
                        else{
                            currentCall = new FilterCall(content.toString());
                        }

                        addCall(currentCall);
                        content = new StringBuilder();
                        condition = Condition.INTERMEDIATE;
                        functionType = FunctionType.SEPARATOR;
                    }
                    else {
                        content.append(currChar);
                    }
                    break;
                case INTERMEDIATE:
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
                            throw new SYNTAX_ERROR();
                        }
                    }
                    break;
            }
        }

        if (condition == Condition.INTERMEDIATE){
            if (!function.toString().isEmpty()){
                throw new SYNTAX_ERROR();
            }
        }
        else{
            throw new SYNTAX_ERROR();
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
        MapCall mapArgegation = null; //all the mappings composed until current moment
        FilterCall filterAgregation = null; //conjunction of previous filters
        while (chainIterator.hasNext()){
            current = chainIterator.next();
            if (current instanceof MapCall){
                if (mapArgegation == null){
                    mapArgegation = (MapCall)current;
                }
                else{
                    replaceElements(current, mapArgegation); //replace elements
                    mapArgegation = (MapCall)current;
                }
            }
            else {
                if (filterAgregation == null){
                    filterAgregation = (FilterCall)current;
                    if (mapArgegation != null){
                        replaceElements(current, mapArgegation);
                    }
                }
                else{
                    if (mapArgegation != null) {
                        replaceElements(current, mapArgegation);
                    }
                    BinaryExpression conjunction = new BinaryExpression(filterAgregation.expression, Utilities.Operation.AND, current.expression);
                    filterAgregation.setExpression(conjunction);
                }
            }
        }
        if (filterAgregation == null){
            filterAgregation = new FilterCall("(0<1)");
        }
        if(mapArgegation == null){
            mapArgegation = new MapCall("element");
        }
        callChain.clear();
        callChain.add(filterAgregation);
        callChain.add(mapArgegation);
    }

    /**
     * Helper method. Replaces all elements in current expression with mapAgregation expression
     * @param current call with replaced elements
     * @param agregation expression to be placed
     */
    private void replaceElements(Call current, Call agregation){
        if (!(current.getExpression() instanceof BinaryExpression)){
            return;
        }
        BinaryExpression traversal = (BinaryExpression)current.getExpression();
        Stack<BinaryExpression> traversalStack = new Stack<>();
        boolean leftCheck = false;
        boolean rightCheck = false;
        //traversalStack.push(traversal);
        //traversal = (BinaryExpression)traversal.getLeftOp();
        while (!traversalStack.empty() || !leftCheck || !rightCheck){
            if (!leftCheck) {
                if (traversal.getLeftOp() instanceof BinaryExpression) {
                    traversalStack.push(traversal);
                    traversal = (BinaryExpression) traversal.getLeftOp();
                }
                else{
                    if (traversal.getLeftOp() instanceof Element){
                        traversal.setLeftOp(agregation.getExpression());
                    }
                    leftCheck = true;
                }
            }
            else if (!rightCheck){
                if (traversal.getRightOp() instanceof BinaryExpression) {
                    traversalStack.push(traversal);
                    traversal = (BinaryExpression) traversal.getRightOp();
                }
                else{
                    if (traversal.getRightOp() instanceof Element){
                        traversal.setRightOp(agregation.getExpression());
                    }
                    rightCheck = true;
                }
            }
            else {
                BinaryExpression temp = traversalStack.peek();
                if (temp.getLeftOp() == traversal){ //we returned from left child
                    leftCheck = false;
                    rightCheck = false;
                    if (temp.getRightOp() instanceof BinaryExpression) {
                        traversal = (BinaryExpression) temp.getRightOp(); //traverse to right
                    }
                    else {
                        leftCheck = true;
                        rightCheck = false;
                        traversal = traversalStack.pop();
                    }
                }
                else { //we returned from right child
                    leftCheck = rightCheck = true;
                    traversal = traversalStack.pop();
                }
            }
        }
    }
}