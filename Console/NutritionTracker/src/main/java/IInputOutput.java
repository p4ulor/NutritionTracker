import java.util.Scanner;
//******* Demonstration purposes *******
public interface IInputOutput { //http://tutorials.jenkov.com/java/interfaces.html
    //All variables in an interface are implicitly public, static and final
    int errorCode = -1;
    static void print(String text){ //can be called without needing an instance that's formed by this interface, contrary to the methods bellow
        System.out.print(text);
    }
    static int getIntInput() {
        //https://stackoverflow.com/questions/13102045/scanner-is-skipping-nextline-after-using-next-or-nextfoo
        //https://www.geeksforgeeks.org/why-is-scanner-skipping-nextline-after-use-of-other-next-functions/
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

    static String getInput(){ // Scanner in = new Scanner(System.in); String input = in.nextLine();  return input;      ->  String input = new Scanner(System.in).nextLine(); return input
        return new Scanner(System.in).nextLine();
    }

    default void printFormat(String s, String s2) { //new printer method that doesnt brake class Console,
      if(s2.isEmpty()) print(String.valueOf(errorCode)); //https://stackoverflow.com/a/654049/9375488
      System.out.printf(s,s2);
    }

    void LCD_Display_print(String txt);
}
