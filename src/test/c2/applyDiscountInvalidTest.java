package c2;

import app.customer;
import app.applyDiscount;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.*;

/**
 * applyDiscountInvalidTest.java (MUT#10)
 * Package c2: C.2 (Invalid Case Tests).
 */
@RunWith(JUnitParamsRunner.class)
public class applyDiscountInvalidTest {
    private applyDiscount discountApp;
    private customer sampleCustomer;

    @Before
    public void setUp() {
        discountApp = new applyDiscount();
        sampleCustomer = new customer(100, "Test User", "test@gmail.com", "0123456789", "Student", 5);
    }

    @Test
    @Parameters({
        "0.00",     // Boundary Value Analysis: subtotal at zero
        "-50.00"    // Equivalence Partitioning: negative subtotal
    })
    public void testApplyDiscountInvalidSubtotalReturnsNoDiscount(double invalidSubtotal) {
        double actualDiscount = discountApp.applyDiscount(sampleCustomer, invalidSubtotal);
        assertEquals(0.0, actualDiscount, 0.001);
    }
}