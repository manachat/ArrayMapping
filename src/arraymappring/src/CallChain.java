package arraymappring.src;

import java.util.List;

public class CallChain extends AbstractCall{
    private List<AbstractCall> callChain;

    public void addCall(AbstractCall call){
        callChain.add(call);
    }
}
