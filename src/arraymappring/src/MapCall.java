package arraymappring.src;

public class MapCall extends Call {

    public MapCall(String content){
        super(content);

    }

    @Override
    public String toString(){
        return "map{" + expression.toString() + "}";
    }
}
