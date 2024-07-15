package p4ulor.nutritiontracker.objs

/**
 * Daily values according to: https://www.fda.gov/food/new-nutrition-facts-label/daily-value-new-nutrition-and-supplement-facts-labels
 */
enum class Nutrients(
    val fullName: String,
    val group: Group,
    val dailyValue: Float
) {

    CAL("Calories", Group.Macros, 2000),
    CARBS("Carbs", Group.Macros, 275),
    PRO("Protein", Group.Macros, 50),
    FAT("Fat", Group.Macros, 78),
    FIB("Fiber", Group.Macros, 28),

    //Micros are all in mg(milligrams), to convert micrograms to milligrams -> https://www.thecalculatorsite.com/conversions/common/mcg-to-mg.php value*0.001, IU to mg https://mypharmatools.com/othertools/iu
    //https://en.wikipedia.org/wiki/Vitamin#List
    //https://en.wikipedia.org/wiki/B_vitamins               Alternative nutrient names(acording to google and nutritiondata.self.com):
    VA("Vitamin A", Group.Vitamin, 0.9f),  //Retinol, retinal, retinoic acid, beta-carotene
    VC("Vitamin C", Group.Vitamin, 0.9f),  //Ascorbic acid and ascorbate
    VE("Vitamin E", Group.Vitamin, 15),  //alpha tocopherol, alpha-tocopherol, tocotrienol
    VK("Vitamin K", Group.Vitamin, 0.12f),  //antihemorrhagic factor, menadiol, menadione (vitamin K-3), menaquinone (vitamin K-2), methylphytyl naphthoquinone, phylloquinone (vitamin K-1), phytonadione
    V1("Vitamin B1", Group.Vitamin, 1.2f),  //Thiamin
    V2("Vitamin B2", Group.Vitamin, 1.3f),  //Riboflavin
    V3("Vitamin B3", Group.Vitamin, 16),  //Niacin
    V5("Vitamin B5", Group.Vitamin, 5),  //Pantothenic Acid
    V6("Vitamin B6", Group.Vitamin, 1.7f),  //Pyridoxine
    //V7                                                       Biotin
    V9("Vitamin B9", Group.Vitamin, 0.4f),     //Folate (Folic Acid)
    V12("Vitamin B12", Group.Vitamin, 0.0024f),  //Cobalamin
    //Choline -> there's always trace amounts in everything / easy to take the %DV /  subjective %DV / kinda irrelevant
    //Betaine -> there's always trace amounts in everything / easy to take the %DV /  subjective %DV / kinda irrelevant

    CALC("Calcium", Group.Mineral, 1300),  //subjective and likely inflated by the dairy industry
    IR("Iron", Group.Mineral, 18),
    MAG("Magnesium", Group.Mineral, 420),
    PH("Phosphorus", Group.Mineral, 1250),
    POT("Potassium", Group.Mineral, 4700),
    SOD("Sodium", Group.Mineral, 2300),
    ZI("Zinc", Group.Mineral, 11),
    COP("Copper", Group.Mineral, 1),
    MAN("Manganese", Group.Mineral, 2.3f),
    SEL("Selenium", Group.Mineral, 0.055f),
    //Fluoride -> there's always tiny trace amounts in everything / easy to take the %DV /  subjective %DV / kinda irrelevant

    //PUFA's(Polyunsaturated fatty acids) https://www.healthline.com/nutrition/how-much-omega-3#general-guidelines
    //the daily value of these nutrients may be subjective
    O3("Omega 3", Group.FattyAcid, 1600f),  //alpha-linolenic acid
    O6("Omega 6", Group.FattyAcid, 420f);  //linoleic acid

    constructor(fullName: String, group: Group, dailyValue: Int) : this(fullName, group, dailyValue.toFloat())

    companion object {
        val nutrientComparator = Comparator<Nutrients> { nutrient1, nutrient2 ->
            nutrient1.ordinal.compareTo(nutrient2.ordinal)
        }
    }

    enum class Group {
        Macros,
        //Micros:
        Vitamin,
        Mineral,
        FattyAcid
    }
}