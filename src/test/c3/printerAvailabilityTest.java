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
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

/**
 * printerAvailabilityTest.java (MUT#9)
 * Package c3: Addresses Rubric Criteria C.3 (Test Doubles - Mockito Mocks & Stubs).
 */
@RunWith(JUnitParamsRunner.class)
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
        Mockito.when(mockPrinterChecker.isPrinterAvailable("A3", "Colour")).thenReturn(true);
        Mockito.when(mockDiscountCalculator.applyDiscount(Mockito.any(customer.class), Mockito.anyDouble())).thenReturn(10.00);

        printOrder order = new printOrder(sampleCustomer, "Colour", "A3", "Single-sided", 10, 2, "None", false, false);
        double charge = calculationCoordinator.processOrderCharge(order);

        assertTrue(charge > 0);
        assertNotEquals("Cancelled", order.getOrderStatus());
        Mockito.verify(mockPrinterChecker, Mockito.times(1)).isPrinterAvailable("A3", "Colour");
    }

    @Test
    public void testPrinterUnavailableTerminationPath() {
        Mockito.when(mockPrinterChecker.isPrinterAvailable("A4", "Black & White")).thenReturn(false);

        printOrder order = new printOrder(sampleCustomer, "Black & White", "A4", "Single-sided", 20, 1, "None", false, false);
        double charge = calculationCoordinator.processOrderCharge(order);

        assertEquals(-1.0, charge, 0.001);
        assertEquals("Cancelled", order.getOrderStatus());

        Mockito.verify(mockPrinterChecker, Mockito.times(1)).isPrinterAvailable("A4", "Black & White");
        Mockito.verify(mockDiscountCalculator, Mockito.never()).applyDiscount(Mockito.any(customer.class), Mockito.anyDouble());
    }

    @Test
    public void testVerifyCorrectParametersPassedToMock() {
        Mockito.when(mockPrinterChecker.isPrinterAvailable("A5", "Colour")).thenReturn(true);
        Mockito.when(mockDiscountCalculator.applyDiscount(Mockito.any(customer.class), Mockito.anyDouble())).thenReturn(5.00);

        printOrder order = new printOrder(sampleCustomer, "Colour", "A5", "Single-sided", 10, 1, "None", false, false);
        calculationCoordinator.processOrderCharge(order);

        Mockito.verify(mockPrinterChecker, Mockito.times(1)).isPrinterAvailable("A5", "Colour");
    }

    @Test
    @Parameters({
        "A3, Colour, true, 10.00",         // Printer available -> charge proceeds
        "A4, Black & White, false, 0.00",  // Printer unavailable -> order cancelled
        "A5, Colour, true, 5.00"           // Printer available on another combo
    })
    public void testProcessOrderChargeAcrossPrinterAvailabilityScenarios(String paperSize, String printType,
                                                                          boolean printerAvailable, double mockDiscount) {
        Mockito.when(mockPrinterChecker.isPrinterAvailable(paperSize, printType)).thenReturn(printerAvailable);
        if (printerAvailable) {
            Mockito.when(mockDiscountCalculator.applyDiscount(Mockito.any(customer.class), Mockito.anyDouble()))
                   .thenReturn(mockDiscount);
        }

        printOrder order = new printOrder(sampleCustomer, printType, paperSize, "Single-sided", 10, 2, "None", false, false);
        double charge = calculationCoordinator.processOrderCharge(order);

        if (printerAvailable) {
            assertTrue(charge > 0);
            assertNotEquals("Cancelled", order.getOrderStatus());
        } else {
            assertEquals(-1.0, charge, 0.001);
            assertEquals("Cancelled", order.getOrderStatus());
        }

        Mockito.verify(mockPrinterChecker, Mockito.times(1)).isPrinterAvailable(paperSize, printType);
    }

    @Test
    public void testPaymentAndEmailStubs() {
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