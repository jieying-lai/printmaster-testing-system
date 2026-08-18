package c5;

import app.customer;
import app.printOrder;
import app.calculatePrintingCharge;
import app.printerAvailability;
import app.applyDiscount;
import app.generateInvoice;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

/**
 * IntegrationTest.java
 * Package c5: Addresses Rubric Criteria C.5 (Perform integration testing after unit tests have been completed).
 * Tests the complete flow:
 * createPrintOrder -> isPrinterAvailable (mocked) -> calculatePrintingCharge -> applyDiscount -> generateInvoice.
 */
public class IntegrationTest {

    @Mock
    private printerAvailability mockPrinterChecker;

    private applyDiscount realDiscountCalculator;
    private calculatePrintingCharge realChargeCalculator;
    private generateInvoice realInvoiceGenerator;
    private printOrder orderFactory;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        realDiscountCalculator = new applyDiscount();
        realChargeCalculator = new calculatePrintingCharge(mockPrinterChecker, realDiscountCalculator);
        realInvoiceGenerator = new generateInvoice();
        orderFactory = new printOrder();
    }

    /**
     * C.5: Integration Test Path 1 — Printer Available (Success Flow)
     */
    @Test
    public void testIntegrationPrinterAvailableSuccessFlow() {
        // Rubric C.5 — Integration Test: End-to-end integration when printer is available
        customer customerJoseph = new customer(101, "Joseph Lee", "josephd311@gmail.com", "0113341987", "Corporate", 25);
        printOrder order = orderFactory.createPrintOrder(customerJoseph, "Colour", "A3", "Double-sided", 80, 10, "Comb", true, true);
        assertNotNull(order);

        Mockito.when(mockPrinterChecker.isPrinterAvailable("A3", "Colour")).thenReturn(true);

        double totalAmount = realChargeCalculator.processOrderCharge(order);
        assertTrue("Total amount should be greater than zero", totalAmount > 0);

        Mockito.verify(mockPrinterChecker, Mockito.times(1)).isPrinterAvailable("A3", "Colour");

        assertEquals(1120.00, order.getBasePrintingCharge(), 0.01);
        assertEquals(1225.00, order.getAdditionalServiceCharges(), 0.01);
        assertTrue("Discount should be applied", order.getDiscounts() > 0);

        String invoiceText = realInvoiceGenerator.generateInvoice(order);

        assertNotNull(invoiceText);
        assertTrue(invoiceText.contains("PRINTMASTER INVOICE"));
        assertTrue(invoiceText.contains("Joseph Lee"));
        assertTrue(invoiceText.contains("Corporate"));
        assertTrue(invoiceText.contains("TOTAL AMOUNT PAYABLE"));
    }

    /**
     * C.5: Integration Test Path 2 — Printer Unavailable (Termination Flow)
     */
    @Test
    public void testIntegrationPrinterUnavailableTerminationFlow() {
        // Rubric C.5 — Integration Test: End-to-end integration when printer is unavailable (aborts flow)
        customer customerCharlie = new customer(103, "Charlie Lim", "charliel315@gmail.com", "0182346789", "Regular", 0);
        printOrder order = orderFactory.createPrintOrder(customerCharlie, "Black & White", "A4", "Single-sided", 20, 2, "None", false, false);

        Mockito.when(mockPrinterChecker.isPrinterAvailable("A4", "Black & White")).thenReturn(false);

        double resultCharge = realChargeCalculator.processOrderCharge(order);

        assertEquals(-1.0, resultCharge, 0.001);
        assertEquals("Cancelled", order.getOrderStatus());

        Mockito.verify(mockPrinterChecker, Mockito.times(1)).isPrinterAvailable("A4", "Black & White");
    }
}
