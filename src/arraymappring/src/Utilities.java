package arraymappring.src;

public class Utilities {

    public enum Operations {
        ADDITION,
        SUBSTRACTION,
        MULTIPLICATION,
        LESS,
        MORE,
        EQUALS,
        AND,
        OR
    }

    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'};

    public static boolean checkAllowedDigigt(char symbol){
        for (char ch : digits) {
            if (ch == symbol){
                return true;
            }
        }
        return false;
    }
}
