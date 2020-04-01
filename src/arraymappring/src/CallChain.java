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

    public CallChain parseFunctions(String input){
        CallChain result = new CallChain();
        Call currentCall = new Call();
        Condition condition = Condition.WORD;
        StringBuilder function = new StringBuilder(7); //map: 3 letters, filter: 6 letters, %>%: 3 letters
        StringBuilder content = new StringBuilder();


        for (int charIndex = 0; charIndex < input.length(); charIndex++){
            char currChar = input.charAt(charIndex);
            switch (condition){
                case WORD:
                    if (currChar == '{'){
                        if (function.toString().equals("map")){
                            currentCall = new MapCall(content);
                        }
                        else if (function.toString().equals("filter")){
                            currentCall = new FilterCall(content);
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
                        currentCall.parseCall(); //кидает error
                        content = new StringBuilder();
                        condition = Condition.INTERMEDIATE;
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
                            condition = Condition.WORD;
                        }
                        else {
                            throw new SYNTAX_ERROR();
                        }
                    }
                    break;
            }
        }





        return result;
    }
}
