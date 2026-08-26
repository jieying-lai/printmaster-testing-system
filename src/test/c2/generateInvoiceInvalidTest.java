package c2;

import app.generateInvoice;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * generateInvoiceInvalidTest.java (MUT#11)
 * Package c2: C.2 (Invalid Case Tests).
 */
public class generateInvoiceInvalidTest {
    private generateInvoice invoiceGenerator;

    @Before
    public void setUp() {
        invoiceGenerator = new generateInvoice();
    }

    @Test
    public void testGenerateInvoiceNullOrderReturnsErrorMessage() {
        String result = invoiceGenerator.generateInvoice(null);

        assertNotNull(result);
        assertEquals("Error: No print order provided.", result);
        assertFalse(result.contains("PRINTMASTER INVOICE"));
    }
}