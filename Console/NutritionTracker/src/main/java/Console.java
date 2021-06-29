//This class has the console commands and doesn't really deal with much data. Just calls the class Main to do it
//Almost everything in this whole project is static except for uses of the class Food. That's the only instance we need.
public final class Console implements IInputOutput {
    //                                         0             1          2        3     4     5       6        7           8         9        10         11          12             13      14       15
    private static final String[] cmds = {"printFoods","foodInfo","add","edit","del","sumUp","resetSum","printMeals","mealInfo","addMeal","editMeal","delMeal","printNutrients","reload","extra","commands"};

    private static final String extraInfo = "Type end to finish.\n" +
            "Remember that all foods/meals registered should have their respective and indicated amount of nutrients present in the food per 100g of food.\n" +
            "When loading the data files, the program will ignore food lines that starts with a non-letter char and will perceive \""+Main.commentSymbol+"\" as a comment and will ignore anything after it.\n" +
            "When loading the data files, the program will stop reading when finding a food named \"end\"\n";

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Main.loadCurrentData();
        long stopTime = System.nanoTime();
        print("Load time: "+(stopTime - startTime)*0.000000001+"s\n");
        run();
    }

    private static void run() {
        print("*** WELCOME TO THE NUTRITION TRACKER ***\nHere are the commands(it ignores case):\n");
        printCommands();
        String input;
        do{
            input = getInput();
            processConsoleCommand(input.trim());
        }
        while (!input.equalsIgnoreCase("end"));
    }

    private static void printCommands(){
        //food commands
        printf("%s (and nutrients if desired)\n", cmds[0]);
        printf("%s (prints info of specific food)\n", cmds[1]);
        printf("%s(adds food)\n", cmds[2]);
        printf("%s(edits macros and micros of a food)\n", cmds[3]);
        printf("%s(deletes food)\n\n", cmds[4]);

        //daily total nutrition commands
        printf("%s(sums up a list of foods OR meals you type)\n", cmds[5]);
        printf("%s(resets current macros and micros summed up)\n\n", cmds[6]);

        //meal commands
        printf("%s (and nutrients if desired)\n", cmds[7]);
        printf("%s(prints info of specific meal)\n", cmds[8]);
        printf("%s\n", cmds[9]);
        printf("%s\n", cmds[10]);
        printf("%s\n\n", cmds[11]);

        //misc, extra stuff
        printf("%s (prints the available macro and micro nutrients names and abbreviations\n", cmds[12]);
        printf("%s (reload file data)\n",cmds[13]);
        printf("%s (prints extra info about the program)\n", cmds[14]);
        printf("%s (prints the commands)\n\n", cmds[15]);
    }



    private static void processConsoleCommand(String input) {

        if(input.equalsIgnoreCase(cmds[0])){ //print all foods names, and info if desired
            Main.sharedFunction_cmd1ANDcmd7((byte) 0); //0 =foods, 1 = meals
        }

        else if(input.equalsIgnoreCase(cmds[1])){ //print info of a specific food
            Main.sharedFunction_cmd2ANDcmd8("Food");
        }

        else if(input.equalsIgnoreCase(cmds[1])){ //adds a foot to the currently loaded list of foods
            Main.addFood();
        }

        else if(input.equalsIgnoreCase(cmds[3])){ //edit macros and micros of a given a food name
            Main.editFood();
        }

        else if(input.equalsIgnoreCase(cmds[4])){ //delete a food in the foods file given its name
            Main.deleteFood();
        }

        else if(input.equalsIgnoreCase(cmds[5])){ //sums up a combination of foods or meals you type and prints the macros and micros do the current sum and prints the result in the end
            Main.sumUpFoods();
        }

        else if(input.equalsIgnoreCase(cmds[6])){ //reset the current sum of macros and micros
            Main.resetSum();
        }

        else if(input.equalsIgnoreCase(cmds[7])){ //print all meals available
            Main.sharedFunction_cmd1ANDcmd7((byte) 1); //1 = meals
        }

        else if(input.equalsIgnoreCase(cmds[8])){ //prints info of a given meal
            Main.sharedFunction_cmd2ANDcmd8("Meal");
        }

        else if(input.equalsIgnoreCase(cmds[9])){ //create a meal, give it a name, and directly add foods, macros or micros to it
            //todo
        }

        else if(input.equalsIgnoreCase(cmds[10])){ //edit macros and micros of a meal given its name
            //todo
        }

        else if(input.equalsIgnoreCase(cmds[11])){ //delete a meal from the meals file given its name
            //todo
        }

        else if(input.equalsIgnoreCase(cmds[12])){ //details all the nutrients names, abbreviations and their groups
            Main.getAllNutrientNamesAndAbbreviations();
        }

        else if(input.equalsIgnoreCase(cmds[13])){
            Main.loadCurrentData();
        }

        else if(input.equalsIgnoreCase(cmds[14])){
            print(extraInfo);
        }

        else if (input.equalsIgnoreCase(cmds[15])){
            printCommands();
        }
        else if(input.equalsIgnoreCase("end")) {}
        else { print("\nCommand doesn't exist\n");}
    }

    public static void print(String s){
        IInputOutput.print(s);
    }

    //here, we try to acess out default method in our interface, and to do that, we need an to instance the interface
    public static void printf(String s, String s2){ //since we can't call printFormat from IOutputDisplayer statically, we need to create an instance with an anonymous class
        /* anonymous class
        IOutputDisplayer a = new IOutputDisplayer() {
            @Override
            public void LCD_Display_print(String text) {

            }
        };
         */
        IInputOutput a = txt -> { //anonymous class with lambda (->) syntax, since it's just 1 method to override, we can use lambda, otherwise we would have to use the syntax above
            print(txt); //(we cant System.out.print to a LCD, but is just to prove a point on how it works, this is useless, we just need an instance thats all
        };
        a.printFormat(s,s2);
    }

    //private static Scanner input = new Scanner(System.in);
    public static String getInput(){
        return IInputOutput.getInput();
        //return input.nextLine();
    }
    public static int getIntInput(){
       return IInputOutput.getIntInput();
    }

    @Override
    public void LCD_Display_print(String text) {
        //...
    }


}
