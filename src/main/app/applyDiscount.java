package app;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * applyDiscount.java
 * Calculates discounts per Table 4 and Business Rule 4.
 */
public class applyDiscount {

    public double applyDiscount(customer cust, double subtotal) {
        if (subtotal <= 0) {
            return 0.0;
        }

        double currentSubtotal = subtotal;

        if (cust != null && cust.getCustomerType() != null) {
            String type = cust.getCustomerType().trim();
            if ("Student".equalsIgnoreCase(type)) {
                currentSubtotal = currentSubtotal * 0.90; // 10%
            } else if ("Corporate".equalsIgnoreCase(type)) {
                currentSubtotal = currentSubtotal * 0.85; // 15%
            }
        }

        if (subtotal > 300.00) {
            currentSubtotal = currentSubtotal * 0.95; // extra 5%
        }

        if (cust != null && cust.getOrderCount() > 20) {
            currentSubtotal = currentSubtotal * 0.95; // extra 5%
        }

        double discountAmount = subtotal - currentSubtotal;
        return roundToTwoDecimals(discountAmount);
    }

    private double roundToTwoDecimals(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
