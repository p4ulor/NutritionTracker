//This class has the console commands and doesn't really deal with much data. Just calls the class Main to do it
//Almost everything in this whole project is static except for uses of the class Food. That's the only instance we need.
public final class Console implements IInputOutput {
    //                                          0                                  1                                      2                  3                                          4                       5                                                  6                                                     7                                  8                                     9         10         11        12                                                                         13                         14                                            15
    private static final String[] cmds =       {"printFoods",                      "foodInfo",                            "add",             "edit",                                    "del",                  "sumUp",                                           "resetSum",                                           "printMeals",                      "mealInfo",                           "addMeal","editMeal","delMeal","printNutrients",                                                          "reload",                  "extra",                                      "commands"};
    private static final String[] cmdsPrints = {"%s (and nutrients if desired)\n", "%s (prints info of specific food)\n", "%s(adds food)\n", "%s(edits macros and micros of a food)\n", "%s(deletes food)\n\n", "%s(sums up a list of foods OR meals you type)\n", "%s(resets current macros and micros summed up)\n\n", "%s (and nutrients if desired)\n", "%s(prints info of specific meal)\n", "%s\n", "%s\n", "%s\n\n", "%s (prints the available macro and micro nutrients names and abbreviations\n", "%s (reload file data)\n", "%s (prints extra info about the program)\n", "%s (prints the commands)\n\n"};
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
            print("\n$ ");
            input = getInput();
            processConsoleCommand(input.trim());
        }
        while (!input.equalsIgnoreCase("end"));
    }

    private static void printCommands(){
        assert cmds.length==cmdsPrints.length : "The number of command keys and the number of commands to print is different";
        for(byte i = 0; i< cmds.length; i++){
            printf(cmdsPrints[i], cmds[i]);
        }
    }

    private static void processConsoleCommand(String input) {
        if(input.equalsIgnoreCase(cmds[0])){ //print all foods names, and info if desired
            Main.sharedFunction_cmd1ANDcmd7(true); //true=its food, false=its meal
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
            Main.sharedFunction_cmd1ANDcmd7(false);
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

    public static void printf(String s, String s2) {
        IInputOutput.printFormat(s, s2);
    }

    public static String getInput(){
        return IInputOutput.getInput();
    }
    public static int getIntInput(){
       return IInputOutput.getIntInput();
    }
}
