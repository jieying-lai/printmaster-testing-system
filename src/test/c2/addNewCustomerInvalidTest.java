package c2;

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
 * addNewCustomerInvalidTest.java (MUT#3)
 * Package c2: C.2 (Invalid Case Tests).
 */
@RunWith(JUnitParamsRunner.class)
public class addNewCustomerInvalidTest {
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

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
        "101, Duplicate ID User, newemail101@gmail.com, 0171112233", // Duplicate ID 101
        "107, Charlie Duplicate Email, charliel315@gmail.com, 0178902345", // Duplicate Email charliel315@gmail.com
        "109, Charlie Duplicate Phone, charlie234@gmail.com, 0182346789" // Duplicate Phone 0182346789
    })
    public void testRegisterCustomerDuplicateErrors(int id, String name, String email, String phone) {
        // MUT#3 — Equivalence Partitioning: Duplicate customer ID, email, or phone number rejection
        customerManager.registerCustomer(id, name, email, phone);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
        "grace23.gmail.com",
        "grace23@",
        "invalidemail"
    })
    public void testRegisterCustomerInvalidEmailFormat(String invalidEmail) {
        // MUT#3 — Equivalence Partitioning: Invalid email format rejection
        customerManager.registerCustomer(115, "Grace", invalidEmail, "0190876599");
    }
}
