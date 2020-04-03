package arraymappring.src;

public class FilterCall extends Call {

    public FilterCall(String content){
        super(content);
    }

    @Override
    public String toString(){
        return "filter{" + expression.toString() + "}";
    }
}
