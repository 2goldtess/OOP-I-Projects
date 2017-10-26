package ShippingStore;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * A class for creating simple packages.
 *
 * @author  Kentessa Fanfair
 * @version 0.0.1
 * @since   0.0.1
 */
public class Package {

    private String mTrackingNumber;
    private String mType;
    private String mSpecification;
    private String mMailingClass;
    private float mWeight;
    private int mVolume;

    private final static int TN_MAX_LENGTH = 5; // max length for a tracking number
    private final static List<String> typeOptions = Arrays.asList("postcard", "letter", "envelope", "tube",
                                                                  "packet", "box", "crate", "drum", "roll");
    private final static List<String> specOptions = Arrays.asList("fragile", "books", "catalogs", "do-not-bend", "n/a");
    private final static List<String> mailingClassOptions = Arrays.asList("first-class", "priority", "retail", "ground",
                                                                          "metro");

    private  final static String filename = "packages.txt";

    private static ArrayList<Package> packages = new ArrayList<Package>();

    /**
     * Constructs an empty Package object
     */
    public Package() {

    }

    /**
     * Constructs a Package object
     * @param trackingNumber a unique string of alphanumeric values used for tracking a package
     * @param type specifies the type package
     * @param specification specifies the specification of a package
     * @param mailingClass specifies the mailing class of a package
     * @param weight specifies the weight of the object
     * @param volume specifies the volumne of the object
     */
    public Package(String trackingNumber, String type, String specification, String mailingClass, float weight,
                   int volume) {

        mTrackingNumber = trackingNumber;
        mType = type;
        mSpecification = specification;
        mMailingClass = mailingClass;
        mWeight = weight;
        mVolume = volume;
    }

    /**
     * @return mTrackingNumber a private member
     */
    public String getTrackingNumber() {

        return mTrackingNumber;
    }

    /**
     * @return mType a private member
     */
    public String getType() {

        return mType;
    }

    /**
     * @return mSpecification a private member
     */
    public String getSpecification() {

        return mSpecification;
    }

    /**
     * @return mMailingClass a private member
     */
    public String getMailingClass() {

        return mMailingClass;
    }

    /**
     * @return mWeight a private member
     */
    public float getWeight() {

        return mWeight;
    }

    /**
     * @return mVolume a private member
     */
    public int getVolume() {

        return mVolume;
    }

    /**
     *  set value of mTrackingNumber, a private member
     */
    public void setTrackingNumber(String trackingNumber) {

        mTrackingNumber = trackingNumber;
    }

    /**
     * set value of mType, a private member
     */
    public void setType(String type) {

        mType = type;
    }

    /**
     * set value of mSpecification, a private member
     */
    public void setSpecification(String specification) {

        mSpecification = specification;
    }

    /**
     * set value of mMailingClass, a private member
     */
    public void setMailingClass(String mailingClass) {

        mMailingClass = mailingClass;
    }

    /**
     * set value of mWeight, a private member
     */
    public void setWeight(float weight) {

        mWeight = weight;
    }

    /**
     * set value of mVolume, a private member
     */
    public void setVolume(int volume) {

        mVolume = volume;
    }

    /**
     * @return string representation of a Package object
     */
    @Override
    public String toString() {

        return (mTrackingNumber + " " +  mType + " " + mSpecification + " " +
                mMailingClass + " " + mWeight + " " + mVolume);
    }

    /**
     * Prompts user for package information then validates information and restarts the loop after encountering first
     * failed validation test, in which case the updated member variables will not be saved.
     * If data is valid, all Package object member variables remains updated then the loop breaks
     */
    public void createPackage() {

        Scanner userInput = new Scanner(System.in);
        boolean isValidData = false;

        System.out.printf("Follow the order below, when entering package information%n" +
                           "      TrackingNumber Type Specification Class Weight Volume%n%n" +
                           "e.g.  12345 Envelope Do-not-Bend First-Class 22.5 80%n%n");

        do {
            System.out.printf("Please enter package information as shown above:  ");
            String inputStream = userInput.nextLine();
            System.out.println();

            String[] splitInputStream = inputStream.split(" ");

            if (splitInputStream.length < 6) {
                System.out.println("`-->   *** Insufficient data entered. Please check your entries and try again");
                continue;
            }

            if (isValidTrackingNumber(splitInputStream[0])) {
                mTrackingNumber = splitInputStream[0];
            } else {
                System.out.println("`-->   *** Invalid tracking number, enter alphanumeric characters (of length 5)" +
                                   " only");
                continue;
            }

            if (isValidType(splitInputStream[1])) {
                mType = splitInputStream[1];
            } else {
                System.out.println("`-->   *** Invalid type, enter a valid package type --> " +
                                   "[ Postcard, Letter, Envelope, Package, Box, Crate, Drum, Roll, or Tube ]");
                continue;
            }

            if (isValidSpecification(splitInputStream[2])) {
                mSpecification = splitInputStream[2];
            } else {
                System.out.println("`-->   *** Invalid specification, enter valid specification --> " +
                                   "[ Fragile, Books, Catalogs, Do-not-Bend, N/A ]");
                continue;
            }

            if (isValidMailingClass(splitInputStream[3])) {
                mMailingClass = splitInputStream[3];
            } else {

                System.out.println("`-->   *** Invalid mailing class, enter valid mailing class --> " +
                                   "[ First-Class, Priority, Retail, Ground, Metro ]");
                continue;
            }

            try {
                mWeight = Float.parseFloat(splitInputStream[4]);
                if (!isValidWeight(mWeight)) {
                    System.out.println("`-->   *** Invalid weight, enter valid weight (Weight must be > 0 )");
                    continue;
                }
            } catch (IllegalArgumentException iae) {
                System.out.println("`-->   *** Invalid weight, enter integer/decimal values only");
                continue;
            }

            try {
                mVolume = Integer.parseInt(splitInputStream[5]);
                if (!isValidVolume(mVolume)) {
                    System.out.println("`-->   ***Invalid volume, enter a valid volume. (Volume must be > 0 )");
                    continue;
                }
            } catch (IllegalArgumentException iae) {
                System.out.println("`-->   *** Invalid volume, enter integer values only");
                continue;
            }
            isValidData = true;

        } while (!isValidData);
    }

