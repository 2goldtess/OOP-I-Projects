package shippingstore;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.nio.channels.Channels;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ShippingStoreTest {

    private ShippingStore shippingStore;
    private PackageOrder packageOrder;
    private ArrayList<PackageOrder> packageOrderList;
    String separator = System.getProperty("line.separator");
    String expectedOutput;
    final String expectedHeader =  " -------------------------------------------------------------------------- " + separator +
                                   "| Tracking # | Type    | Specification | Class       | Weight(oz) | Volume |" + separator +
                                   " -------------------------------------------------------------------------- " + separator;
    final String expectedFooter =  " --------------------------------------------------------------------------" + separator + separator;

    //TODO check test constructor

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public  void createEnvironment() throws Exception {
        shippingStore = new ShippingStore();
        shippingStore.addOrder("56789", "Postcard", "Do-not-Bend", "Metro",
                "0.3", "2");
        packageOrder = shippingStore.getPackageOrder(0);

        System.out.println("Set Up Environment");
    }

    @After
    public void clearEnvironment() throws Exception{
        shippingStore = null;
        packageOrder = null;
        expectedOutput = null;

        System.out.println("Cleared Environment");
    }

    @Test
    public void testGetDataFile() throws Exception {
        assertEquals("File reference must be \'PackageOrderDB.txt\'", new File("PackageOrderDB.txt"),
                     shippingStore.getDataFile());
    }


    @Test
    public void testShowPackageOrdersWhenListIsNotEmpty() throws Exception {
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
    }


    @Test
    public void testShowPackageOrdersWhenListIsEmpty() throws Exception {
        shippingStore.removeOrder("56789");
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        shippingStore.showPackageOrders();
        expectedOutput = expectedHeader + expectedFooter;
        assertEquals(expectedOutput, os.toString());
    }


    @Test
    public void testShowPackageOrdersRangeWhenPackagesExistInSpecifiedRange() throws Exception {
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
    }

    @Test
    public void testShowPackageOrdersRangeWhenPackagesDoesNotExistInSpecifiedRange() throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        shippingStore.showPackageOrdersRange(11, 100);
        expectedOutput = "No packages found with weight within the given range.\n" + separator;

        assertEquals(expectedOutput, os.toString());
    }

    @Test
    public void tesFindPackageOrderWhenOrderExists() throws Exception {
        shippingStore.addOrder("12345", "Postcard", "Do-not-Bend", "Metro",
                "0.3", "2");

        shippingStore.addOrder("67890", "Postcard", "Do-not-Bend", "Metro",
                "0.3", "2");

        int results = shippingStore.findPackageOrder("67890");
        assertEquals(2, results);
    }

    @Test
    public void tesFindPackageOrderWhenOrderDoestNotExists() throws Exception {
        int results = shippingStore.findPackageOrder("00000");
        assertEquals(-1, results);
    }

    @Test
    public void testSearchPackageOrderWhenPackageDoesNotExistsInList() throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        expectedOutput = "\nSearch did not find a match.\n" + separator;

        shippingStore.searchPackageOrder("11111");
        assertEquals(expectedOutput, os.toString());
    }

    @Test
    public void testSearchPackageOrderWhenPackageExistsInList() throws Exception {

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
    }

    @Test
    public void testAddOrderWithInvalidTrackingNumberForPackageInformation() throws Exception {

        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        // invalid tracking number
        shippingStore.addOrder("1234", "Postcard", "Do-not-Bend", "Metro",
                               "0.3", "2");

        expectedOutput = "Invalid Tracking Number: not proper format."
                       + "Tracking Number must be at least 5 alphanumeric characters." + separator;

        assertEquals(expectedOutput, os.toString());

    }
    @Test
    public void testAddOrderWithInvalidTypeForPackageInformation() throws Exception {

        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        shippingStore.addOrder("12345", "Postard", "Do-not-Bend", "Metro",
                "0.3", "2");

        expectedOutput = "Invalid type:\n" +
                "Type must be one of following: " +
                "Postcard, Letter, Envelope, Packet, Box, Crate, Drum, Roll, Tube." + separator;

        assertEquals(expectedOutput, os.toString());
    }

    @Test
    public void testForNumberFormatExceptionWhenAddingOrderWeight() throws Exception {
        try {
            shippingStore.addOrder("12345", "Postcard", "Do-not-Bend",
                                   "Metro", "CAUSE FAILURE", "2");
            fail("Should've thrown a \'Number Format Exception!\'");

        } catch (NumberFormatException expected) { }
    }

    @Test
    public void testAddOrderWithValidPackageInformation() throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        shippingStore.addOrder("12345", "Postcard", "Do-not-Bend", "Metro",
                               "0.3", "2");

        expectedOutput = "Package Order has been added.\n" + separator;

        assertEquals(expectedOutput, os.toString());
    }

    @Test
    public void testRemoveOrderThatExists() throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        shippingStore.removeOrder("56789");

        expectedOutput = "\nAction successful. Package order has been removed from the database.\n" + separator;

        assertEquals(expectedOutput, os.toString());
    }

    @Test
    public void testRemoveOrderThatDoestNotExists() throws Exception {

        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        expectedOutput = "\nAction failed. No package order with the given tracking # exist in database.\n" + separator;
        shippingStore.removeOrder("00000");

        assertEquals(expectedOutput, os.toString());
    }

    @Test
    public void testGetPackageOrderWithValidIndexValue() throws Exception {

        String packageReturned = shippingStore.getPackageOrder(0).getTrackingNumber();
        assertEquals( "0 index must return first package in the list","56789", packageReturned);
    }

    @Test (expected = FileNotFoundException.class)
    public void testReadWithFileThatDoesNotExist() throws Exception {
        FileReader dataReader = new FileReader("fileDoesNotExist.txt");
        shippingStore.read(dataReader);
    }

    @Test
    public void testReadWithValidInputString() throws Exception {

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
    }

    @Test (expected = NumberFormatException.class)
    public void  testReadWithInvalidInputToForceAnException() throws Exception {
        String data = "This string does-not-contain float or int values to parse or data with right amount of space" +
                       "    between      each          substring" ;
        Reader input = new StringReader(data);

        shippingStore.read(input);
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void testReadWithInsufficientAmountStringsToBeParsedByArray() throws Exception {
        String data = "Not enough";
        Reader input = new StringReader(data);

        shippingStore.read(input);
    }

    /**
     * This
     * @throws Exception
     */
    @Test (expected = IOException.class)
    public void testFlush() throws Exception {
        RandomAccessFile raFile = new RandomAccessFile("raFileTest.txt", "r");
        OutputStreamWriter writer = new OutputStreamWriter(Channels.newOutputStream(raFile.getChannel()));
        shippingStore.flush(writer);
    }
}