package c1;

import app.customer;
import app.printOrder;
import app.generateInvoice;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * generateInvoiceTest.java (MUT#11)
 * Package c1: C.1 (Valid Case Tests).
 */
public class generateInvoiceTest {
    private generateInvoice invoiceGenerator;

    @Before
    public void setUp() {
        invoiceGenerator = new generateInvoice();
    }

    @Test
    public void testGenerateInvoiceStandardOrder() {
        // MUT#11 TC#1 — Equivalence Partitioning: Generate invoice for a standard order without optional services or discounts
        customer c = new customer(103, "Charlie Lim", "charliel315@gmail.com", "0182346789", "Regular", 0);
        printOrder order = new printOrder(c, "Black & White", "A4", "Single-sided", 20, 2, "None", false, false);
        order.setBasePrintingCharge(8.00);
        order.setAdditionalServiceCharges(0.00);
        order.setDiscounts(0.00);
        order.setTotalPrintingCharge(8.00);

        String invoice = invoiceGenerator.generateInvoice(order);

        assertNotNull(invoice);
        assertTrue(invoice.contains("Charlie Lim"));
        assertTrue(invoice.contains("103"));
        assertTrue(invoice.contains("Base Printing Charge  : RM 8.00"));
        assertTrue(invoice.contains("TOTAL AMOUNT PAYABLE  : RM 8.00"));
    }

    @Test
    public void testGenerateInvoiceWithOptionalServices() {
        // MUT#11 TC#2 — Equivalence Partitioning: Generate invoice itemising optional services (Spiral Binding & Lamination)
        customer c = new customer(105, "Jun Yu", "yxwjy1012@gmail.com", "0140021012", "Student", 1);
        printOrder order = new printOrder(c, "Colour", "A3", "Double-sided", 50, 3, "Spiral", true, false);
        order.setBasePrintingCharge(210.00);
        order.setAdditionalServiceCharges(233.00); // Spiral 8 + Lamination 1.50x150=225 -> 233
        order.setDiscounts(0.00);
        order.setTotalPrintingCharge(443.00);

        String invoice = invoiceGenerator.generateInvoice(order);

        assertNotNull(invoice);
        assertTrue(invoice.contains("Spiral"));
        assertTrue(invoice.contains("Base Printing Charge  : RM 210.00"));
        assertTrue(invoice.contains("Optional Service Fee  : RM 233.00"));
        assertTrue(invoice.contains("TOTAL AMOUNT PAYABLE  : RM 443.00"));
    }

    @Test
    public void testGenerateInvoiceWithDiscounts() {
        // MUT#11 TC#3 — Equivalence Partitioning: Generate invoice displaying student discount and reduced final payable amount
        customer c = new customer(111, "Grace", "grace23@gmail.com", "0190876542", "Student", 5);
        printOrder order = new printOrder(c, "Black & White", "A4", "Single-sided", 100, 1, "None", false, false);
        order.setBasePrintingCharge(20.00);
        order.setAdditionalServiceCharges(0.00);
        order.setDiscounts(2.00);
        order.setTotalPrintingCharge(18.00);

        String invoice = invoiceGenerator.generateInvoice(order);

        assertNotNull(invoice);
        assertTrue(invoice.contains("Grace"));
        assertTrue(invoice.contains("Discount Amount       : RM 2.00"));
        assertTrue(invoice.contains("TOTAL AMOUNT PAYABLE  : RM 18.00"));
    }

    @Test
    public void testGenerateInvoiceFullyLoadedOrder() {
        // MUT#11 TC#4 — Equivalence Partitioning: Fully-loaded order with all optional services and stacked discounts
        customer c = new customer(101, "Joseph Lee", "josephd311@gmail.com", "0113341987", "Corporate", 25);
        printOrder order = new printOrder(c, "Colour", "A3", "Double-sided", 80, 10, "Comb", true, true);
        order.setBasePrintingCharge(1120.00);
        order.setAdditionalServiceCharges(1225.00); // Comb 5 + Lamination 1.50x800=1200 + Express 20 = 1225
        order.setDiscounts(547.50);
        order.setTotalPrintingCharge(1797.50);

        String invoice = invoiceGenerator.generateInvoice(order);

        assertNotNull(invoice);
        assertTrue(invoice.contains("Joseph Lee"));
        assertTrue(invoice.contains("Corporate"));
        assertTrue(invoice.contains("TOTAL AMOUNT PAYABLE  : RM 1797.50"));
    }
}
