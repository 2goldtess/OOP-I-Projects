package shippingstore;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import static org.junit.Assert.*;

public class ShippingStoreTest {

    private static ShippingStore shippingStore = null;
    private static PackageOrder packageOrder = null;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void createEnvironment() throws Exception {
        shippingStore = new ShippingStore();
        packageOrder = new PackageOrder("11111", "Postcard", "Do-not-Bend", "Metro", 0.3f, 2);

        System.out.println("Set Up Environment");
    }

    @AfterClass
    public  static void clearEnvironment() throws Exception{
        shippingStore = null;
        packageOrder = null;

        System.out.println("Cleared Environment");

    }

    @Test
    public void testGetDataFile() throws Exception {
        assertEquals("File reference must be \'PackageOrderDB.txt\'", new File("PackageOrderDB.txt"),
                     shippingStore.getDataFile());
    }

//    @Test
//    public void showPackageOrders() throws Exception {
//    }
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
        shippingStore.addOrder("1111", "Postcard", "Do-not-Bend", "Metro", "0.3", "2");
        // invalid type
        shippingStore.addOrder("11111", "Postard", "Do-not-Bend", "Metro", "0.3", "2");
    }

    @Test
    public void testForNumberFormatExceptionWhenAddingOrder() throws Exception {
        try {
            shippingStore.addOrder("11111", "Postcard", "Do-not-Bend", "Metro", "CAUSE FAILURE", "2");
            fail("Should've thrown an exception!");

        } catch (NumberFormatException expected) { }
    }
//
//    @Test
//    public void removeOrder() throws Exception {
//    }
//
//    @Test
//    public void getPackageOrder() throws Exception {
//    }
//
//    @Test
//    public void read() throws Exception {
//    }
//
//    @Test
//    public void flush() throws Exception {
//    }

}