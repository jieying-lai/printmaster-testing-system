package c1;

import app.customer;
import app.readCustomer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.*;

/**
 * readCustomerTest.java (MUT#2)
 * Package c1: C.1 (Valid Case Tests).
 */
@RunWith(JUnitParamsRunner.class)
public class readCustomerTest {
    private readCustomer reader;

    @Before
    public void setUp() {
        reader = new readCustomer("customer.txt");
    }

    @Test
    @Parameters({
        "101, Joseph Lee, Corporate, 25",
        "103, Charlie Lim, Regular, 0",
        "105, Jun Yu, Student, 1"
    })
    public void testGetCustomerByIdValid(int id, String expectedName, String expectedType, int expectedOrderCount) {
        // MUT#2 — Equivalence Partitioning: Valid existing customer ID retrieval from customer.txt
        customer c = reader.getCustomerById(id);
        assertNotNull(c);
        assertEquals(id, c.getId());
        assertEquals(expectedName, c.getName());
        assertEquals(expectedType, c.getCustomerType());
        assertEquals(expectedOrderCount, c.getOrderCount());
    }
}
