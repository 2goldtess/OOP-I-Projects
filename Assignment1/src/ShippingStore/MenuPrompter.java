package ShippingStore;

import java.util.Scanner;

/**
 * MenuPrompter is a simple class for prompting a user for (valid)input.
 *
 * It prompts the user for an input corresponding to the provided menu options. It then validates the input
 * (making sure it is within the range of the given number of menu options and that it contains integer values only)
 * before returning the input value.
 *
 * @author  Kentessa Fanfair
 * @version 0.0.1
 * @since   0.0.1
 */
public class MenuPrompter {
    private int minValue;
    private int maxValue;

    /**
     * Constructs an empty MenuPrompter
     */
    public MenuPrompter() {

    }
    /**
     * Constructs a MenuPrompter object with specified range of values
     *
     * @param minValue - start point for the range
     * @param maxValue - endpoint for the range
     */
    public MenuPrompter(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     * Prompts for input and loops until valid input is received
     *
     * @return choice - the number corresponding to the menu option selected
     */
     int promptAndValidateMenuSelection() {

        Scanner userInput = new Scanner(System.in);
        int choice;

        do {
            System.out.print("Enter the number corresponding to the menu option you would like to make: ");

            if (userInput.hasNextInt()) {
                choice = userInput.nextInt();

                if (choice >= minValue && choice <= maxValue) {
                    break; // if choice in correct range, break out of loop
                } else {
                    System.out.println("*** Invalid selection. Please enter values ranging from 1 - 6 only");
                    continue; // restart loop, didn't get valid integer input
                }
            } else {
                System.out.println("*** Invalid input, Please enter integer values only.");
                userInput.next(); // discard non-int input
                continue; // restart loop, didn't get an integer input
            }

        } while (true);

        return choice;
    }
}
