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
        StringBuilder operationWord = new StringBuilder(7); //map: 3 letters | filter 6 letters
        Call currentCall;
        Condition currentCondition = Condition.CALL_PARSE;
        for(int charIndex = 0; charIndex < input.length(); charIndex++){
            switch (currentCondition){
                case CALL:
                    operationWord = new StringBuilder(7);
                    break;
                case CALL_PARSE:
                    if (input.charAt(charIndex) == '{'){ //change condition
                        if (operationWord.toString().equals("map")){
                            currentCall = new MapCall();
                        }
                        else if (operationWord.toString().equals("filter")){
                            currentCall = new FilterCall();
                        }
                        else
                            throw new SYNTAX_ERROR();
                        currentCondition = Condition.EXPRESSION;
                    }
                    else{//continue
                        operationWord.append(input.charAt(charIndex));
                    }
                    break;
                case FILTER_CALL:
                    break;
                case MAP_CALL:
                    break;
                case EXPRESSION:
                    if (input.charAt(charIndex) == '('){ //binary expression
                        //create binary expression and apply result to its left
                        currentCondition = Condition.LEFT;

                    }

            }
        }


        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
