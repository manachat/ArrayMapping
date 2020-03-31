package arraymappring.src;

public class Element extends Expression {
    private StringBuilder content = new StringBuilder();

    @Override
    public void appendContent(char ch){
        content.append(ch);
    }
}
