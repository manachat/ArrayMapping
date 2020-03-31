package arraymappring.src;

import java.util.ArrayList;
import java.util.List;

public class CallChain extends AbstractCall{
    private List<AbstractCall> callChain = new ArrayList<>();

    public void addCall(AbstractCall call){
        callChain.add(call);
    }
}
