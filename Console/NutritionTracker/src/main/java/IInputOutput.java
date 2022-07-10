import java.util.Scanner;

public interface IInputOutput {

    Scanner input = new Scanner(System.in);
    int errorCode = -1;
    static void print(String text){
        System.out.print(text);
    }
    static int getIntInput() {
        int n=0;
        try {
            String s = getInput();
            if(s.isEmpty()) throw new NumberFormatException("String is empty");
            n = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            e.getMessage();
        }
        return n;
    }

    static String getInput() {
        return input.nextLine();
    }

    static void printFormat(String s, String s2) {
      if(s2.isEmpty()) print(String.valueOf(errorCode));
      else System.out.printf(s,s2);
    }
}
