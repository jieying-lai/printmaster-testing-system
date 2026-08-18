package c2;

import app.customer;
import app.printOrder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.*;

/**
 * printOrderInvalidTest.java (MUT#5)
 * Package c2: C.2 (Invalid Case Tests).
 */
@RunWith(JUnitParamsRunner.class)
public class printOrderInvalidTest {
    private customer sampleCustomer;
    private printOrder orderValidator;

    @Before
    public void setUp() {
        sampleCustomer = new customer(103, "Charlie Lim", "charliel315@gmail.com", "0182346789", "Regular", 0);
        orderValidator = new printOrder();
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
        "0, 4",    // Boundary Value Analysis: pages = 0 (invalid low)
        "501, 4"   // Boundary Value Analysis: pages = 501 (invalid high per Business Rule 7)
    })
    public void testValidateOrderInvalidPageCount(int pages, int copies) {
        // MUT#5 — Boundary Value Analysis: Number of pages below minimum (0) or exceeding maximum (501 per Business Rule 7)
        orderValidator.validateOrder(sampleCustomer, "Black & White", "A4", "Single-sided", pages, copies, "None", false, false);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
        "10, 0",     // Boundary Value Analysis: copies = 0 (invalid low)
        "10, 1001"   // Boundary Value Analysis: copies = 1001 (invalid high per Business Rule 8)
    })
    public void testValidateOrderInvalidCopyCount(int pages, int copies) {
        // MUT#5 — Boundary Value Analysis: Number of copies below minimum (0) or exceeding maximum (1001 per Business Rule 8)
        orderValidator.validateOrder(sampleCustomer, "Colour", "A3", "Single-sided", pages, copies, "None", false, false);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
        "Ring Binding",
        "Staple+Comb",
        "InvalidBinding"
    })
    public void testValidateOrderInvalidBindingOption(String invalidBinding) {
        // MUT#5 — Equivalence Partitioning: Invalid binding option selection (violates Business Rule 9)
        orderValidator.validateOrder(sampleCustomer, "Colour", "A4", "Single-sided", 20, 2, invalidBinding, false, false);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
        "Greyscale, A4, Single-sided", // Invalid Print Type
        "Black & White, Letter, Single-sided", // Invalid Paper Size
        "Black & White, A4, Both" // Invalid Printing Side
    })
    public void testValidateOrderInvalidCategoricalInputs(String printType, String paperSize, String printingSide) {
        // MUT#5 — Equivalence Partitioning: Categorical inputs out of domain
        orderValidator.validateOrder(sampleCustomer, printType, paperSize, printingSide, 20, 2, "None", false, false);
    }
}
