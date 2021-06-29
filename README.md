# NutritionTracker
Nutrition tracker program for macros and micros

* The program works offline(no internet connection need)
* It's like a MyFitnessPal, but it requires you to config your data more manually before actually using it(that is, before you can calculate your macros and micros for the day or the macros and micros of a meal per example, you're supposed to have some data to work with). By data I mean your individual foods and meals. You can edit these by directly accessing the foods and meals data files or by calling methods to change that data.
* You basically have the information(names, macro and micros) of your foods and meals in a .txt file. And per example, if you want to calculate what macros you hit in a certain meal or day, you select what foods you ate and their ammounts in grams, and this app will calculate the macros and micros for each nutrients and the % of the DV(Daily Value, AKA Reference Daily Intake). It will also let you add foods, or edit the macros or micros of a food, delete it from the .txt file, make a meal based on a group of foods, etc.

The App folder contains the project files that are designed to deploy the program into an Android app (and should be open using [Android Studio](https://developer.android.com/studio))

The Console folder contains the project files that are designed to be executed in console(and should be open using [IntelliJ IDEA community](https://www.jetbrains.com/idea/download/#section=windows))
___

This a personal project where I develop this program in 4 different forms:

- [ ] Make the App using java
- [ ] Make the App using kotlin
- [x] Make the Console program using java
- [ ] Make the Console program using kotlin

I aim to use as many concepts and methodologies(as reasonably possible) to exercice and demonstrate my skills with these 2 languages and 2 environments(in a default console and in a Android app)
So, expect to view this program a BIT more confusing or spaghetti-like than it should be.

I also made this project because I wanted to have a tool like MyFitnessPal, but that doesnt require internet connection and that doesn't have so many restrictions on the foods and nutritional values of the foods that you can track. I wanted to create something more pratical.

This is also for educational and recreational purposes. It's open source = it's open for you to make whatever you want with it.

___

In case you're new to this,
[IntelliJ](https://en.wikipedia.org/wiki/IntelliJ_IDEA) is great IDE for java and kotlin

and [Android Studio](https://en.wikipedia.org/wiki/Android_Studio) is based on IntelliJ
##### IDEA means: Integrated Development Environment Application. Or [this](https://stackoverflow.com/questions/22026104/what-does-the-a-stand-for-in-intellij-idea)