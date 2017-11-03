package shippingstore;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.nio.channels.Channels;

import static org.junit.Assert.*;

public class ShippingStoreTest {

    private ShippingStore shippingStore;
    private PackageOrder packageOrder;
    String separator = System.getProperty("line.separator");
    String expectedOutput;
    String expectedInput;
    final String expectedHeader =  " -------------------------------------------------------------------------- " + separator +
                                   "| Tracking # | Type    | Specification | Class       | Weight(oz) | Volume |" + separator +
                                   " -------------------------------------------------------------------------- " + separator;
    final String expectedFooter =  " --------------------------------------------------------------------------" + separator + separator;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * This method instantiates all the necessary member objects used in the test methods before each test method is ran
     * @throws Exception
     */
    @Before
    public  void createEnvironment() throws Exception {
        shippingStore = new ShippingStore();
        shippingStore.addOrder("56789", "Postcard", "Do-not-Bend", "Metro",
                "0.3", "2");
        packageOrder = shippingStore.getPackageOrder(0);

        System.out.println("Set Up Environment");
    }


    /**
     * This method sets all the member object used with the class back to null after each test method has been
     * executed
     * @throws Exception
     */
    @After
    public void clearEnvironment() throws Exception{
        shippingStore = null;
        packageOrder = null;
        expectedOutput = null;
        expectedInput = null;

        System.out.println("Cleared Environment");
    }


    /**
     * This method tests that the getDataFile method returns the expected File object
     * @throws Exception
     */
    @Test
    public void testGetDataFile() throws Exception {
        assertEquals("File reference must be \'PackageOrderDB.txt\'", new File("PackageOrderDB.txt"),
                     shippingStore.getDataFile());
    }


    /**
     * This method tests that the showPackageOrder method returns the expected output when the list is not empty
     * @throws Exception
     */
    @Test
    public void testShowPackageOrdersWhenListIsNotEmpty() throws Exception {
        PrintStream originalOut = System.out;

        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        shippingStore.showPackageOrders();
        expectedOutput = expectedHeader;
        expectedOutput += String.format("| %-11s| %-8s| %-14s| %-12s| %-11s| %-7s|",
                          packageOrder.getTrackingNumber(),
                          packageOrder.getType(),
                          packageOrder.getSpecification(),
                          packageOrder.getMailingClass(),
                          String.format("%.2f", packageOrder.getWeight()),
                          Integer.toString(packageOrder.getVolume())) + separator;
        expectedOutput += expectedFooter;
        assertEquals(expectedOutput, os.toString());

        System.setOut(originalOut);
    }


    /**
     * This method tests that the showPackageOrder method returns the expected output when the list is empty
     * @throws Exception
     */
    @Test
    public void testShowPackageOrdersWhenListIsEmpty() throws Exception {
        PrintStream originalOut = System.out;

        shippingStore.removeOrder("56789");
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        shippingStore.showPackageOrders();
        expectedOutput = expectedHeader + expectedFooter;
        assertEquals(expectedOutput, os.toString());

        System.setOut(originalOut);
    }


    /**
     * This method tests the showPackageOrdersRange method that the expected output is printed to the console when
     * there are packages fitting the range provided
     * @throws Exception
     */
    @Test
    public void testShowPackageOrdersRangeWhenPackagesExistInSpecifiedRange() throws Exception {
        PrintStream originalOut = System.out;

        shippingStore.addOrder("18888", "Postcard", "Do-not-Bend", "Metro",
                "0.5", "2");

        shippingStore.addOrder("67890", "Postcard", "Do-not-Bend", "Metro",
                "10", "2");
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        shippingStore.showPackageOrdersRange(0, 10);
        expectedOutput = expectedHeader;
        expectedOutput +=  "| 56789      | Postcard| Do-not-Bend   | Metro       | 0.30       | 2      |" + separator +
                           "| 18888      | Postcard| Do-not-Bend   | Metro       | 0.50       | 2      |" + separator +
                           "| 67890      | Postcard| Do-not-Bend   | Metro       | 10.00      | 2      |" + separator;
        expectedOutput += expectedFooter;
        assertEquals(expectedOutput, os.toString());

        System.setOut(originalOut);
    }


    /**
     * This method tests that the showPackageOrdersRange expected output is printed to the console when there are not
     * packages fitting the range provided
     * @throws Exception
     */
    @Test
    public void testShowPackageOrdersRangeWhenPackagesDoesNotExistInSpecifiedRange() throws Exception {

        PrintStream originalOut = System.out;

        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        shippingStore.showPackageOrdersRange(11, 100);
        expectedOutput = "No packages found with weight within the given range.\n" + separator;

        assertEquals(expectedOutput, os.toString());

        System.setOut(originalOut);
    }


    /**
     * This method tests that the findPackageOrder method returns the expected value when the package exist in database
     * @throws Exception
     */
    @Test
    public void tesFindPackageOrderWhenOrderExists() throws Exception {
        PrintStream originalOut = System.out;

        shippingStore.addOrder("12345", "Postcard", "Do-not-Bend", "Metro",
                "0.3", "2");
        shippingStore.addOrder("67890", "Postcard", "Do-not-Bend", "Metro",
                "0.3", "2");

        int results = shippingStore.findPackageOrder("67890");
        assertEquals(2, results);

        System.setOut(originalOut);
    }


