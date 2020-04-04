package arraymapping.util;

import arraymapping.src.SYNTAX_ERROR;

public class Utilities {

    public enum Operation {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        LESS,
        MORE,
        EQUALS,
        AND,
        OR
    }

    public static String operationToString(Operation operation){
        /*switch (operation){
            case OR:
                return "|";
            case AND:
                return "&";
            case EQUALS:
                return "=";
            case MORE:
                return ">";
            case LESS:
                return "<";
            case MULTIPLICATION:
                return "*";
            case SUBTRACTION:
                return "-";
            case ADDITION:
                return "+";
        }
         */
        return "" + operations[operation.ordinal()];
    }

    public enum ReturnType{
        LOGICAL,
        ARITHMETIC
    }

    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'};

    public static boolean isDigit(char symbol){
        for (char ch : digits) {
            if (ch == symbol){
                return true;
            }
        }
        return false;
    }

    private static final char[] operations = {'+', '-', '*', '<', '>', '=', '&', '|'};

    public static boolean isOperation(char symbol){
        for (char ch : operations){
            if (ch == symbol){
                return true;
            }
        }
        return false;
    }

    public static Operation charToOperation(char symbol) {
        switch (symbol){
            case '+':
                return Operation.ADDITION;
            case '-':
                return Operation.SUBTRACTION;
            case '*':
                return Operation.MULTIPLICATION;
            case '<':
                return Operation.LESS;
            case '>':
                return Operation.MORE;
            case '=':
                return Operation.EQUALS;
            case '&':
                return Operation.AND;
            case '|':
                return Operation.OR;
            default:
                throw new SYNTAX_ERROR();
        }
    }
}