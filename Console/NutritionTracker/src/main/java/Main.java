import java.io.*;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Main {
    public static final String commentSymbol = "//"; //comment symbol must be separated by a space by the last numerical value of a nutrient
    public static final String unknownSymbol = "???"; //https://stackoverflow.com/a/215505/9375488
    private static final String foodsFile = "foodData.txt";
    private static final String mealsFile = "mealsData.txt";

    private static LinkedList<Food> foods = new LinkedList<>();
    private static LinkedList<Food> meals = new LinkedList<>();
    private static final Nutrients[] nutrients = Nutrients.values();
    private static final int ammountOfNutrients = nutrients.length;
    private static Food currentSum = new Food("currentSum", new Nutrients[ammountOfNutrients], new float[ammountOfNutrients]); //can be thought as the "food" with all the properties summed together

    static { //static block -> https://www.geeksforgeeks.org/g-fact-79/, this only runs once, and that's when the class is loaded into memory
        currentSum.setNutrients(nutrients);
        print("Array of nutrients loaded and current sum initialized\n");
    }

    static void sharedFunction_cmd1ANDcmd7(boolean isFood){
        LinkedList<Food> list;
        if(isFood) list = foods;
        else list = meals;
        print("Print info aswell? (if so, type \'yes\')\n");
        String res = getInput();
        if(res.equalsIgnoreCase("yes")) {
            for (int i = 0; i < list.size(); i++) {
                print(Main.getFoodAsString(list.get(i), true));
                print("\n");
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                print(list.get(i).getName()+", ");
            }
        }
    }

    static void sharedFunction_cmd2ANDcmd8(String name){ //name="Food" or "Meal"
        LinkedList<Food> list;
        if(name.equalsIgnoreCase("Food")) list = foods;
        else list = meals;
        print(name+" name? ");
        String res = getInput();
        for (int i = 0; i < list.size(); i++) {
            Food food = list.get(i);
            if(food.getName().equalsIgnoreCase(res)){
                print(Main.getFoodAsString(food, true));
            }
        }
    }

    //CALCULATE FINAL MEAL/DAILY NUTRITIONAL VALUES. todo make meals acceptable
    static void sumUpFoods() { //should all foods have all the micros and macros INCLUDING zeros or discard those? hmmm
        print("Type the food name to add, followed by the ammount in grams(finish by typing \'end\'):\n");
        String s = getInput();
        float[] newVals = new float[ammountOfNutrients];
        while(!s.equalsIgnoreCase("end")){
            String[] values = s.trim().split(" ");

            if(values.length!=2) { print("Improper ammount of inputs\n"); return; }

            Food foodToAdd = getFood(values[0]); //getFood already ignores case
            if(foodToAdd==null) { print("Food doesn't exist\n"); return; }

            float ammount = 0;
            try {
                ammount = Float.parseFloat(values[1]);
            } catch(Exception e) { print("Error when converting number to float\n"); }

            float[] vals = foodToAdd.getValues();
            Nutrients[] nutri = foodToAdd.getNutrients();
            Nutrients[] orgNutri = currentSum.getNutrients();
            for(int i = 0; i< ammountOfNutrients; i++){
                for(int j = 0; i<vals.length; j++) {
                    if(nutri[j].name().equalsIgnoreCase(orgNutri[i].name())){
                        newVals[i] = newVals[i] + (vals[j] * (ammount/100));
                    }
                }
            }

            print("Next food:\n");
            s = getInput();
        }
        currentSum.setValues(newVals);
        print(getFoodAsString(currentSum, true)+"\n");
    }

    static void resetSum(){
        currentSum.setNutrients(nutrients);
        currentSum.setValues(new float[ammountOfNutrients]);
        print("Current sum reseted\n");
    }

    //EDIT DATA FROM foodData.txt from console

    static void addFood(){
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
    static void editFood(){
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

    static void deleteFood() {
        print("Food name?\n");
        Food f = getFood(getInput());
        if(f==null) {
            print("Food doesn't exist\n");
            return;
        }
        foods.remove(f);
        print("Food removed\n");
    }

    //EDIT PERSONAL DAILY VALUE OF NUTRIENTS
    //todo

    //LOAD AND PROCESS DATA FROM foodData.txt
    //todo
    static void loadCurrentData() { //abides by a certain format
        print("Loading data in "+foodsFile+"\n");

        /*//when run with a .jar, using File class just tries to access something that is in the same path as the .jar, but it won't acess inside the .jar
        JarFile jar = null;
        try {
            String file = "NutritionTracker.main.jar";
            jar = new JarFile(file);
        } catch (IOException e) { e.printStackTrace(); }
        JarEntry entry = jar.getJarEntry(foodsFile);
        if (entry == null) {
            print("Food file NOT loaded!"); return;
        } else print(foodsFile+" found");*/

        foods.clear();

        try (InputStreamReader foodDataStream = new InputStreamReader(Main.class.getClassLoader().getResourceAsStream(foodsFile))) {
            BufferedReader br = new BufferedReader(foodDataStream);
            String line;
            boolean executed = false;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if(line.isEmpty()) continue;
                executed = true;

                int indexOfComment = line.indexOf(commentSymbol); //if exists
                if(indexOfComment==-1) indexOfComment = line.length(); //because then the interval is open on the left [ ; [
                                      //        [0,indexOfComment+1[
                                      //https://www.javatpoint.com/substring   cut out unnecessary spaces    split by space

                String[] values = line.substring(0,indexOfComment).replaceAll("\\s+", " ").split(" "); //pick each individual string(data) and put it in an array of strings
                if(indexOfComment==-1) values[values.length-1] = commentSymbol; //example: values = ["Flakes", "Cal", "361", "carbs", "67.7", "pro", "12.2", "//"]
                if(processLineInTXTDataFile(true, values)==null) break;
                sb.append(line).append("\n");
            }
            if(!executed) print("File is empty?\n");

            else {
                File f = new File("NutritionTracker.main.jar"); //if its in a jar file (which is read only) we copy the file to the path
                if (f.exists() && f.isDirectory()) {
                    File file = new File(foodsFile);
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                    }
                    addToTXTFile(foodsFile, sb.toString());
                }
            }
        } catch (Exception e) { e.printStackTrace(); }




        print("Loading data in "+mealsFile+"\n");

        meals.clear();
        //todo



    }

    private static Food processLineInTXTDataFile(boolean isFood, String[] values) { //adds a line of data(can be food or meal) given the current array of data, IMPORTANT
        String foodName = values[0];
        if(foodName.isEmpty()) return new Food();
        if(foodName.equalsIgnoreCase("end")) return null;
        if(getFood(foodName)!=null) { print("\nThe food named "+foodName+" already exists\n"); return new Food(); }
        if(!doesOnlyHaveLetters((foodName))) return new Food();
        boolean error = false;
        StringBuilder errorMessage = new StringBuilder("\nLine at food named "+foodName+" has errors: ");
        Nutrients[] nutrients = new Nutrients[ammountOfNutrients];
        float[] vals = new float[ammountOfNutrients];
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
        //else meals.add(new Meal("")); //todo
        return food;
    }

    //GENERAL and UTILITY METHODS
    public static void print(String text){ //to shorten the "System.out.println"
        Console.print(text);
    }
    public static String getInput(){
        return Console.getInput();
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

    //https://stackoverflow.com/questions/5238491/check-if-string-contains-only-letters
    private static boolean doesOnlyHaveLetters(String s){
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isLetter(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void addToTXTFile(String file, String content){ //respect the end keyword?
        try(BufferedWriter output = new BufferedWriter(new FileWriter(file, true))){
            output.append(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addLineToTXTFile(String file, String line){ //respect the end keyword?
        addToTXTFile(file, "\n"+line);
    }

    //GETTERS

    static String getFoodAsString(Food food, boolean printMode){
        StringBuilder stringBuilder;
        if(printMode) stringBuilder = new StringBuilder(food.getName()+" -> ");
        else stringBuilder = new StringBuilder(food.getName()+" ");
        Nutrients[] nutrients = food.getNutrients();
        float[] vals = food.getValues();
        for(int j = 0; nutrients[j]!=null ; j++){
            stringBuilder.append(nutrients[j].getFullName() +" "+ vals[j]+" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(" "));
        return stringBuilder.toString();
    }

    private static Food getFood(String foodName){
        Food food = null;
        for(int i = 0; i< foods.size(); i++){
            if(foods.get(i).getName().equalsIgnoreCase(foodName)) food = foods.get(i);
        }
        return food;
    }

    static String getAllNutrientNamesAndAbbreviations(){
        int i = 1;
        for(Nutrients nutri : Nutrients.values()){ //or nutri.String(), returns like: CAL, CARBS, PRO. The name of the enum object itelf                                                                                            //https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html -> talks about the \\s
            print(i++ +": "+nutri.getFullName()+" | "+nutri.name()+", group:"+nutri.getGroup()+"\n");
        }
        //print("\n");
        return null;
    }

}

//I call it: parallel level class
//Class main works mainly with static methods, thus, those static methods cannot work with this class Food IF it were to be an inner class of Main, because "An instance of an inner class cannot be created without an instance of the outer class."
class Food { //https://www.quora.com/Can-we-keep-more-than-one-class-in-a-single-java-file      https://stackoverflow.com/questions/48839053/how-to-use-multiple-classes-in-java-in-one-file/48839113  https://stackoverflow.com/questions/2336692/java-multiple-class-declarations-in-one-file
    private final String name;
    private Nutrients[] nutrients;
    private float[] values;

    Food(){
        name = Main.unknownSymbol;
    }

    Food(String name, Nutrients[] nutrients, float[] values) { //check properties.length==vals?
        this.name=name;
        this.nutrients=nutrients;
        this.values = values;
    }

    public void sortNutrients(){
        Nutrients[] nu = Nutrients.values(); //correct order
        Nutrients[] orderedNutrients = new Nutrients[nutrients.length];
        float[] orderedVals = new float[values.length];
        int index = 0;
        for(int i = 0; i<nu.length; i++){ //query already in correct order
            for(int j = 0; j<nutrients.length; j++){ //get existing nutrient in already correct order
                if(nutrients[i]==null) continue;
                if(nu[j].name().equalsIgnoreCase( nutrients[i].name() ) ){ //if exists
                    orderedNutrients[index] = nutrients[j];
                    orderedVals[index] = values[j];
                    index++;
                    break;
                }
            }
        }
        this.nutrients = orderedNutrients; //ehh, let the GC (garbage colector) do its job lol https://stackoverflow.com/questions/55567935/how-to-delete-an-array-properly-in-java/55568220
        this.values = orderedVals; //https://stackoverflow.com/questions/15448457/deleting-an-entire-array
    }

    public void setValues(float[] values){
        this.values = values;
    }
    public void setNutrients(Nutrients[] nutrients){
        this.nutrients=nutrients;
    }

    public String getName(){
        return name;
    }
    public Nutrients[] getNutrients(){
        return nutrients;
    }
    public float[] getValues(){
        return values;
    }
}



