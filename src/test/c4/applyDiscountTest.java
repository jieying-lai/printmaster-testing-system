package c4;

import app.customer;
import app.applyDiscount;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.*;

/**
 * applyDiscountTest.java (MUT#10)
 * Package c4: C.4 (Externalised Test Data loaded from discount_test_data.txt).
 */
@RunWith(JUnitParamsRunner.class)
public class applyDiscountTest {
    private applyDiscount discountApp;

    @Before
    public void setUp() {
        discountApp = new applyDiscount();
    }

    /**
     * Requirement C.4: Externalised Test Data from discount_test_data.txt
     * Text file format: customerType, orderCount, subtotal, expectedDiscount, expectedFinalSubtotal
     */
    @Test
    @FileParameters("discount_test_data.txt")
    public void testApplyDiscountFromExternalTextFile(String customerType, int orderCount, double subtotal,
                                                     double expectedDiscount, double expectedFinalSubtotal) {
        // MUT#10 & Rubric C.4 — Externalised Test Data: Testing discount calculation loaded dynamically from discount_test_data.txt
        customer cust = new customer(100, "Test User", "test@gmail.com", "0123456789", customerType, orderCount);
        double actualDiscount = discountApp.applyDiscount(cust, subtotal);
        double actualFinal = subtotal - actualDiscount;

        assertEquals(expectedDiscount, actualDiscount, 0.01);
        assertEquals(expectedFinalSubtotal, actualFinal, 0.01);
    }

    /**
     * Decision Table #1 & Boundary Value Analysis (MUT#10 TC#1 to TC#9)
     */
    @Test
    @Parameters({
        "Student, 5, 100.00, 10.00",      // TC#1: Student 10% discount only
        "Corporate, 2, 100.00, 15.00",    // TC#2: Corporate 15% discount only
        "Regular, 3, 150.00, 0.00",       // TC#3: Regular customer no discount
        "Student, 4, 400.00, 58.00",      // TC#4: Student (10%) + Order subtotal > 300 (+5%) -> 400-10%=360, 360-5%=342 (disc 58)
        "Corporate, 25, 200.00, 38.50",   // TC#5: Corporate (15%) + Loyalty > 20 orders (+5%) -> 200-15%=170, 170-5%=161.50 (disc 38.50)
        "Corporate, 30, 500.00, 116.44",  // TC#6: Corporate (15%) + Order subtotal > 300 (+5%) + Loyalty > 20 orders (+5%) stacked -> 383.56 final (disc 116.44)
        "Student, 1, 300.00, 30.00",      // TC#7: Boundary Value Analysis: Subtotal exactly RM300.00 (boundary) -> +5% NOT applied
        "Corporate, 20, 100.00, 15.00",   // TC#8: Boundary Value Analysis: Order count exactly 20 (boundary) -> +5% loyalty NOT applied
        "Regular, 21, 100.00, 5.00"       // TC#9: Boundary Value Analysis: Order count 21 (just above boundary) -> +5% loyalty applied
    })
    public void testApplyDiscountDecisionTableAndBoundaryRules(String customerType, int orderCount, double subtotal, double expectedDiscount) {
        // MUT#10 — Decision Table Rule 1–8 & BVA: Validates discount stacking and threshold boundary conditions
        customer cust = new customer(100, "Test User", "test@gmail.com", "0123456789", customerType, orderCount);
        double actualDiscount = discountApp.applyDiscount(cust, subtotal);
        assertEquals(expectedDiscount, actualDiscount, 0.01);
    }
}
