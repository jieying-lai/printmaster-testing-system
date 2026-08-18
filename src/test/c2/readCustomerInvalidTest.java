package c2;

import app.readCustomer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.*;

/**
 * readCustomerInvalidTest.java (MUT#2)
 * Package c2: C.2 (Invalid Case Tests).
 */
@RunWith(JUnitParamsRunner.class)
public class readCustomerInvalidTest {
    private readCustomer reader;

    @Before
    public void setUp() {
        reader = new readCustomer("customer.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
        "4356",
        "0",
        "-1"
    })
    public void testGetCustomerByIdInvalid(int invalidId) {
        // MUT#2 — Boundary Value Analysis & Equivalence Partitioning: Non-existent or invalid customer ID (invalid low / non-existent)
        reader.getCustomerById(invalidId);
    }
}
