package shippingstore;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ShippingStoreTest {

    private static ShippingStore shippingStore = null;


    @BeforeClass
    public static void createEnvironment() throws Exception {
        shippingStore = new ShippingStore();
        System.out.println("Set Up Environment");
    }

    @AfterClass
    public  static void clearEnvironment() throws Exception{
        shippingStore = null;
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
//    @Test
//    public void addOrder() throws Exception {
//    }
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