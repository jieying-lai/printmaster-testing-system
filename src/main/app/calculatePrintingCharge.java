package app;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * calculatePrintingCharge.java
 * Calculates printing charges per Tables 2 & 3 and Business Rules 2, 5, 10.
 */
public class calculatePrintingCharge {
    private printerAvailability printerChecker;
    private applyDiscount discountCalculator;

    public calculatePrintingCharge() {
        this.printerChecker = new printerAvailability();
        this.discountCalculator = new applyDiscount();
    }

    public calculatePrintingCharge(printerAvailability printerChecker, applyDiscount discountCalculator) {
        this.printerChecker = printerChecker;
        this.discountCalculator = discountCalculator;
    }

    public double calculateBaseCharge(String printType, String paperSize, String printingSide, int numOfPages, int numOfCopies) {
        double rate = 0.0;

        if ("A4".equalsIgnoreCase(paperSize)) {
            if ("Black & White".equalsIgnoreCase(printType)) {
                rate = "Single-sided".equalsIgnoreCase(printingSide) ? 0.20 : 0.18;
            } else if ("Colour".equalsIgnoreCase(printType)) {
                rate = "Single-sided".equalsIgnoreCase(printingSide) ? 0.80 : 0.75;
            }
        } else if ("A3".equalsIgnoreCase(paperSize)) {
            if ("Black & White".equalsIgnoreCase(printType)) {
                rate = "Single-sided".equalsIgnoreCase(printingSide) ? 0.40 : 0.35;
            } else if ("Colour".equalsIgnoreCase(printType)) {
                rate = "Single-sided".equalsIgnoreCase(printingSide) ? 1.50 : 1.40;
            }
        } else if ("A5".equalsIgnoreCase(paperSize)) {
            if ("Black & White".equalsIgnoreCase(printType)) {
                rate = "Single-sided".equalsIgnoreCase(printingSide) ? 0.15 : 0.13;
            } else if ("Colour".equalsIgnoreCase(printType)) {
                rate = "Single-sided".equalsIgnoreCase(printingSide) ? 0.60 : 0.55;
            }
        }

        if (rate == 0.0) {
            throw new IllegalArgumentException("Invalid combination of paper size, print type, or printing side.");
        }

        double baseCharge = rate * numOfPages * numOfCopies;
        return roundToTwoDecimals(baseCharge);
    }

    public double calculateOptionalServiceCharges(String bindingOptions, boolean lamination, boolean expressPrinting, int numOfPages, int numOfCopies) {
        double serviceCharges = 0.0;

        if (bindingOptions != null) {
            String b = bindingOptions.trim();
            if ("Staple".equalsIgnoreCase(b)) {
                serviceCharges += 2.00;
            } else if ("Comb".equalsIgnoreCase(b)) {
                serviceCharges += 5.00;
            } else if ("Spiral".equalsIgnoreCase(b)) {
                serviceCharges += 8.00;
            }
        }

        if (lamination) {
            // BR10: charged on total printed pages = pages * copies
            serviceCharges += 1.50 * (numOfPages * numOfCopies);
        }

        if (expressPrinting) {
            serviceCharges += 20.00;
        }

        return roundToTwoDecimals(serviceCharges);
    }

    public double calculatePrintingCharge(double baseCharges, double serviceCharges, double discountAmount) {
        double netTotal = baseCharges + serviceCharges - discountAmount;
        if (netTotal < 0) {
            netTotal = 0.0;
        }
        return roundToTwoDecimals(netTotal);
    }

    public double processOrderCharge(printOrder order) {
        if (!printerChecker.isPrinterAvailable(order.getPaperSize(), order.getPrintType())) {
            System.out.println("Selected printer is currently unavailable.");
            order.setOrderStatus("Cancelled");
            return -1.0;
        }

        double base = calculateBaseCharge(order.getPrintType(), order.getPaperSize(), order.getPrintingSide(), order.getNumOfPages(), order.getNumOfCopies());
        double service = calculateOptionalServiceCharges(order.getBindingOptions(), order.isLamination(), order.isExpressPrinting(), order.getNumOfPages(), order.getNumOfCopies());
        double subtotal = base + service;

        double discount = discountCalculator.applyDiscount(order.getCustomerDetails(), subtotal);
        double finalTotal = calculatePrintingCharge(base, service, discount);

        order.setBasePrintingCharge(base);
        order.setAdditionalServiceCharges(service);
        order.setDiscounts(discount);
        order.setTotalPrintingCharge(finalTotal);

        return finalTotal;
    }

    private double roundToTwoDecimals(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
