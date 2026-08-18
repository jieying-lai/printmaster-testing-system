package c1;

import app.customer;
import app.printOrder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.*;

/**
 * printOrderTest.java (MUT#4 & MUT#5)
 * Package c1: C.1 (Valid Case Tests).
 */
@RunWith(JUnitParamsRunner.class)
public class printOrderTest {
    private customer sampleCustomer;
    private printOrder orderValidator;

    @Before
    public void setUp() {
        sampleCustomer = new customer(103, "Charlie Lim", "charliel315@gmail.com", "0182346789", "Regular", 0);
        orderValidator = new printOrder();
    }

    @Test
    @Parameters({
        "Black & White, A4, Single-sided, 5, 2, None, false, false",
        "Colour, A5, Double-sided, 25, 15, Spiral, true, true"
    })
    public void testCreateAndValidateOrderValid(String printType, String paperSize, String printingSide,
                                                int numOfPages, int numOfCopies, String binding, boolean lamination, boolean express) {
        // MUT#4 & MUT#5 — Equivalence Partitioning: Valid print order creation and validation
        printOrder order = orderValidator.createPrintOrder(sampleCustomer, printType, paperSize, printingSide, numOfPages, numOfCopies, binding, lamination, express);
        assertNotNull(order);
        assertTrue(order.validateOrder());
        assertEquals(printType, order.getPrintType());
        assertEquals(paperSize, order.getPaperSize());
        assertEquals(numOfPages, order.getNumOfPages());
        assertEquals(numOfCopies, order.getNumOfCopies());
    }
}
