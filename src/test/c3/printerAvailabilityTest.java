package c3;

import app.customer;
import app.printOrder;
import app.calculatePrintingCharge;
import app.printerAvailability;
import app.applyDiscount;
import app.payment;
import app.emailInvoice;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

/**
 * printerAvailabilityTest.java (MUT#9)
 * Package c3: C.3 (Test Doubles - Mockito Mocks & Stubs).
 */
public class printerAvailabilityTest {

    @Mock
    private printerAvailability mockPrinterChecker;

    @Mock
    private applyDiscount mockDiscountCalculator;

    private calculatePrintingCharge calculationCoordinator;
    private customer sampleCustomer;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        calculationCoordinator = new calculatePrintingCharge(mockPrinterChecker, mockDiscountCalculator);
        sampleCustomer = new customer(101, "Joseph Lee", "josephd311@gmail.com", "0113341987", "Corporate", 25);
    }

    @Test
    public void testPrinterAvailableSuccessPath() {
        // MUT#9 TC#1 — Test Double (Mockito Stub): Mock printerAvailability to return true per Decision Table #3 Rule 1
        Mockito.when(mockPrinterChecker.isPrinterAvailable("A3", "Colour")).thenReturn(true);
        Mockito.when(mockDiscountCalculator.applyDiscount(Mockito.any(customer.class), Mockito.anyDouble())).thenReturn(10.00);

        printOrder order = new printOrder(sampleCustomer, "Colour", "A3", "Single-sided", 10, 2, "None", false, false);
        double charge = calculationCoordinator.processOrderCharge(order);

        assertTrue(charge > 0);
        assertNotEquals("Cancelled", order.getOrderStatus());

        // MUT#9 TC#1 — Test Double (Mockito Verify): Confirm mock isPrinterAvailable was called exactly once with exact parameters
        Mockito.verify(mockPrinterChecker, Mockito.times(1)).isPrinterAvailable("A3", "Colour");
    }

    @Test
    public void testPrinterUnavailableTerminationPath() {
        // MUT#9 TC#2 — Test Double (Mockito Stub): Mock printerAvailability to return false per Decision Table #3 Rule 2
        Mockito.when(mockPrinterChecker.isPrinterAvailable("A4", "Black & White")).thenReturn(false);

        printOrder order = new printOrder(sampleCustomer, "Black & White", "A4", "Single-sided", 20, 1, "None", false, false);
        double charge = calculationCoordinator.processOrderCharge(order);

        assertEquals(-1.0, charge, 0.001);
        assertEquals("Cancelled", order.getOrderStatus());

        // MUT#9 TC#2 — Test Double (Mockito Verify): Confirm mock was called and downstream methods NOT called
        Mockito.verify(mockPrinterChecker, Mockito.times(1)).isPrinterAvailable("A4", "Black & White");
        Mockito.verify(mockDiscountCalculator, Mockito.never()).applyDiscount(Mockito.any(customer.class), Mockito.anyDouble());
    }

    @Test
    public void testVerifyCorrectParametersPassedToMock() {
        // MUT#9 TC#3 — Test Double (Mockito Verify): Confirm isPrinterAvailable is invoked with exact arguments ('A5', 'Colour')
        Mockito.when(mockPrinterChecker.isPrinterAvailable("A5", "Colour")).thenReturn(true);
        Mockito.when(mockDiscountCalculator.applyDiscount(Mockito.any(customer.class), Mockito.anyDouble())).thenReturn(5.00);

        printOrder order = new printOrder(sampleCustomer, "Colour", "A5", "Single-sided", 10, 1, "None", false, false);
        calculationCoordinator.processOrderCharge(order);

        Mockito.verify(mockPrinterChecker, Mockito.times(1)).isPrinterAvailable("A5", "Colour");
    }

    @Test
    public void testPaymentAndEmailStubs() {
        // Rubric C.3 — Test Double (Stubbing unimplemented payment and emailInvoice classes)
        payment paymentStub = Mockito.mock(payment.class);
        emailInvoice emailStub = Mockito.mock(emailInvoice.class);

        Mockito.when(paymentStub.makePayment(150.00, "e-Wallet")).thenReturn(true);
        Mockito.when(emailStub.sendInvoiceEmail("josephd311@gmail.com", "Invoice Content")).thenReturn(true);

        assertTrue(paymentStub.makePayment(150.00, "e-Wallet"));
        assertTrue(emailStub.sendInvoiceEmail("josephd311@gmail.com", "Invoice Content"));

        Mockito.verify(paymentStub).makePayment(150.00, "e-Wallet");
        Mockito.verify(emailStub).sendInvoiceEmail("josephd311@gmail.com", "Invoice Content");
    }
}
