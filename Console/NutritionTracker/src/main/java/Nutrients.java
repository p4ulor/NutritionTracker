enum Nutrients{ //daily value according to: https://www.fda.gov/food/new-nutrition-facts-label/daily-value-new-nutrition-and-supplement-facts-labels
    //5#, macros are all in g(grams)
    CAL("Calories", 'A',2000),
    CARBS("Carbs",'A',275),
    PRO("Protein",'A',50),
    FAT("Fat",'A',78),
    FIB("Fiber",'A',28),

    //Micros are all in mg(milligrams), to convert micro grams to miligrams -> https://www.thecalculatorsite.com/conversions/common/mcg-to-mg.php value*0.001, IU to mg https://mypharmatools.com/othertools/iu
    //11# vitamins
    //https://en.wikipedia.org/wiki/Vitamin#List
    //https://en.wikipedia.org/wiki/B_vitamins               Alternative nutrient names(acording to google and nutritiondata.self.com):
    VA("Vitamin A",'V', 0.9f),      //Retinol, retinal, retinoic acid, beta-carotene
    VC("Vitamin C",'V',0.9f),       //Ascorbic acid and ascorbate
    VE("Vitamin E",'V',15),         //alpha tocopherol, alpha-tocopherol, tocotrienol
    VK("Vitamin K",'V',0.12f),      //antihemorrhagic factor, menadiol, menadione (vitamin K-3), menaquinone (vitamin K-2), methylphytyl naphthoquinone, phylloquinone (vitamin K-1), phytonadione
    V1("Vitamin B1",'V',1.2f),      //Thiamin
    V2("Vitamin B2",'V',1.3f),      //Riboflavin
    V3("Vitamin B3",'V',16),        //Niacin
    V5("Vitamin B5", 'V',5),        //Pantothenic Acid
    V6("Vitamin B6",'V',1.7f),      //Pyridoxine
//  V7                                                       Biotin
    V9("Vitamin B9",'V',0.4f),      //Folate (Folic Acid)
    V12("Vitamin B12",'V',0.0024f), //Cobalamin
    //Choline -> there's always trace amounts in everything / easy to take the %DV /  subjective %DV / kinda irrelevant
    //Betaine -> there's always trace amounts in everything / easy to take the %DV /  subjective %DV / kinda irrelevant

    //10# minerals
    CALC("Calcium",'M',1300), //subjective and likely inflated by the dairy industry
    IR("Iron",'M',18),
    MAG("Magnesium",'M',420),
    PH("Phosphorus",'M',1250),
    POT("Potassium",'M',4700),
    SOD("Sodium",'M',2300),
    ZI("Zinc",'M',11),
    COP("Copper",'M',1),
    MAN("Manganese",'M',2.3f),
    SEL("Selenium",'M',0.055f),
    //Fluoride -> there's always trace amounts in everything / easy to take the %DV /  subjective %DV / kinda irrelevant

    //PUFA's(Polyunsaturated fatty acids) https://www.healthline.com/nutrition/how-much-omega-3#general-guidelines
    //the daily value of these nutrients may be subjective
    O3("Omega 3",'F',1600f), //alpha-linolenic acid 3.8xOmega6 radio
    O6("Omega 6",'F',420f); //linoleic acid

    private final String fullName;
    private final char group; // A -> macros, V -> vitamins, M -> minerals, F -> Fatty acids
    private final float dailyValue;
    //public final static int ammountOfNutrients = Nutrients.values().length;
    Nutrients(String fullName, char group, float dailyValue) {
        this.fullName = fullName;
        this.group = group;
        this.dailyValue = dailyValue;
    }
    public String getFullName() {
        return fullName;
    }
    public char getGroup() {
        return group;
    }
    public float getDailyValue(){
        return dailyValue;
    }
    public Object getAll(){
        return this.getDeclaringClass().getEnumConstants();
    }

}