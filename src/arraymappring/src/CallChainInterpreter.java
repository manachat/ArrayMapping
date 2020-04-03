package arraymappring.src;

public class CallChainInterpreter {
    private enum Condition{
        CALL,
        CALL_PARSE,
        FILTER_CALL,
        MAP_CALL,
        EXPRESSION,
        ELEMENT,
        CONST_EXPR,
        LEFT,
        L_ELEMENT,
        L_CONSTANT,
        OPERATION,
        R_CONSTANT,
        R_ELEMENT,
        RIGHT,
        EXIT
    }

    public static CallChain parseString(String input){
        Call currentCall;
        StringBuilder operationWord = new StringBuilder(7); //map: 3 letters | filter 6 letters
        StringBuilder expressionContent = new StringBuilder();
        //AbstractExpression currentExpression = new AbstractExpression();
        Condition currentCondition = Condition.CALL_PARSE;

        /*
        for(int charIndex = 0; charIndex < input.length(); charIndex++){
            switch (currentCondition){
                case CALL:
                    operationWord = new StringBuilder(7);
                    operationWord.append(input.charAt(charIndex));
                    currentCondition = Condition.CALL_PARSE;
                    break;
                case CALL_PARSE:
                    if (input.charAt(charIndex) == '{'){ //change condition
                        if (operationWord.toString().equals("map")){
                            currentCall = new MapCall();
                        }
                        else if (operationWord.toString().equals("filter")){
                            currentCall = new FilterCall();
                        }
                        else {
                            throw new SYNTAX_ERROR();
                        }
                        result.addCall(currentCall);
                        currentCondition = Condition.EXPRESSION;
                    }
                    else{//continue
                        operationWord.append(input.charAt(charIndex));
                    }
                    break;
                case EXPRESSION:
                    if (input.charAt(charIndex) == '('){ //binary expression
                        //create binary expression and apply result to its left
                        currentExpression = new BinaryExpression();
                        currentCondition = Condition.LEFT;
                    }
                    else if(input.charAt(charIndex) == 'e'){
                        currentExpression = new Element();
                        currentExpression.appendContent(input.charAt(charIndex));
                        currentCondition = Condition.ELEMENT;
                    }
                    else if(Utilities.checkAllowedDigigt(input.charAt(charIndex))){
                        currentExpression = new ConstantExpression();
                        currentExpression.appendContent(input.charAt(charIndex));
                        currentCondition = Condition.CONST_EXPR;
                    }
                    else if(input.charAt(charIndex) == ' '){
                        //space is allowed symbol before expression inside { } (maybe)
                    }
                    else {
                        throw new SYNTAX_ERROR();
                    }
                    break;
                case ELEMENT:
                case CONST_EXPR:
                    if (input.charAt(charIndex) != '}'){
                        currentExpression.appendContent(input.charAt(charIndex));
                    }
                    else {
                        currentCondition = Condition.EXIT;
                    }
                    break;
                case LEFT:
                    if (input.charAt(charIndex) == ' '){

                    }
                    else if (input.charAt(charIndex) == '('){

                    }
                    break;
            }
        }


         */

        return null;
    }



    @Override
    public String toString() {
        return super.toString();
    }
}
