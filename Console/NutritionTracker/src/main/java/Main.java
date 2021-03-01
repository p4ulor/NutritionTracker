import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    static final String commentSymbol = "//"; //comment symbol must be separated by a space by the last numerical value of a nutrient
    static final String unknownSymbol = "???";
    private static final String foodsFile = "foodData.txt";
    private static final String mealsFile = "mealsData.txt";

    private static final String cmd1 = "printFoods";
    private static final String cmd1_2 = "foodInfo";
    private static final String cmd2 = "add";
    private static final String cmd3 = "edit";
    private static final String cmd4 = "del";

    private static final String cmd5 = "sumUp";
    private static final String cmd6 = "resetSum";

    private static final String cmd7 = "printMeals";
    private static final String cmd8 = "mealInfo";
    private static final String cmd9 = "createMeal";
    private static final String cmd10 = "editMeal";
    private static final String cmd11 = "delMeal";

    private static final String cmd12 = "printNutrients";
    private static final String cmd13 = "reload";
    private static final String cmd14 = "extra";

    private static final String extraInfo = "Print end to finish.\n" +
            "Remember that all foods/meals have their respective amount of nutrients present in the food per 100g of food.\n" +
            "When loading the data files, the program will ignore food lines that starts with non-letter char and will perceive \"//\" as a comment and will ignore anything after it.\n" +
            "When loading the data files, the program will stop reading when finding a food named \"end\"\n";

    private static LinkedList<Food> foods = new LinkedList<>();
    private static LinkedList<Food> meals = new LinkedList<>();
    private static int size = Nutrients.values().length;
    private static Food currentSum = new Food("currentSum", new Nutrients[size], new float[size]); //can be thought as the "food" with all the properties summed together
    //public static HashMap<String, float[]> foods = new HashMap<>();


    public static void main(String[] args) {
        long startTime = System.nanoTime();
        loadCurrentData();
        long stopTime = System.nanoTime();
        print("Load time: "+(stopTime - startTime)*0.000000001+"s\n");
        run();
    }

    private static void run() {
        System.out.println("WELCOME TO THE NUTRITION TRACKER \n Here are the commands(it ignores case): ");
        System.out.printf("%s (and nutrients if desired), %s (prints info of specific food),  %s(adds food), %s(edits macros and micros of a food), %s(deletes food) \n", cmd1, cmd1_2, cmd2, cmd3, cmd4); //food commands
        System.out.printf("%s(sums up a list of foods OR meals you type), %s(resets current macros and micros summed up) \n", cmd5, cmd6);
        System.out.printf("%s (and nutrients if desired), %s(prints info of specific meal), %s, %s, %s \n", cmd7, cmd8, cmd9, cmd10, cmd11);
        System.out.printf("%s (prints the available macro and micro nutrients names and abbreviations. %s (reload file data)", cmd12, cmd13);
        System.out.printf("%s (prints extra info about the program)", cmd14);
        print("\n");
        String input;
        do{
            input = getInput();
            processConsoleCommand(input);
        }
        while (!input.equalsIgnoreCase("end"));
    }

    private static void sharedFunction_cmd1ANDcmd7(LinkedList<Food> list){
        print("Print info aswell? (if so, type \'yes\')\n");
        String res = getInput();
        if(res.equalsIgnoreCase("yes")) {
            for (int i = 0; i < list.size(); i++) {
                print(getFoodAsString(list.get(i), true));
                print("\n");
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                print(list.get(i).getName()+", ");
                if(i%10==0) print("\n");
            }
        }
    }

    private static void sharedFunction_cmd1_2ANDcmd8(LinkedList<Food> list, String name){
        print(name+" name? ");
        String res = getInput();
        for (int i = 0; i < list.size(); i++) {
            Food food = list.get(i);
            if(food.getName().equalsIgnoreCase(res)){
                print(getFoodAsString(food, true));
            }
        }
    }

    private static void processConsoleCommand(String input) {

        if(input.equalsIgnoreCase(cmd1)){ //print all foods names, and info if desired
            sharedFunction_cmd1ANDcmd7(foods);
        }

        else if(input.equalsIgnoreCase(cmd1_2)){ //print info of a specific food
            sharedFunction_cmd1_2ANDcmd8(foods,"Food");
        }

        else if(input.equalsIgnoreCase(cmd2)){ //adds a foot to the currently loaded list of foods
            addFood();
        }

        else if(input.equalsIgnoreCase(cmd3)){ //edit macros and micros of a given a food name
            editFood();
        }

        else if(input.equalsIgnoreCase(cmd4)){ //delete a food in the foods file given its name
            deleteFood();
        }

        else if(input.equalsIgnoreCase(cmd5)){ //sums up a combination of foods or meals you type and prints the macros and micros do the current sum and prints the result in the end
            sumUpFoods();
        }

        else if(input.equalsIgnoreCase(cmd6)){ //reset the current sum of macros and micros
            currentSum = new Food("currentSum", new Nutrients[size], new float[size]);
        }

        else if(input.equalsIgnoreCase(cmd7)){ //print all meals available
            sharedFunction_cmd1ANDcmd7(meals);
        }

        else if(input.equalsIgnoreCase(cmd8)){ //prints info of a given meal
            sharedFunction_cmd1_2ANDcmd8(meals,"Meal");
        }

        else if(input.equalsIgnoreCase(cmd9)){ //create a meal, give it a name, and directly add foods, macros or micros to it
            //todo
        }

        else if(input.equalsIgnoreCase(cmd10)){ //edit macros and micros of a meal given its name
            //todo
        }

        else if(input.equalsIgnoreCase(cmd11)){ //delete a meal from the meals file given its name
            //todo
        }

        else if(input.equalsIgnoreCase(cmd12)){ //details all the nutrients names, abbreviations and their groups
            getAllNutrientNamesAndAbbreviations();
        }
        else if(input.equalsIgnoreCase(cmd13)){
            loadCurrentData();
        }
        else if(input.equalsIgnoreCase(cmd14)){
            print(extraInfo);
        }
        else if (input.equalsIgnoreCase("end")){}
        else { print("\nCommand doesn't exist\n");}
    }

    //CALCULATE FINAL MEAL/DAILY NUTRITIONAL VALUES.
    //todo
    private static void sumUpFoods() {
        print("Print the names of foods, so you calculate the total nutritional values for a meal or for the day(finish by typing \'end\'):");
        while(!getInput().equalsIgnoreCase("end")){
            for(Food food : foods){

            }
        }
        print(getFoodAsString(currentSum, true)+"\n");
    }


    //EDIT DATA FROM foodData.txt from console

    public static void addFood(){
        print("Type the food name followed by the nutrients name followed by the amount of grams\n(remember to convert micrograms to milligrams for micros, don't type the \'g\' and type everything separated by 1 space)\nit contains per 100g (type end to finish)\n");
        String input = getInput();
        if(input.isEmpty()) { print("Error, empty input\n"); return;}
        String[] values = input.split(" ");
        Food food = processLineInTXTDataFile(true,values);
        if(food==null || food.getName().equals(unknownSymbol)) { print("Error: bad format\n"); return; }
        String line = getFoodAsString(food, false); //cant be original, because erros are fixed in processLineInTXTDatFile
        addLineToTXTFile(foodsFile,line);
        print("\nThe following food was added:\n");
        print(getFoodAsString(food,true)+"\n");
    }

    //todo
    public static void editFood(){
        System.out.println("Food name?");
        if(getFood(getInput())==null) {
            print("Food doesn't exist");
            return;
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(foodsFile);
            outputStream.write(0);
        } catch (Exception e) { e.printStackTrace(); }
    }

    //todo
    private static void deleteFood() {
        System.out.println("Food name?");
        if(getFood(getInput())==null) {
            print("Food doesn't exist");
            return;
        }

    }



    //EDIT PERSONAL DAILY VALUE OF NUTRIENTS
    //todo

    //LOAD AND PROCESS DATA FROM foodData.txt
    //todo
    private static void loadCurrentData() { //abides by a certain format
        File file = new File(foodsFile);
        if( !(file.exists()) ) {
            try { file.createNewFile(); } catch (IOException e) { }
        }

        foods.clear();
        print("Loading data in "+foodsFile+"\n");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean executed = false;
            while ((line = br.readLine()) != null) {
                if(line.isEmpty()) continue;
                executed = true;
                int indexOfComment = line.indexOf(commentSymbol);
                if(indexOfComment==-1) indexOfComment = line.length()-1;
                                      //        [0,indexOfComment+1[
                                      //https://www.javatpoint.com/substring   cut out unnecessary spaces    split by space
                String[] values = line.substring(0,indexOfComment+1).replaceAll("\\s+", " ").split(" "); //pick each individual string(data) and put it in an array of strings
                if(indexOfComment==-1) values[values.length-1] = commentSymbol; //example: values = ["Flakes", "Cal", "361", "carbs", "67.7", "pro", "12.2", "//"]
                if(processLineInTXTDataFile(true, values)==null) break;
            }
            if(!executed) print("File is empty?");
        } catch (Exception e) { e.printStackTrace(); }

        print("Loading data in "+mealsFile+"\n");
        //todo
    }

    private static Food processLineInTXTDataFile(boolean isFood, String[] values) { //adds a line of data(can be food or meal) given the current array of data
        String foodName = values[0];;
        if(foodName.equalsIgnoreCase("end")) return null;
        if(getFood(foodName)!=null) { print("\nFood already exists\n"); return new Food(); }
        if(!isAlpha((foodName))) return new Food();
        boolean error = false;
        StringBuilder errorMessage = new StringBuilder("Line at food named "+foodName+" has errors: ");
        Nutrients[] nutrients = new Nutrients[Nutrients.values().length];
        float[] vals = new float[Nutrients.values().length];
        int length = values.length;
        if((values.length-1)/*(minus name)*/ % 2 != 0) {
            error=true;
            errorMessage.append("Incomplete pair of values, ");
            length = length -1; //if length is not even, that means a value is missing, so we fix it like this
        }
        int propertyIndex = 0;
        for(int i = 1; i<length; i++){
            //if(values[i].equals(commentSymbol)) break;
            Nutrients nutrient = getEnumFromString(Nutrients.class,values[i]);
            if(nutrient==null){ //if didnt find by abbreviated name, check nutrient validation by full name
                for(Nutrients nutri : Nutrients.values()){                                                                                              //https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html -> talks about the \\s
                    if(nutri.getFullName().equalsIgnoreCase(values[i].replaceAll("\\s+",""))){ //replaces spaces with "" https://stackoverflow.com/questions/17973964/how-to-compare-two-strings-in-java-without-considering-spaces
                        nutrient = nutri; break;
                    }
                }
            }
            if(nutrient==null){
                error = true;
                errorMessage.append("Nutrient "+values[i]+" doesn't exist, ");
                continue;
            }
            try {
                float val = Float.parseFloat(values[++i]);
                nutrients[propertyIndex] = nutrient;
                vals[propertyIndex++] = val;
            } catch (Exception e) {
                error=true; errorMessage.append("Error with nutrient value(will be set t zero), "); e.printStackTrace();
                vals[propertyIndex++] = 0;
            }
        }
        if(error) print(errorMessage.append("\n").toString()); //the existence of errors doesn't invalidate the food(they are fixed or the nutrients with errors arent accounted)
        Food food = new Food(foodName, nutrients, vals);
        food.sortNutrients(); //in case there's something like "Flakes carbs 67.7 Cal 361", in these cases it will be fixed to Flakes Cal 361 carbs 67.7
        if(isFood) foods.add(food);
        else meals.add(food);
        return food;
    }



    //GENERAL and UTILITY METHODS
    public static void print(String text){ //to shorten the "System.out.println"
        System.out.print(text);
    }

    public static String getInput(){
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        return input;
    }

    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) { //https://stackoverflow.com/questions/604424/how-to-get-an-enum-value-from-a-string-value-in-java
        if( c != null && string != null ) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch(Exception ex) {
                //print("Can't find nutrient "+string+" in enum. Will searched for full name next\n");
                //ex.printStackTrace();
            }
        }
        return null;
    }

    public static boolean isAlpha(String name) { //https://stackoverflow.com/questions/5238491/check-if-string-contains-only-letters
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public static void addLineToTXTFile(String file, String line){
        try(Writer output = new BufferedWriter(new FileWriter(file, true))){
            output.append("\n"+line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //GETTERS

    public static String getFoodAsString(Food food, boolean printMode){
        StringBuilder stringBuilder;
        if(printMode) stringBuilder = new StringBuilder(food.getName()+" -> ");
        else stringBuilder = new StringBuilder(food.getName()+" ");
        Nutrients[] nutrients = food.getNutrients();
        float[] vals = food.getVals();
        for(int j = 0; nutrients[j]!=null ; j++){
            stringBuilder.append(nutrients[j].getFullName() +" "+ vals[j]+" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(" "));
        return stringBuilder.toString();
    }

    public static Food getFood(String foodName){
        Food food = null;
        for(int i = 0; i< foods.size(); i++){
            if(foods.get(i).getName().equalsIgnoreCase(foodName)) food = foods.get(i);
        }
        return food;
    }

    public static String getAllNutrientNamesAndAbbreviations(){
        int i = 1;
        for(Nutrients nutri : Nutrients.values()){ //or nutri.String(), returns like: CAL, CARBS, PRO. The name of the enum object itelf                                                                                            //https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html -> talks about the \\s
            print(i++ +": "+nutri.getFullName()+" | "+nutri.name()+", group:"+nutri.getGroup()+"\n");
        }
        print("\n");
        return null;
    }
}

class Food {
    private final String name;
    private Nutrients[] nutrients;
    private float[] vals;

    Food(){
        name = Main.unknownSymbol;
    }

    Food(String name, Nutrients[] nutrients, float[] vals) { //check properties.length==vals?
        this.name=name;
        this.nutrients=nutrients;
        this.vals=vals;
    }

    public void sortNutrients(){
        Nutrients[] nu = Nutrients.values(); //correct order
        Nutrients[] orderedNutrients = new Nutrients[nutrients.length];
        float[] orderedVals = new float[vals.length];
        int index = 0;
        for(int i = 0; i<nu.length; i++){ //query already in correct order
            for(int j = 0; j<nutrients.length; j++){ //get existing nutrient in already correct order
                if(nutrients[i]==null) continue;
                //
                if(nu[j].name().equalsIgnoreCase( nutrients[i].name() ) ){ //if exists
                    orderedNutrients[index] = nutrients[j];
                    orderedVals[index] = vals[j];
                    index++;
                    break;
                }
            }
        }
        this.nutrients = orderedNutrients; //ehh https://stackoverflow.com/questions/55567935/how-to-delete-an-array-properly-in-java/55568220
        this.vals = orderedVals; //https://stackoverflow.com/questions/15448457/deleting-an-entire-array
    }

    public String getName() {
        return name;
    }
    public Nutrients[] getNutrients() {
        return nutrients;
    }
    public float[] getVals() {
        return vals;
    }
}

