package arraymappring.src;

import java.util.ArrayList;
import java.util.List;

public class CallChain extends AbstractCall{
    private List<AbstractCall> callChain = new ArrayList<>();

    public void addCall(AbstractCall call){
        callChain.add(call);
    }

    private enum Condition {
        WORD,
        CONTENT,
        INTERMEDIATE
    }

    private enum FunctionType{
        FILTER,
        MAP,
        SEPARATOR
    }


    /**
     * Divides raw input string into sequence of Call objects
     * @param rawString raw string containing call chain
     * @return CallChain object
     */
    public CallChain(String rawString){
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

        if (condition != Condition.INTERMEDIATE){
            throw new SYNTAX_ERROR();
        }
    }
}
