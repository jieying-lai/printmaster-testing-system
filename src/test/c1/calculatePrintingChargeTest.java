package c1;

import app.calculatePrintingCharge;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.*;

/**
 * calculatePrintingChargeTest.java (MUT#6, MUT#7, MUT#8)
 * Package c1: C.1 (Valid Case Tests).
 */
@RunWith(JUnitParamsRunner.class)
public class calculatePrintingChargeTest {
    private calculatePrintingCharge calculator;

    @Before
    public void setUp() {
        calculator = new calculatePrintingCharge();
    }

    /**
     * Decision Table #4: All 12 Base Rate Combinations (MUT#6)
     */
    @Test
    @Parameters({
        "Black & White, A4, Single-sided, 10, 1, 2.00",  // Rule 1: A4, B&W, Single (0.20 x 10 = 2.00)
        "Black & White, A4, Double-sided, 10, 1, 1.80",  // Rule 2: A4, B&W, Double (0.18 x 10 = 1.80)
        "Colour, A4, Single-sided, 10, 1, 8.00",         // Rule 3: A4, Colour, Single (0.80 x 10 = 8.00)
        "Colour, A4, Double-sided, 10, 1, 7.50",         // Rule 4: A4, Colour, Double (0.75 x 10 = 7.50)
        "Black & White, A3, Single-sided, 10, 1, 4.00",  // Rule 5: A3, B&W, Single (0.40 x 10 = 4.00)
        "Black & White, A3, Double-sided, 10, 1, 3.50",  // Rule 6: A3, B&W, Double (0.35 x 10 = 3.50)
        "Colour, A3, Single-sided, 10, 1, 15.00",        // Rule 7: A3, Colour, Single (1.50 x 10 = 15.00)
        "Colour, A3, Double-sided, 10, 1, 14.00",        // Rule 8: A3, Colour, Double (1.40 x 10 = 14.00)
        "Black & White, A5, Single-sided, 10, 1, 1.50",  // Rule 9: A5, B&W, Single (0.15 x 10 = 1.50)
        "Black & White, A5, Double-sided, 10, 1, 1.30",  // Rule 10: A5, B&W, Double (0.13 x 10 = 1.30)
        "Colour, A5, Single-sided, 10, 1, 6.00",         // Rule 11: A5, Colour, Single (0.60 x 10 = 6.00)
        "Colour, A5, Double-sided, 10, 1, 5.50"          // Rule 12: A5, Colour, Double (0.55 x 10 = 5.50)
    })
    public void testCalculateBaseChargeDecisionTableRules(String printType, String paperSize, String printingSide,
                                                          int numOfPages, int numOfCopies, double expectedBaseCharge) {
        // MUT#6 — Decision Table Rule 1–12 (Decision Table #4): Base rate calculation per Table 2 rates and Business Rule 2
        double actualBase = calculator.calculateBaseCharge(printType, paperSize, printingSide, numOfPages, numOfCopies);
        assertEquals(expectedBaseCharge, actualBase, 0.001);
    }

    /**
     * Decision Table #2: Optional Service Charge Rules (MUT#7)
     */
    @Test
    @Parameters({
        "None, false, false, 10, 1, 0.00",     // Rule 1: No optional services
        "Staple, false, false, 10, 1, 2.00",   // Rule 2: Binding only (Staple RM2.00)
        "None, true, false, 10, 2, 30.00",     // Rule 3: Lamination only (RM1.50 x 10 pages x 2 copies = 30.00, BR10)
        "None, false, true, 10, 1, 20.00",     // Rule 4: Express printing only (RM20.00 flat)
        "Comb, true, false, 10, 2, 35.00",     // Rule 5: Comb binding (RM5) + Lamination (RM30) = 35.00
        "Spiral, false, true, 10, 1, 28.00",   // Rule 6: Spiral binding (RM8) + Express (RM20) = 28.00
        "None, true, true, 10, 1, 35.00",      // Rule 7: Lamination (RM15) + Express (RM20) = 35.00
        "Spiral, true, true, 10, 1, 43.00"     // Rule 8: Spiral (RM8) + Lamination (RM15) + Express (RM20) = 43.00
    })
    public void testCalculateOptionalServiceChargesDecisionTableRules(String binding, boolean lamination, boolean express,
                                                                       int pages, int copies, double expectedServiceCharge) {
        // MUT#7 — Decision Table Rule 1–8 (Decision Table #2): Optional service charges per Table 3 and Business Rule 10
        double actualService = calculator.calculateOptionalServiceCharges(binding, lamination, express, pages, copies);
        assertEquals(expectedServiceCharge, actualService, 0.001);
    }

    /**
     * MUT#8: Combining base, service, and discount with rounding (BR5)
     */
    @Test
    @Parameters({
        "2.00, 0.00, 0.00, 2.00",       // Base only, no service, no discount
        "165.00, 180.00, 0.00, 345.00", // Base + Service, no discount
        "40.00, 0.00, 6.00, 34.00",     // Base + Discount, no service
        "1890.00, 300.00, 547.50, 1642.50" // Base + Service - Discount with 2 decimal place rounding (BR5)
    })
    public void testCalculatePrintingChargeFinalCombination(double base, double service, double discount, double expectedFinal) {
        // MUT#8 — Equivalence Partitioning & Business Rule 5: Final charge combination and 2 decimal place rounding
        double actualFinal = calculator.calculatePrintingCharge(base, service, discount);
        assertEquals(expectedFinal, actualFinal, 0.001);
    }
}
