package shippingstore;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;
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
//    @Test
//    public void searchPackageOrder() throws Exception {
//    }
//
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

    @Test
    public void read() throws Exception {
        
    }
//
//    @Test
//    public void flush() throws Exception {
//    }

}