    /**
     * Adds a package to packages (an ArrayList)
     */
    public void addPackageToMemory() {

        packages.add(this);
        System.out.println("`-->   Success!, new package added to records");
    }

    /**
     * Writes header [ Tracking # | Type | Specification | Class | Weight | Volume ] in the output file. Then it writes
     * all packages from the ArrayList to the output file.
     */
    static public void exportPackagesToFile() {

//        File file = new File(filename);
//        file.createNewFile();

        try (FileOutputStream fos = new FileOutputStream(filename);
              PrintWriter outputFile = new PrintWriter(fos )) {

             outputFile.println(" -----------------------------------------------------------------------------------");
             outputFile.println("|   TRACKING # |        TYPE | SPECIFICATION |        CLASS |    WEIGHT |    VOLUME |");
             outputFile.println(" -----------------------------------------------------------------------------------");

             for (Package p: packages) {
                 outputFile.printf("|%14s|%13s|%15s|%14s|%11.2f|%11d|%n",
                                     p.getTrackingNumber().toUpperCase(), p.getType(),
                                     p.getSpecification(), p.getMailingClass(),
                                     p.getWeight(), p.getVolume());
             }

        } catch (IOException ioe) {
            System.out.printf("`-->   *** Problem saving data to %s %n", filename);
            ioe.printStackTrace();
        }
    }

    /**
     * Ignores the first three lines, which contains the header information, then reads each line and parses the String
     * for the tracking number, type,  specification, class, weight, and volume, in that order. A package object is
     * created using the data retrieved and that object is then added to an ArrayList of packages.
     */
    static public void importPackagesFromFile() {

        boolean isRead = false;

        try (
                FileInputStream fis = new FileInputStream(filename);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        ){
            String line;

            int i = 0;

            while((line = reader.readLine()) != null) {

                if ( i >= 3) {
                    isRead = true;

                    String trimmedLine = line.replaceAll("\\s+", "");
                    String removedPipeLine = trimmedLine.replaceAll("\\|", " ").trim();

                    String[] args = removedPipeLine.split(" ");

                    float weight = Float.parseFloat(args[4]);
                    int volume = Integer.parseInt(args[5]);
                    Package newPackage = new Package(args[0], args[1], args[2], args[3], weight, volume);
                    packages.add(newPackage);
                }
                i++;
            }
            reader.close();

        } catch (IOException ioe ) {
            System.out.printf("`-->   *** Problem importing data from %s%n", filename);
//            ioe.printStackTrace();
        }

        if (!isRead) {
            System.out.printf("`-->   %s is empty or doesn't exist%n", filename);
        }
    }

    /**
     * Displays all existing packages on the console
     */
    static  public void showAllExistingPackages() {

        System.out.println(" -----------------------------------------------------------------------------------");
        System.out.println("|   TRACKING # |        TYPE | SPECIFICATION |        CLASS |    WEIGHT |    VOLUME |");
        System.out.println(" -----------------------------------------------------------------------------------");

        for (Package p: packages) {
            System.out.printf("|%14s|%13s|%15s|%14s|%11.2f|%11d|%n",
                    p.getTrackingNumber().toUpperCase(), p.getType(),
                    p.getSpecification(), p.getMailingClass(),
                    p.getWeight(), p.getVolume());
        }
        System.out.println("|                                                                                   |");
        System.out.println(" -----------------------------------------------------------------------------------");
        System.out.println();
    }

