package c1;

import app.addNewCustomer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * addNewCustomerTest.java (MUT#3)
 * Package c1: C.1 (Valid Case Tests).
 */
@RunWith(JUnitParamsRunner.class)
public class addNewCustomerTest {
    private addNewCustomer customerManager;
    private static final String TEST_FILE = "customer.txt";

    @Before
    public void setUp() throws IOException {
        resetTestCustomerFile();
        customerManager = new addNewCustomer(TEST_FILE);
    }

    @After
    public void tearDown() throws IOException {
        resetTestCustomerFile();
    }

    private void resetTestCustomerFile() throws IOException {
        File file = new File(TEST_FILE);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write("101,Joseph Lee,josephd311@gmail.com,0113341987,Corporate,25");
            bw.newLine();
            bw.write("103,Charlie Lim,charliel315@gmail.com,0182346789,Regular,0");
            bw.newLine();
            bw.write("105,Jun Yu,yxwjy1012@gmail.com,0140021012,Student,1");
            bw.newLine();
            bw.write("111,Grace,grace23@gmail.com,0190876542,Student,5");
            bw.newLine();
        }
    }

    @Test
    @Parameters({
        "112, Alice Wong, alice112@gmail.com, 0199988776, Regular, 0"
    })
    public void testRegisterCustomerValid(int id, String name, String email, String phone, String type, int orderCount) {
        // MUT#3 — Equivalence Partitioning: Valid customer registration details with unique ID, email, and phone number
        boolean success = customerManager.registerCustomer(id, name, email, phone, type, orderCount);
        assertTrue(success);
    }
}
