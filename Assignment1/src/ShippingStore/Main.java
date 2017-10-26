package ShippingStore;

import java.io.FileNotFoundException;
import java.util.ConcurrentModificationException;

/**
 * Main/Driver for the package ShippingStore
 *
 * @author  Kentessa Fanfair
 * @version 0.0.1
 * @since   0.0.1
 */
public class Main {

    public static void main(String[] args) {
        final int MIN_VALUE = 1; // start point for range of menu options
        final int MAX_VALUE = 6; // endpoint for range of menu options

        System.out.println();
        System.out.printf("                    Welcome to the Shipping Store%n" +
                          "               ----------------------------------------%n");
        System.out.printf(
                "               1). Show all existing package records in the database%n" +
                "               2). Add a new package to the database%n" +
                "               3). Delete a package from the database%n" +
                "               4). Search for a package via tracking number%n" +
                "               5). Show a list of packages within a given weight range%n" +
                "               6). Exit program%n%n%n");

        // load data from text file before user provides menu selection
        Package.importPackagesFromFile();

        // run menu prompt until the user quits (enters 6)
        do {
            // create an instance of the menuPrompter with specified range
            MenuPrompter menuPrompter = new MenuPrompter(MIN_VALUE, MAX_VALUE);

            // store results into choice
            int choice = menuPrompter.promptAndValidateMenuSelection();

            switch (choice) {
                case 1:
                    Package.showAllExistingPackages();
                    continue;
                case 2:
                    // create an instance of the Package with no params
                    Package newPackage = new Package();
                    newPackage.createPackage();
                    newPackage.addPackageToMemory();
                    continue;
                case 3:
                    Package.deletePackage();
                    continue;
                case 4:
                    Package.findPackage();
                    continue;
                case 5:
                    Package.findPackagesWithinWeightRange();
                    continue;
                case 6:
                    System.out.printf("%n%nPreparing to exit program...%nSaving data to 'packages.txt'... %n" +
                                      "Exiting program...%n%n");
                    Package.exportPackagesToFile();
                    System.exit(0);
                default:
                    System.out.println("None of the options matches your input");
            }
        } while (true);
    }
}