    /**
     * This method tests that the findPackageOrder method returns the expected value when the package does not exist in
     * the database
     * @throws Exception
     */
    @Test
    public void tesFindPackageOrderWhenOrderDoestNotExists() throws Exception {

        int results = shippingStore.findPackageOrder("00000");
        assertEquals(-1, results);
    }


    /**
     * This method tests the searchPackageOrder method for the specified output when the package exists is not in
     * the database
     * @throws Exception
     */
    @Test
    public void testSearchPackageOrderWhenPackageDoesNotExistsInList() throws Exception {

        PrintStream originalOut = System.out;

        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        expectedOutput = "\nSearch did not find a match.\n" + separator;

        shippingStore.searchPackageOrder("11111");
        assertEquals(expectedOutput, os.toString());

        System.setOut(originalOut);
    }


    /**
     * This method tests the searchPackageOrder method for the specified output when the package exists in the database
     * @throws Exception
     */
    @Test
    public void testSearchPackageOrderWhenPackageExistsInList() throws Exception {

        PrintStream originalOut = System.out;

        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        expectedOutput = "\nHere is the order that matched:\n" + separator;
        expectedOutput += expectedHeader;
        expectedOutput += String.format("| %-11s| %-8s| %-14s| %-12s| %-11s| %-7s|",
                packageOrder.getTrackingNumber(),
                packageOrder.getType(),
                packageOrder.getSpecification(),
                packageOrder.getMailingClass(),
                String.format("%.2f", packageOrder.getWeight()),
                Integer.toString(packageOrder.getVolume())) + separator;
        expectedOutput += expectedFooter;

        shippingStore.searchPackageOrder("56789");

        assertEquals(expectedOutput, os.toString());

        System.setOut(originalOut);
    }


    /**
     * This method tests the addOrder method for the specified console output when an invalid tracking number is given
     * for the tracking number field
     * @throws Exception
     */
    @Test
    public void testAddOrderWithInvalidTrackingNumberForPackageInformation() throws Exception {

        PrintStream originalOut = System.out;

        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        // invalid tracking number
        shippingStore.addOrder("1234", "Postcard", "Do-not-Bend", "Metro",
                               "0.3", "2");

        expectedOutput = "Invalid Tracking Number: not proper format."
                       + "Tracking Number must be at least 5 alphanumeric characters." + separator;

        assertEquals(expectedOutput, os.toString());

        System.setOut(originalOut);
    }


    /**
     * This method tests the addOrder method for the specified console output when an invalid type is given for the
     * type field
     * @throws Exception
     */
    @Test
    public void testAddOrderWithInvalidTypeForPackageInformation() throws Exception {
        PrintStream originalOut = System.out;

        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        shippingStore.addOrder("12345", "Postard", "Do-not-Bend", "Metro",
                "0.3", "2");

        expectedOutput = "Invalid type:\n" +
                "Type must be one of following: " +
                "Postcard, Letter, Envelope, Packet, Box, Crate, Drum, Roll, Tube." + separator;

        assertEquals(expectedOutput, os.toString());

        System.setOut(originalOut);
    }


    /**
     * This method tests the addOrder method for a NumberFormatException when invalid information is provided for the
     * weight field
     * @throws Exception
     */
    @Test
    public void testForNumberFormatExceptionWhenAddingOrderWeight() throws Exception {
        try {
            shippingStore.addOrder("12345", "Postcard", "Do-not-Bend",
                                   "Metro", "CAUSE FAILURE", "2");
            fail("Should've thrown a \'Number Format Exception!\'");

        } catch (NumberFormatException expected) { }
    }


    /**
     * This method tests the addOrder method. It checks that the specified console output is return when there's an
     * attempt to add an order with valid information
     * @throws Exception
     */
    @Test
    public void testAddOrderWithValidPackageInformation() throws Exception {
        PrintStream originalOut = System.out;

        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        shippingStore.addOrder("12345", "Postcard", "Do-not-Bend", "Metro",
                               "0.3", "2");

        expectedOutput = "Package Order has been added.\n" + separator;

        assertEquals(expectedOutput, os.toString());

        System.setOut(originalOut);
    }


    /**
     * This method test that an order is removed if it existed in the database
     * @throws Exception
     */
    @Test
    public void testRemoveOrderThatExists() throws Exception {
        PrintStream originalOut = System.out;

        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        shippingStore.removeOrder("56789");

        expectedOutput = "\nAction successful. Package order has been removed from the database.\n" + separator;

        assertEquals(expectedOutput, os.toString());

        System.setOut(originalOut);
    }


    /**
     * This method tests that the specified console output is return when there's an attempt to remove an order that
     * does not exist in the database
     * @throws Exception
     */
    @Test
    public void testRemoveOrderThatDoestNotExists() throws Exception {
        PrintStream originalOut = System.out;

        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        expectedOutput = "\nAction failed. No package order with the given tracking # exist in database.\n" + separator;
        shippingStore.removeOrder("00000");

        assertEquals(expectedOutput, os.toString());

        System.setOut(originalOut);
    }