    /**
     * Deletes a package from the ArrayList of packages (if one exists matching the tracking number provided)
     */
    static public void deletePackage() {

        try {
            Scanner userInput = new Scanner(System.in);
            boolean isDeleted = false;

            System.out.printf("Enter tracking number: ");
            String trackingNumber = userInput.nextLine().trim();

            for (Package p: packages ) {
                if(p.getTrackingNumber().equalsIgnoreCase(trackingNumber)) {
                    packages.remove(p);
                    System.out.printf("`-->   Package with Tracking Number#: %s was deleted%n", trackingNumber);
                    isDeleted = true;
                }
            }
            if (!isDeleted) {
                System.out.printf("`-->   *** Package with Tracking Number#: %s does not exist%n", trackingNumber);
            }

        } catch (ConcurrentModificationException cme) {
        }

    }

    /**
     * Displays a package matching the tracking number provided
     * (if one exists matching that tracking number)
     */
    static public void findPackage() {

        Scanner userInput = new Scanner(System.in);
        boolean isFound = false;

        System.out.printf("Enter tracking number: ");
        String trackingNumber = userInput.nextLine().trim();

        for ( Package p: packages ) {
            if(p.getTrackingNumber().equalsIgnoreCase(trackingNumber)) {
                System.out.println("`-->     " + p);
                isFound = true;
            }
        }

        if (!isFound) {
            System.out.printf("`-->   *** Package with Tracking Number#: %s does not exist%n", trackingNumber);
        }
    }

    /**
     * Displays all objects within the weight range specified by the user. Feedback is given to the user if no package
     * matched the specified range given
     */
    static  public void findPackagesWithinWeightRange(){

        Scanner userInput = new Scanner(System.in);
        boolean isFound = false;

        System.out.printf("Enter min weight: ");
        String firstInput = userInput.nextLine().trim();
        float minWeight = Float.parseFloat(firstInput);


        System.out.printf("Enter max weight: ");
        String secondInput = userInput.nextLine().trim();
        float maxWeight = Float.parseFloat(secondInput);

        for (Package p: packages ) {
            if(p.getWeight() >= minWeight && p.getWeight() <= maxWeight)  {
                System.out.println("`-->     " + p);
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.printf("`-->   *** Package within range %.2f and %.2f does not exist%n", minWeight, maxWeight );
        }
    }

    // Methods that validates user's input against program specified requirements
    /**
     * Runs validation check (for alphanumeric characters only) against a string of characters given by the end-user
     * @param s a string of characters given by the end-user
     * @return true if the input given by the user contains alphanumeric characters only, false if it does not
     */
    private boolean isAlphaNumeric(String s) {

        String pattern = "^[a-zA-Z0-9]*$";
        return s.matches(pattern);
    }

    /**
     * Runs a validation check (for a unique set of alphanumeric characters of length five (5)) against a string of
     * characters given by the end-user.
     * @param s a string of characters given by the end-user
     * @return true if string is valid, false if it fails the test
     */
    private boolean isValidTrackingNumber(String s ) {

        if(isAlphaNumeric(s) && s.length() == TN_MAX_LENGTH) {
            for (Package p: packages) {
                if (p.getTrackingNumber().equalsIgnoreCase(s)) {
                    System.out.printf("`-->   *** Package with the given tracking number: %s already exist%n", s);
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Runs a validation check against a list of valid types
     * @param s a string of characters given by the end-user
     * @return true if string is valid, false if it fails the test
     */
    private  boolean isValidType(String s) {

        for ( String type: typeOptions) {
            if (s.equalsIgnoreCase(type)){
                return true;
            }
        }
        return false;
    }

    /**
     * Runs a validation check against a list of valid specifications
     * @param s a string of characters given by the end-user
     * @return true if string is valid, false if it fails the test
     */
    private boolean isValidSpecification(String s) {

        for ( String spec: specOptions) {
            if (s.equalsIgnoreCase(spec)){
                return true;
            }
        }
        return false;
    }

    /**
     * Runs a validation check against a list of valid mailing class
     * @param s a string of characters given by the end-user
     * @return true if string is valid, false if it fails the test
     */
    private boolean isValidMailingClass(String s) {

        for ( String mClass: mailingClassOptions) {
            if (s.equalsIgnoreCase(mClass)){
                return true;
            }
        }
        return false;
    }

    /**
     * Runs a validation check against the data provided by the end-user. Value must be greater than zero (0)
     * @param f a float value given by the end-user
     * @return true if string is valid, false if it fails the test
     */
    private  boolean isValidWeight(Float f) {

        if (f > 0 ) {
            return true;
        }
        return false;
    }

    /**
     * Runs a validation check against the data provided by the end-user. Value must be greater than zero (0)
     * @param i an integer value given by the end-user
     * @return true if string is valid, false if it fails the test
     */
    private boolean isValidVolume(int i ) {

        if (i > 0) {
            return true;
        }
        return  false;
    }

    /**
     * Takes a String and formats it to title case
     * @param s
     * @return s title case version of s
     */
    private String formattedString(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}