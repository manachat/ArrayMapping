package arraymapping.src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        CallChain call;
        try {
            call = new CallChain(input);
            call.refactor();
            System.out.println(call.toString());
        }
        catch (SYNTAX_ERROR | TYPE_ERROR err){
            System.out.println(err.getMessage());
        }

    }

}
