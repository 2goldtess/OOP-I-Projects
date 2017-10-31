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


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public  void createEnvironment() throws Exception {
        shippingStore = new ShippingStore();
        packageOrder = new PackageOrder("12345", "Postcard", "Do-not-Bend",
                                        "Metro", 0.3f, 2);
        packageOrderList = new ArrayList<>();

        //System.out.println("Set Up Environment");
    }

    @After
    public void clearEnvironment() throws Exception{
        shippingStore = null;
        packageOrder = null;

        //System.out.println("Cleared Environment");

    }

    @Test
    public void testGetDataFile() throws Exception {
        assertEquals("File reference must be \'PackageOrderDB.txt\'", new File("PackageOrderDB.txt"),
                     shippingStore.getDataFile());
    }

    @Test
    public void testShowPackageOrdersWhenListIsNotEmpty() throws Exception {
        shippingStore.addOrder("56789", "Postcard", "Do-not-Bend", "Metro",
                               "0.3", "2");
        shippingStore.showPackageOrders();
    }

    @Test
    public void testShowPackageOrdersWhenListIsEmpty() throws Exception {
        shippingStore.showPackageOrders();
    }
//
//    @Test
//    public void showPackageOrdersRange() throws Exception {

//    }
//
//    @Test
//    public void findPackageOrder() throws Exception {
//    }
//
    @Test
    public void testSearchPackageOrderWhenPackageDoestNotExistsInList() throws Exception {
        shippingStore.addOrder("56789", "Postcard", "Do-not-Bend", "Metro",
                "0.3", "2");

        shippingStore.searchPackageOrder("11111");
    }

    @Test
    public void testAddOrderWithInvalidPackageInformation() throws Exception {
        // invalid tracking number
        shippingStore.addOrder("1234", "Postcard", "Do-not-Bend", "Metro",
                               "0.3", "2");
        // invalid type
        shippingStore.addOrder("12345", "Postard", "Do-not-Bend", "Metro",
                               "0.3", "2");
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
        shippingStore.addOrder("56789", "Postcard", "Do-not-Bend", "Metro",
                               "0.3", "2");
    }

    @Test
    public void testRemoveOrderThatExists() throws Exception {
        shippingStore.addOrder("23456", "Postcard", "Do-not-Bend", "Metro",
                "0.3", "2");
        shippingStore.removeOrder("23456");
    }

    @Test
    public void testRemoveOrderThatDoestNotExists() throws Exception {
        shippingStore.removeOrder("00000");
    }

    @Test
    public void testGetPackageOrderWithValidIndexValue() throws Exception {
        shippingStore.addOrder("23456", "Postcard", "Do-not-Bend", "Metro",
                "0.3", "2");

        String packageReturned = shippingStore.getPackageOrder(0).getTrackingNumber();
        assertEquals( "0 index must return first package in the list","23456", packageReturned);
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

        // if read method was successful then the package should have been added to the packageOrderList
        // to check is that is the case, call showPackages to see if the order is in the list
        shippingStore.showPackageOrders();
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

    @Test (expected = IOException.class)
    public void testFlush() throws Exception {
        RandomAccessFile raFile = new RandomAccessFile("raFileTest.txt", "r");
        OutputStreamWriter writer = new OutputStreamWriter(Channels.newOutputStream(raFile.getChannel()));
        shippingStore.flush(writer);
    }
}