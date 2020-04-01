package arraymappring.src;

public class Call extends AbstractCall {
    protected Expression expression;
    protected StringBuilder content;

    public Call(StringBuilder content){
        this.content = content;
        parseCall();
    }

    public Call(){

    }

    public void parseCall(){

    }
}
