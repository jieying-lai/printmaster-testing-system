package c2;

import app.calculatePrintingCharge;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.*;

/**
 * calculateBaseChargeInvalidTest.java (MUT#6)
 * Package c2: C.2 (Invalid Case Tests).
 */
@RunWith(JUnitParamsRunner.class)
public class calculateBaseChargeInvalidTest {
    private calculatePrintingCharge calculator;

    @Before
    public void setUp() {
        calculator = new calculatePrintingCharge();
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
        "Colour, A2, Single-sided, 10, 1",       // Invalid paper size (A2 not in {A3, A4, A5})
        "Greyscale, A4, Single-sided, 10, 1",     // Invalid print type (not Black & White / Colour)
        "Sepia, A6, Double-sided, 10, 1"          // Both paper size AND print type invalid
    })
    public void testCalculateBaseChargeInvalidCombination(String printType, String paperSize, String printingSide,
                                                            int numOfPages, int numOfCopies) {
        calculator.calculateBaseCharge(printType, paperSize, printingSide, numOfPages, numOfCopies);
    }
}