    /**
     * This method tests that a package object is returned when a valid index is provided
     * @throws Exception
     */
    @Test
    public void testGetPackageOrderWithValidIndexValue() throws Exception {

        String packageReturned = shippingStore.getPackageOrder(0).getTrackingNumber();
        assertEquals( "0 index must return first package in the list","56789", packageReturned);
    }


    /**
     * This method tests the getPackageOrder method. It checks that a null object is returned when an invalid index is
     * provided. It also does a comparison for the expected output the function should console out
     * @throws Exception
     */
    @Test
    public void testGetPackageOrderWithInvalidIndexValue() throws Exception {
        PrintStream originalOut = System.out;

        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        PackageOrder packageOrder = shippingStore.getPackageOrder(-1);
        assertNull( "1 is not a valid index", packageOrder);

        expectedOutput = "Invalid Index. Please enter another command or 'h' to list the commands." + separator;
        assertEquals(expectedOutput, os.toString());

        System.setOut(originalOut);
    }


    /**
     * This method read function throws the expected exception when file does not exists
     * @throws Exception (FileNotFoundException)
     */
    @Test (expected = FileNotFoundException.class)
    public void testReadWithFileThatDoesNotExist() throws Exception {
        FileReader dataReader = new FileReader("fileDoesNotExist.txt");
        shippingStore.read(dataReader);
    }


    /**
     * This method tests that read method operates as expected and is successfully when valid input is provided
     * @throws Exception
     */
    @Test
    public void testReadWithValidInputString() throws Exception {
        PrintStream originalOut = System.out;

        String data = "88888 Postcard Do-Not-Bend Metro 0.8 8";
        Reader input = new StringReader(data);

        shippingStore.read(input);
        PackageOrder newPackage = shippingStore.getPackageOrder(1);
        assertEquals("88888", newPackage.getTrackingNumber());

        // if read method was successful then the package should have been added to the packageOrderList
        // to check is that is the case, call showPackages to see if the order is in the list
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        expectedOutput = expectedHeader;
        expectedOutput += String.format("| %-11s| %-8s| %-14s| %-12s| %-11s| %-7s|",
                packageOrder.getTrackingNumber(),
                packageOrder.getType(),
                packageOrder.getSpecification(),
                packageOrder.getMailingClass(),
                String.format("%.2f", packageOrder.getWeight()),
                Integer.toString(packageOrder.getVolume())) + separator;
        expectedOutput += String.format("| %-11s| %-8s| %-14s| %-12s| %-11s| %-7s|",
                newPackage.getTrackingNumber(),
                newPackage.getType(),
                newPackage.getSpecification(),
                newPackage.getMailingClass(),
                String.format("%.2f", newPackage.getWeight()),
                Integer.toString(newPackage.getVolume())) + separator;
        expectedOutput += expectedFooter;

        shippingStore.showPackageOrders();

        assertEquals(expectedOutput, os.toString());

        System.setOut(originalOut);
    }


    /**
     * This method tests for expected exception 'NumberFormatException' is thrown when the substrings that should be
     * containing digits do not contain any. The expected exception is thrown when ParseInt or ParseFloat attempts to
     * parse the string and there is not digits to return
     * @throws Exception (NumberFormatException)
     */
    @Test (expected = NumberFormatException.class)
    public void  testReadWithInvalidInputToForceAnException() throws Exception {
        String data = "This string does-not-contain float or int values to parse or data with right amount of space" +
                       "    between      each          substring" ;
        Reader input = new StringReader(data);

        shippingStore.read(input);
    }


    /**
     * This method tests the read method when the user enters insufficient amount of information required. In this case
     * if the user enters less than 5 substrings in a single line, the expected exception 'ArrayOutOfBoundsException' is
     * thrown.
     * @throws Exception (ArrayIndexOutOfBoundsException)
     */
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void testReadWithInsufficientAmountStringsToBeParsedByArray() throws Exception {
        String data = "Not enough";
        Reader input = new StringReader(data);

        shippingStore.read(input);
    }


    /**
     * This method tests the flush method using a read only file. In this case the method should thrown an exception
     * because the file cannot be accessed
     * @throws Exception (IOException)
     */
    @Test (expected = IOException.class)
    public void testFlushWithFileInReadModeOnly() throws Exception {
        RandomAccessFile raFile = new RandomAccessFile("raFileTest.txt", "r");
        OutputStreamWriter writer = new OutputStreamWriter(Channels.newOutputStream(raFile.getChannel()));
        shippingStore.flush(writer);
    }


    /**
     * This method tests the flush method to ensure that packages are being stored in the correct way
     * @throws Exception
     */
    @Test
    public void testFlush()  throws  Exception{
        StringWriter output = new StringWriter();
        shippingStore.flush(output);

        String string = new String(output.toString());
        expectedInput = "56789 Postcard Do-not-Bend Metro 0.30 2" + separator;
        assertEquals(expectedInput, string);
    }
}