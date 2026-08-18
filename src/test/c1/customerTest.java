package c1;

import app.customer;
import app.readCustomer;
import app.addNewCustomer;
import app.printOrder;
import app.calculatePrintingCharge;
import app.generateInvoice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.*;

/**
 * ValidCasesTest.java (MUT#1, MUT#2, MUT#3, MUT#4, MUT#6, MUT#7, MUT#8, MUT#11)
 * Package c1: C.1 (Valid Case Tests).
 */
@RunWith(JUnitParamsRunner.class)
public class customerTest {

    @Test
    public void testCustomerConstructorAndGetters() {
        // MUT#1 — Equivalence Partitioning: Valid customer instantiation and detail retrieval
        customer c = new customer(101, "Joseph Lee", "josephd311@gmail.com", "0113341987", "Corporate", 25);
        assertEquals(101, c.getId());
        assertEquals("Joseph Lee", c.getName());
        assertEquals("josephd311@gmail.com", c.getEmail());
        assertEquals("0113341987", c.getPhoneNumber());
        assertEquals("Corporate", c.getCustomerType());
        assertEquals(25, c.getOrderCount());
    }

    @Test
    public void testRetrieveCustomerMethod() {
        // MUT#1 — Equivalence Partitioning: Valid customer retrieval logic with 0 previous orders
        customer c = new customer();
        customer result = c.retrieveCustomer(103, "Charlie Lim", "charliel315@gmail.com", "0182346789", "Regular", 0);
        assertNotNull(result);
        assertEquals(103, result.getId());
        assertEquals("Charlie Lim", result.getName());
        assertEquals(0, result.getOrderCount());
    }

    @Test
    public void testSetters() {
        // MUT#1 — Equivalence Partitioning: Valid field mutation via setters
        customer c = new customer();
        c.setId(105);
        c.setName("Jun Yu");
        c.setEmail("yxwjy1012@gmail.com");
        c.setPhoneNumber("0140021012");
        c.setCustomerType("Student");
        c.setOrderCount(1);

        assertEquals(105, c.getId());
        assertEquals("Jun Yu", c.getName());
        assertEquals("Student", c.getCustomerType());
        assertEquals(1, c.getOrderCount());
    }
}
