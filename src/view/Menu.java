package view;

import utilities.Inputter;

public class Menu {

    public int getChoice(String[] options) {
        System.out.println("\n-----------------------------------");
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s\n", i + 1, options[i]);
        }
        return Inputter.getInt("Choose an option: ", 1, options.length);
    }
}
